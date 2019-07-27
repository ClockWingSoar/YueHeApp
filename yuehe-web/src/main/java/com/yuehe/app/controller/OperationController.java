package com.yuehe.app.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.dto.OperationDetailDTO;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleDetailDTO;
import com.yuehe.app.dto.ShopDetailDTO;
import com.yuehe.app.dto.YueHeAllShopsDetailDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.property.BaseProperty;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.OperationService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.YueHeUtil;

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
		List<OperationDetailDTO> operationList =new ArrayList<OperationDetailDTO>();
		operationList = operationService.getAllOperationForOperationList();
		 LOGGER.info("operationList {}", operationList);
		model.addAttribute("operationList",operationList);
		model.addAttribute("subModule", "operationList");
		model.addAttribute("module", "operation");
		
		return "user/operationList.html";
	}

	@GetMapping("/getOperationNewItem")
	public  String operationNewItemOverview(Model model){
		List<CosmeticShop> cosmeticShopList =  yueHeCommonService.getAllCosmeticShops();
		List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_OPERATOR);
		List<Tool> toolList = yueHeCommonService.getAllTools();
		
		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("dutyList", dutyList);
		model.addAttribute("subModule", "operationNewItem");
		return "user/operationNewItem.html";
	}
	
	@GetMapping("/getOperationSummary")
	public  String operationSummary(Model model){
		List<CosmeticShop> cosmeticShopList =  yueHeCommonService.getAllCosmeticShops();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
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
        operation.setSale(yueHeCommonService.getSaleById(saleId));
		operation.setEmployee(yueHeCommonService.getEmployeeById(operatorId));
        operation.setTool(yueHeCommonService.getToolById(toolId));
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
	List<SaleBeautifySkinItemForFilterDTO> findAllSalesByClientId(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		List<SaleBeautifySkinItemForFilterDTO> saleList = saleService.getSalesByClientId(clientId);
		saleList.forEach(l -> System.out.println(l));
		return saleList;
	}
    
	@RequestMapping(value = "/getSaleAllOperations", method = RequestMethod.GET)
	public @ResponseBody
	SaleDetailDTO  findAllOperationsBySaleId(
			@RequestParam(value = "saleId", required = true) String saleId) {
		System.err.println("saleId-"+saleId);
		SaleDetailDTO saleDetailDTO = operationService.getSaleOperationDetailBySaleId(saleId);
		System.out.println(saleDetailDTO);
		return saleDetailDTO;
	}
	@RequestMapping(value = "/getYueHeAllShopsOperations", method = RequestMethod.GET)
	public @ResponseBody
	SaleDetailDTO  findAllOperationsByYueHe() {
		YueHeAllShopsDetailDTO yueHeAllShopsDetailDTO = operationService.getYueHeAllShopsDetail();
		System.out.println(yueHeAllShopsDetailDTO);
		return yueHeAllShopsDetailDTO;
	}
	@RequestMapping(value = "/getShopAllClientsOperations", method = RequestMethod.GET)
	public @ResponseBody
	ShopDetailDTO  findAllOperationsByShop(
			@RequestParam(value = "shopId", required = true) String shopId) {
		System.err.println("shopId-"+shopId);
		ShopDetailDTO shopDetailDTO = operationService.getShopClientDetailByShopId(shopId);
		System.out.println(shopDetailDTO);
		return shopDetailDTO;
	}
	@RequestMapping(value = "/getClientAllSalesOperations", method = RequestMethod.GET)
	public @ResponseBody
	ClientDetailDTO  findAllOperationsByClient(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		ClientDetailDTO clientDetailDTO = operationService.getClientSaleDetailByClientId(clientId);
		System.out.println(clientDetailDTO);
		return clientDetailDTO;
	}

}
