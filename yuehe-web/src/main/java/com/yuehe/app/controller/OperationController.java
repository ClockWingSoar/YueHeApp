package com.yuehe.app.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuehe.app.dto.ClientDetailDto;
import com.yuehe.app.dto.OperationDetailDto;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto;
import com.yuehe.app.dto.SaleDetailDto;
import com.yuehe.app.dto.ShopDetailDto;
import com.yuehe.app.dto.YueHeAllShopsDetailDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.OperationService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class OperationController{
//	 @ModelAttribute("module")
//	    String module() {
//	        return "operation";
//	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	  private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private final OperationService operationService;
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	public OperationController(OperationService operationService, SaleService saleService,
			ClientService clientService,YueHeCommonService yueHeCommonService) {
		this.operationService = operationService;
		this.saleService = saleService;
		this.clientService = clientService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getOperationList")
	public  String operationOverview(Model model){
		// TODO Auto-generated method stub
		List<OperationDetailDto> operationList =new ArrayList<OperationDetailDto>();
		operationList = operationService.getAllOperationForOperationList();
		 LOGGER.info("operationList {}", operationList);
		model.addAttribute("operationList",operationList);
		model.addAttribute("subModule", "operationList");
		model.addAttribute("module", "operation");
		
		return "user/operationList.html";
	}

	@GetMapping("/getOperationNewItem")
	public  String operationNewItemOverview(Model model){
		yueHeCommonService.getAllCosmeticShops(model);
		yueHeCommonService.getAllPersonByRoleName(model,"操作人");
		yueHeCommonService.getAllTools(model);
		model.addAttribute("subModule", "operationNewItem");
		return "user/operationNewItem.html";
	}
	
	@GetMapping("/getOperationSummary")
	public  String operationSummary(Model model){
		yueHeCommonService.getAllCosmeticShops(model);
		model.addAttribute("subModule", "operationSummary");
		return "user/operationSummary.html";
	}
	
	
	@PostMapping("/createOperation")
    public String createOperation( //@RequestParam(name = "clientId", required = false) String clientId,
						            //@RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
									@RequestParam(name = "saleId", required = false) String saleId,
						            @RequestParam(name = "operatorId", required = false) String operatorId,
						            @RequestParam(name = "toolId", required = false) String toolId,
						            @RequestParam(name = "operationDate", required = false) Date operationDate,
						            @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        long idNums = operationService.getEntityNumber();
        String id = YueHeUtil.getId(4,Math.toIntExact(idNums));
        Operation operation =new Operation();
        operation.setId(id);
        operation.setSaleId(saleId);
		operation.setOperatorId(operatorId);
        operation.setToolId(toolId);
        operation.setOperationDate(simpleDateFormat.format(operationDate));
        operation.setDescription(description);
        System.err.println("operation:"+operation);
        LOGGER.info("operation:",operation);

        if (operation != null) {
            LOGGER.info("Saved {}", operationService.create(operation));
        }

        return "redirect:/getOperationList";
    }
	
	
	
	@RequestMapping(value = "/getShopAllClientsForFiltering", method = RequestMethod.GET)
	public @ResponseBody
	List<Client> findAllClientsByShopId(
	        @RequestParam(value = "cosmeticShopId", required = true) String cosmeticShopId) {
		System.err.println("cosmeticShopId-"+cosmeticShopId);
		List<Client> clientList = clientService.getClientsByShopId(cosmeticShopId);
		clientList.forEach(l -> System.out.println(l));
	    return clientList;
	}
	@RequestMapping(value = "/getClientAllSalesForFiltering", method = RequestMethod.GET)
	public @ResponseBody
	List<SaleBeautifySkinItemForFilterDto> findAllSalesByClientId(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		List<SaleBeautifySkinItemForFilterDto> saleList = saleService.getSalesByClientId(clientId);
		saleList.forEach(l -> System.out.println(l));
		return saleList;
	}
    
	@RequestMapping(value = "/getSaleAllOperations", method = RequestMethod.GET)
	public @ResponseBody
	SaleDetailDto  findAllOperationsBySaleId(
			@RequestParam(value = "saleId", required = true) String saleId) {
		System.err.println("saleId-"+saleId);
		SaleDetailDto saleDetailDto = operationService.getSaleOperationDetailBySaleId(saleId);
		System.out.println(saleDetailDto);
		return saleDetailDto;
	}
	@RequestMapping(value = "/getYueHeAllShopsOperations", method = RequestMethod.GET)
	public @ResponseBody
	SaleDetailDto  findAllOperationsByYueHe() {
		YueHeAllShopsDetailDto yueHeAllShopsDetailDto = operationService.getYueHeAllShopsDetail();
		System.out.println(yueHeAllShopsDetailDto);
		return yueHeAllShopsDetailDto;
	}
	@RequestMapping(value = "/getShopAllClientsOperations", method = RequestMethod.GET)
	public @ResponseBody
	ShopDetailDto  findAllOperationsByShop(
			@RequestParam(value = "shopId", required = true) String shopId) {
		System.err.println("shopId-"+shopId);
		ShopDetailDto shopDetailDto = operationService.getShopClientDetailByShopId(shopId);
		System.out.println(shopDetailDto);
		return shopDetailDto;
	}
	@RequestMapping(value = "/getClientAllSalesOperations", method = RequestMethod.GET)
	public @ResponseBody
	ClientDetailDto  findAllOperationsByClient(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		ClientDetailDto clientDetailDto = operationService.getClientSaleDetailByClientId(clientId);
		System.out.println(clientDetailDto);
		return clientDetailDto;
	}

}
