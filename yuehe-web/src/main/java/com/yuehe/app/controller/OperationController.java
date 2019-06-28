package com.yuehe.app.controller;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuehe.app.dto.OperationOperatorToolDto;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.service.OperationService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.ToolService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class OperationController{
	 @ModelAttribute("module")
	    String module() {
	        return "operation";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	
	@Autowired
	private final OperationService operationService;
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ToolService toolService;
	@Autowired
	private final EmployeeService employeeService;
	private  Employee employee;
	public OperationController(OperationService operationService, SaleService saleService,
			EmployeeService employeeService,ToolService toolService) {
		this.operationService = operationService;
		this.saleService = saleService;
		this.employeeService = employeeService;
		this.toolService = toolService;
	}

	@GetMapping("/getOperationList")
	public  String operationOverview(Model model){
		// TODO Auto-generated method stub
		List<OperationOperatorToolDto> operationList =new ArrayList<OperationOperatorToolDto>();
		operationList = operationService.getOperationsDetailList();
		 LOGGER.info("operationList {}", operationList);
		model.addAttribute("operationList",operationList);
		
		return "user/operation";
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
        Date operationDateObj =new Date();
        try {
        	 //createCardDateObj=new SimpleDateFormat("yyyy-MM-dd").parse(createCardDate);
        	 operationDateObj=new SimpleDateFormat("yyyy-MM-dd").parse(operationDate);
//        	 LOGGER.debug("createCardDateObj:",createCardDateObj);
        	 LOGGER.debug("operationDateObj:",operationDateObj);
 		} catch (ParseException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
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
        operation.setOperationDate(operationDateObj);
        operation.setDescription(description);
        LOGGER.debug("operation:",operation);

        if (operation != null) {
            LOGGER.info("Saved {}", operationService.create(operation));
        }

        return "redirect:/getOperationList";
    }
	
    

}
