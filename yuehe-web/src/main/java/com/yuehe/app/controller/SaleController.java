package com.yuehe.app.controller;
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

import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.service.BeautifySkinItemService;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class SaleController{
	 @ModelAttribute("module")
	    String module() {
	        return "sale";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
	
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	@Autowired
	private final EmployeeService employeeService;
	private CosmeticShop cosmeticShop;
	private  Client client;
	private  Employee employee;
	private  BeautifySkinItem beautifySkinItem;
	@Autowired
	private final BeautifySkinItemService beautifySkinItemService;
	public SaleController(SaleService saleService, ClientService clientService,
			EmployeeService employeeService,BeautifySkinItemService beautifySkinItemService,
			CosmeticShopService cosmeticShopService) {
		this.saleService = saleService;
		this.clientService = clientService;
		this.employeeService = employeeService;
		this.beautifySkinItemService = beautifySkinItemService;
		this.cosmeticShopService = cosmeticShopService;
	}

	@GetMapping("/getSaleList")
	public  String saleOverview(Model model){
		// TODO Auto-generated method stub
		List<SaleClientItemSellerDto> saleList =new ArrayList<SaleClientItemSellerDto>();
		saleList = saleService.getSalesDetailList();
		 LOGGER.info("saleList {}", saleList);
		model.addAttribute("saleList",saleList);
		
		return "user/sale";
	}
	@PostMapping("/createSale")
    public String createSale( @RequestParam(name = "clientName", required = false) String clientName,
    								@RequestParam(name = "beautifySkinItemName", required = false) String beautifySkinItemName,
                                       @RequestParam(name = "cosmeticShopName", required = false) String cosmeticShopName,
                                       @RequestParam(name = "itemNumber", required = false) int itemNumber,
                                       @RequestParam(name = "createCardTotalAmount", required = false) long createCardTotalAmount,
                                       @RequestParam(name = "receivedAmount", required = false) int receivedAmount,
                                       @RequestParam(name = "employeePremium", required = false) float employeePremium,
                                       @RequestParam(name = "shopPremium", required = false) float shopPremium,
                                       @RequestParam(name = "createCardDate", required = false) String createCardDate,
                                       @RequestParam(name = "sellerName", required = false) String sellerName,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        long idNums = saleService.getEntityNumber();
        String id = YueHeUtil.getId(6,Math.toIntExact(idNums));
        Sale sale =new Sale();
        sale.setId(id);
        cosmeticShop = cosmeticShopService.getCosmeticShopByName(cosmeticShopName);
        LOGGER.debug("cosmeticshop:",cosmeticShop);
        client = clientService.getClientByClientNameAndShopName(clientName,cosmeticShopName);
	    LOGGER.debug("client:",client);
        if(client != null)
        	sale.setClientId(client.getId());
        employee = employeeService.getEmployeeByName(sellerName);
    	LOGGER.debug("employee:",employee);
        if(employee != null)
        	sale.setSellerId(employee.getId());
        beautifySkinItem = beautifySkinItemService.getBeautifySkinItemByName(beautifySkinItemName);
    	LOGGER.debug("beautifySkinItem:",beautifySkinItem);
        if(beautifySkinItem != null)
        	sale.setBeautifySkinItemId(beautifySkinItem.getId());
        sale.setItemNumber(itemNumber);
        sale.setCreateCardTotalAmount(createCardTotalAmount);
        sale.setReceivedAmount(receivedAmount);
        sale.setEmployeePremium(employeePremium);
        sale.setShopPremium(shopPremium);
        sale.setCreateCardDate(createCardDate);
        sale.setDescription(description);
        LOGGER.debug("sale:",sale);

        if (sale != null) {
            LOGGER.info("Saved {}", saleService.create(sale));
        }

        return "redirect:/getSaleList";
    }
	
    

}
