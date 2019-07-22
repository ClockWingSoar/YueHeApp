package com.yuehe.app.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.util.StringUtils;
import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.SaleClientItemSellerDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDTO;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.YueHeUtil;
import com.yuehe.app.view.CsvView;

// import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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



@Controller
public class SaleController{
	 @ModelAttribute("module")
	    String module() {
	        return "sale";
	    }
	private final static Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
	private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	String sortProperty = "id"; //default sort column is sale id
	Direction sortDirection = Direction.ASC; //default sort direction is ascending
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
		
        if (!StringUtils.isNullOrEmpty(request.getParameter("page"))) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        if (!StringUtils.isNullOrEmpty(request.getParameter("size"))) {
            pageSize = Integer.parseInt(request.getParameter("size"));
        }
		buildSortOrderBeforeDBQuerying(request);
        saleMap = saleService.getSalesDetailListWithPaginationAndSort(pageNumber, pageSize,this.sortDirection,this.sortProperty);
        @SuppressWarnings("unchecked")
		Page<SaleClientItemSellerForDBDTO> saleMapPage = (Page<SaleClientItemSellerForDBDTO>)saleMap.get("salePage");
        List<Sort.Order> sortOrders = saleMapPage.getSort().stream().collect(Collectors.toList());
		sortOrders.forEach(l -> System.out.println(l));
		setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		 LOGGER.info("saleMap {}", saleMap);
		model.addAllAttributes(saleMap);
		model.addAttribute("subModule", "saleList");
//		model.addAttribute("saleListPage",saleList);
		
		return "user/saleList.html";
	}
	  /**
     * Handle request to download an Excel document
     */
    @GetMapping("/saleCsvDownload")
    public void saleCsvDownload(Model model, HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> saleMap =new HashMap<String,Object>();

		buildSortOrderBeforeDBQuerying(request);
		saleMap = saleService.getSalesDetailListForDownload(this.sortDirection,this.sortProperty);
		// List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = (List<SaleClientItemSellerDTO>)saleMap.get("saleList");
		// model.addAttribute("csvObjList", saleClientItemSellerDTOList);
		String[] headers ={"saleId","sellerName","cosmeticShopName","clientName","beautifySkinItemName","createCardDate","itemNumber","createCardTotalAmount","receivedAmount",
								"discount","unpaidAmount","earnedAmount","receivedEarnedAmount","unpaidEarnedAmount","employeePremium","shopPremium","description"};
		saleMap.put("headers", headers);
		@SuppressWarnings("unchecked")
		List<Sort.Order> sortOrders = (List<Sort.Order>)saleMap.get("sortOrders");
		setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		try {
			new CsvView().buildCsvDocument(saleMap, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
        // return " "; to fix the issue of java.lang.IllegalStateException: getOutputStream() has already been called for this responsejava.lang.IllegalStateException: getOutputStream() has already been called for this response
	}
	private void buildSortOrderBeforeDBQuerying(HttpServletRequest request){
		String sort = "id,asc"; //default sort column is sale id and in ascending order
		if (!StringUtils.isNullOrEmpty(request.getParameter("sort"))) {
        	sort = request.getParameter("sort");
		}
		String[] sortStr = sort.split(",");
        this.sortProperty = sortStr[0];
        this.sortDirection = Direction.fromString(sortStr[1]);
	}
	private void setBackSortOrderAfterDBQuerying(List<Sort.Order> sortOrders, Model model, String sortProperty, Direction sortDirection){
		if(sortOrders != null && !sortOrders.isEmpty()){
			int sortOrdersLen = sortOrders.size();
			Sort.Order order = sortOrders.get(sortOrdersLen-1);			
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDirectionFlag", order.getDirection() == Sort.Direction.DESC);
		}else{//set back sortProperty and sortDirectionFlag for the column that not in table,like discount, unpaidAmount,etc.
            model.addAttribute("sortProperty", sortProperty);
            model.addAttribute("sortDirectionFlag", sortDirection == Sort.Direction.DESC);

		}
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
