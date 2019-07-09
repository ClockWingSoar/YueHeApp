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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.DutyEmployeeRoleDto;
import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.dto.SalePerformanceDetailDto;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDto;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.service.BeautifySkinItemService;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.service.DutyService;
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
	  private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final DutyService dutyService;
	@Autowired
	private final BeautifySkinItemService beautifySkinItemService;
	public SaleController(SaleService saleService, ClientService clientService, DutyService dutyService,
			EmployeeService employeeService,BeautifySkinItemService beautifySkinItemService,
			CosmeticShopService cosmeticShopService) {
		this.saleService = saleService;
		this.clientService = clientService;
		this.employeeService = employeeService;
		this.beautifySkinItemService = beautifySkinItemService;
		this.cosmeticShopService = cosmeticShopService;
		this.dutyService = dutyService;
	}

	@GetMapping("/getSaleList")
	public  String saleOverview(Model model){
		// TODO Auto-generated method stub
		List<SaleClientItemSellerDto> saleList =new ArrayList<SaleClientItemSellerDto>();
		saleList = saleService.getSalesDetailList();
		 LOGGER.info("saleList {}", saleList);
		model.addAttribute("subModule", "saleList");
		model.addAttribute("saleList",saleList);
		
		return "user/saleList";
	}
	@GetMapping("/getSaleNewItem")
	public  String saleNewItem(Model model){
		getAllCosmeticShops(model);
		getAllSellerRoles(model);
		getAllBeautifySkinItems(model);
		model.addAttribute("subModule", "saleNewItem");
		
		return "user/saleNewItem";
	}
	@GetMapping("/getSaleSummary")
	public  String saleSummary(Model model){
		getAllCosmeticShops(model);
		model.addAttribute("subModule", "saleSummary");
		
		return "user/saleSummary";
	}
	public void getAllCosmeticShops(Model model) {
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
	}
	public void getAllBeautifySkinItems(Model model) {
		List<BeautifySkinItem> beautifySkinItemList = beautifySkinItemService.getAllBeautifySkinItem();
		model.addAttribute("beautifySkinItemList", beautifySkinItemList);
	}
	public void getAllEmployees(Model model) {
		List<Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employeeList", employeeList);
	}
	public void getAllSellerRoles(Model model) {
		List<DutyEmployeeRoleDto> sellerList = dutyService.getAllPersonByRoleName("专家");
		sellerList.forEach(l -> System.out.println(l));
		model.addAttribute("sellerList", sellerList);
	}
	@PostMapping("/createSale")
    public String createSale( @RequestParam(name = "clientId", required = false) String clientId,
    								@RequestParam(name = "beautifySkinItemId", required = false) String beautifySkinItemId,
//                                       @RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
                                       @RequestParam(name = "itemNumber", required = false) int itemNumber,
                                       @RequestParam(name = "createCardTotalAmount", required = false) long createCardTotalAmount,
                                       @RequestParam(name = "receivedAmount", required = false) long receivedAmount,
                                       @RequestParam(name = "receivedEarnedAmount", required = false) long receivedEarnedAmount,
                                       @RequestParam(name = "employeePremium", required = false) float employeePremium,
                                       @RequestParam(name = "shopPremium", required = false) float shopPremium,
                                       @RequestParam(name = "createCardDate", required = false) Date createCardDate,
                                       @RequestParam(name = "sellerId", required = false) String sellerId,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        long idNums = saleService.getEntityNumber();
        String id = YueHeUtil.getId(6,Math.toIntExact(idNums));
        Sale sale =new Sale();
        sale.setId(id);
        sale.setClientId(clientId);
        sale.setSellerId(sellerId);
        sale.setBeautifySkinItemId(beautifySkinItemId);
        sale.setItemNumber(itemNumber);
        sale.setCreateCardTotalAmount(createCardTotalAmount);
        sale.setReceivedAmount(receivedAmount);
        sale.setReceivedEarnedAmount(receivedEarnedAmount);
        sale.setEmployeePremium(employeePremium);
        sale.setShopPremium(shopPremium);
        sale.setCreateCardDate(simpleDateFormat.format(createCardDate));
        sale.setDescription(description);
        LOGGER.debug("sale:",sale);

        if (sale != null) {
            LOGGER.info("Saved {}", saleService.create(sale));
        }

        return "redirect:/getSaleList";
    }
	
	@RequestMapping(value = "/getYueHeAllShopsSales", method = RequestMethod.GET)
	public @ResponseBody
	YueHeAllSalesPerformanceDetailDto  findAllSalesByYueHe() {
		YueHeAllSalesPerformanceDetailDto yueHeAllSalesPerformanceDetailDto = saleService.getYueHeAllSalesPerformanceDetail();
		System.out.println(yueHeAllSalesPerformanceDetailDto);
		return yueHeAllSalesPerformanceDetailDto;
	}
	@RequestMapping(value = "/getShopAllClientsSales", method = RequestMethod.GET)
	public @ResponseBody
	ShopAllSalesPerformanceDetailDto  findAllSalesByShop(
			@RequestParam(value = "shopId", required = true) String shopId) {
		System.err.println("shopId-"+shopId);
		ShopAllSalesPerformanceDetailDto shopAllSalesPerformanceDetailDto = saleService.getShopAllSalesPerformanceDetail(shopId);
		System.out.println(shopAllSalesPerformanceDetailDto);
		return shopAllSalesPerformanceDetailDto;
	}
	@RequestMapping(value = "/getClientAllSales", method = RequestMethod.GET)
	public @ResponseBody
	ClientAllSalesPerformanceDetailDto  findAllSalesByClient(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto = saleService.getClientAllSalesPerformanceDetail(clientId);
		System.out.println(clientAllSalesPerformanceDetailDto);
		return clientAllSalesPerformanceDetailDto;
	}
	@RequestMapping(value = "/getSalePerformanceDetail", method = RequestMethod.GET)
	public @ResponseBody
	SalePerformanceDetailDto  findSalePerformanceDetailBySaleId(
			@RequestParam(value = "saleId", required = true) String saleId) {
		System.err.println("saleId-"+saleId);
		SalePerformanceDetailDto salePerformanceDetailDto = saleService.getSalePerformanceDetail(saleId);
		System.out.println(salePerformanceDetailDto);
		return salePerformanceDetailDto;
	}
}
