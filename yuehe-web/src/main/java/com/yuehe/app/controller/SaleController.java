package com.yuehe.app.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDTO;
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
	public  String saleOverview(HttpServletRequest request,Model model){
//		public  String saleOverview(@PageableDefault(size = 10,sort = "id") Pageable pageable,Model model){
		// TODO Auto-generated method stub
		Map<String,Object> saleMap =new HashMap<String,Object>();
//		saleList = saleService.getSalesDetailList(pageable);
		int pageNumber = 0; //default page number is 0 (yes it is weird)
	    int pageSize = 10; //default page size is 10
	    String sort = "id,asc"; //default sort column is sale id and in ascending order
	    String sortProperty = "id"; //default sort column is sale id
	    Direction sortDirection = Direction.ASC; //default sort direction is ascending
	    
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            pageSize = Integer.parseInt(request.getParameter("size"));
        }
        if (request.getParameter("sort") != null && !request.getParameter("sort").isEmpty()) {
        	sort = request.getParameter("sort");
        }
        String[] sortStr = sort.split(",");
        sortProperty = sortStr[0];
        sortDirection = Direction.fromString(sortStr[1]);
        saleMap = saleService.getSalesDetailList(pageNumber, pageSize,sortDirection,sortProperty);
        @SuppressWarnings("unchecked")
		Page<SaleClientItemSellerForDBDTO> saleMapPage = (Page<SaleClientItemSellerForDBDTO>)saleMap.get("salePage");
        List<Sort.Order> sortOrders = saleMapPage.getSort().stream().collect(Collectors.toList());
		sortOrders.forEach(l -> System.out.println(l));
		int sortOrdersLen = sortOrders.size();
        if (sortOrdersLen > 0) {
			Sort.Order order = sortOrders.get(sortOrdersLen-1);			
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDirectionFlag", order.getDirection() == Sort.Direction.DESC);
        }else{//set back sortProperty and sortDirectionFlag for the column that not in table,like discount, unpaidAmount,etc.
            model.addAttribute("sortProperty", sortProperty);
            model.addAttribute("sortDirectionFlag", sortDirection == Sort.Direction.DESC);

		}
		 LOGGER.info("saleMap {}", saleMap);
		model.addAllAttributes(saleMap);
		model.addAttribute("subModule", "saleList");
//		model.addAttribute("saleListPage",saleList);
		
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
	YueHeAllSalesPerformanceDetailDTO  findAllSalesByYueHe() {
		YueHeAllSalesPerformanceDetailDTO yueHeAllSalesPerformanceDetailDTO = saleService.getYueHeAllSalesPerformanceDetail();
		System.out.println(yueHeAllSalesPerformanceDetailDTO);
		return yueHeAllSalesPerformanceDetailDTO;
	}
	@RequestMapping(value = "/getShopAllClientsSales", method = RequestMethod.GET)
	public @ResponseBody
	ShopAllSalesPerformanceDetailDTO  findAllSalesByShop(
			@RequestParam(value = "shopId", required = true) String shopId) {
		System.err.println("shopId-"+shopId);
		ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = saleService.getShopAllSalesPerformanceDetail(shopId);
		System.out.println(shopAllSalesPerformanceDetailDTO);
		return shopAllSalesPerformanceDetailDTO;
	}
	@RequestMapping(value = "/getClientAllSales", method = RequestMethod.GET)
	public @ResponseBody
	ClientAllSalesPerformanceDetailDTO  findAllSalesByClient(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-"+clientId);
		ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = saleService.getClientAllSalesPerformanceDetail(clientId);
		System.out.println(clientAllSalesPerformanceDetailDTO);
		return clientAllSalesPerformanceDetailDTO;
	}
	@RequestMapping(value = "/getSalePerformanceDetail", method = RequestMethod.GET)
	public @ResponseBody
	SalePerformanceDetailDTO  findSalePerformanceDetailBySaleId(
			@RequestParam(value = "saleId", required = true) String saleId) {
		System.err.println("saleId-"+saleId);
		SalePerformanceDetailDTO salePerformanceDetailDTO = saleService.getSalePerformanceDetail(saleId);
		System.out.println(salePerformanceDetailDTO);
		return salePerformanceDetailDTO;
	}
}
