package com.yuehe.app.controller;
import java.util.ArrayList;
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

import com.yuehe.app.dto.OperationDetailDto;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto;
import com.yuehe.app.dto.SaleDetailDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.service.OperationService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.ToolService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class OperationController{
//	 @ModelAttribute("module")
//	    String module() {
//	        return "operation";
//	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	
	@Autowired
	private final OperationService operationService;
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ToolService toolService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	@Autowired
	private final EmployeeService employeeService;
	private  Employee employee;
	public OperationController(OperationService operationService, SaleService saleService,CosmeticShopService cosmeticShopService,
			EmployeeService employeeService,ToolService toolService, ClientService clientService) {
		this.operationService = operationService;
		this.saleService = saleService;
		this.employeeService = employeeService;
		this.toolService = toolService;
		this.clientService = clientService;
		this.cosmeticShopService = cosmeticShopService;
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
		
		return "user/operationList";
	}

	@GetMapping("/getOperationNewItem")
	public  String operationNewItemOverview(Model model){
		model.addAttribute("subModule", "operationNewItem");
		return "user/operationNewItem";
	}
	
	@GetMapping("/getOperationSummary")
	public  String operationSummary(Model model){
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		model.addAttribute("subModule", "operationSummary");
		model.addAttribute("cosmeticShopList", cosmeticShopList);
		return "user/operationSummary";
	}
	
	
	@PostMapping("/createOperation")
    public String createOperation( @RequestParam(name = "clientName", required = false) String clientName,
						            @RequestParam(name = "cosmeticShopName", required = false) String cosmeticShopName,
									@RequestParam(name = "beautifySkinItemName", required = false) String beautifySkinItemName,
						            @RequestParam(name = "createCardDate", required = false) String createCardDate,
						            @RequestParam(name = "operatorName", required = false) String operatorName,
						            @RequestParam(name = "toolName", required = false) String toolName,
						            @RequestParam(name = "operationDate", required = false) String operationDate,
						            @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        long idNums = operationService.getEntityNumber();
        String id = YueHeUtil.getId(4,Math.toIntExact(idNums));
        Operation operation =new Operation();
        operation.setId(id);
        //Date createCardDateObj =new Date();
//        Date operationDateObj =new Date();
//        try {
//        	 //createCardDateObj=new SimpleDateFormat("yyyy-MM-dd").parse(createCardDate);
//        	 operationDateObj=new SimpleDateFormat("yyyy-MM-dd").parse(operationDate);
////        	 LOGGER.debug("createCardDateObj:",createCardDateObj);
//        	 LOGGER.debug("operationDateObj:",operationDateObj);
// 		} catch (ParseException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
        Sale sale = saleService.getSaleByClientNameAndShopNameAndItemNameAndCreateCardDate(
        		clientName,cosmeticShopName,beautifySkinItemName,createCardDate);
	   LOGGER.debug("sale:",sale);
	   if(sale != null)
		   operation.setSaleId(sale.getId());
	   employee = employeeService.getEmployeeByName(operatorName);
	   LOGGER.debug("employee:",employee);
	   if(employee != null)
		   operation.setOperatorId(employee.getId());
        Tool tool = toolService.getToolByName(toolName);
        LOGGER.debug("tool:",tool);
        if(tool != null)
        	operation.setToolId(tool.getId());
        operation.setOperationDate(operationDate);
        operation.setDescription(description);
        LOGGER.debug("operation:",operation);

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
	

}
