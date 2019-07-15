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
import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.dto.SalePerformanceDetailDto;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDto;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;
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
	private final YueHeCommonService yueHeCommonService;
	public SaleController(SaleService saleService, YueHeCommonService yueHeCommonService) {
		this.saleService = saleService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getSaleList")
	public  String saleOverview(Model model){
		// TODO Auto-generated method stub
		List<SaleClientItemSellerDto> saleList =new ArrayList<SaleClientItemSellerDto>();
		saleList = saleService.getSalesDetailList();
		 LOGGER.info("saleList {}", saleList);
		model.addAttribute("subModule", "saleList");
		model.addAttribute("saleList",saleList);
		
		return "user/saleList.html";
	}
	@GetMapping("/getSaleNewItem")
	public  String saleNewItem(Model model){
		yueHeCommonService.getAllCosmeticShops(model);
		yueHeCommonService.getAllPersonByRoleName(model,"专家");
		yueHeCommonService.getAllBeautifySkinItems(model);
		model.addAttribute("subModule", "saleNewItem");
		
		return "user/saleNewItem.html";
	}
	@GetMapping("/getSaleSummary")
	public  String saleSummary(Model model){
		yueHeCommonService.getAllCosmeticShops(model);
		model.addAttribute("subModule", "saleSummary");
		
		return "user/saleSummary.html";
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
