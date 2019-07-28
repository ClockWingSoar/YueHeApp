package com.yuehe.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.mysql.cj.util.StringUtils;
import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDTO;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.property.BaseProperty;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.IdType;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SaleController {
	@ModelAttribute("module")
	String module() {
		return "sale";
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String sortProperty = "id"; // default sort column is sale id
	Direction sortDirection = Direction.ASC; // default sort direction is ascending
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;

	public SaleController(SaleService saleService, YueHeCommonService yueHeCommonService, ClientService clientService) {
		this.saleService = saleService;
		this.clientService = clientService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getSaleList")
	public String saleOverview(HttpServletRequest request, Model model) {
		// public String saleOverview(@PageableDefault(size = 10,sort = "id") Pageable
		// pageable,Model model){
		Map<String, Object> saleMap = new HashMap<String, Object>();
		// saleList = saleService.getSalesDetailList(pageable);
		int pageNumber = 0; // default page number is 0 (yes it is weird)
		int pageSize = 10; // default page size is 10

		if (!StringUtils.isNullOrEmpty(request.getParameter("page"))) {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		}

		if (!StringUtils.isNullOrEmpty(request.getParameter("size"))) {
			pageSize = Integer.parseInt(request.getParameter("size"));
		}
		buildSortOrderBeforeDBQuerying(request);
		saleMap = saleService.getSalesDetailListWithPaginationAndSort(pageNumber, pageSize, this.sortDirection,
				this.sortProperty);
		@SuppressWarnings("unchecked")
		Page<SaleClientItemSellerForDBDTO> saleMapPage = (Page<SaleClientItemSellerForDBDTO>) saleMap.get("salePage");
		List<Sort.Order> sortOrders = saleMapPage.getSort().stream().collect(Collectors.toList());
		sortOrders.forEach(l -> System.out.println(l));
		setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		LOGGER.info("saleMap {}", saleMap);
		model.addAllAttributes(saleMap);
		model.addAttribute("subModule", "saleList");
		// model.addAttribute("saleListPage",saleList);

		return "user/saleList.html";
	}

	/**
	 * Handle request to download an Excel document
	 */
	@GetMapping("/saleCsvDownload")
	public void saleCsvDownload(Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> saleMap = new HashMap<String, Object>();

		buildSortOrderBeforeDBQuerying(request);
		saleMap = saleService.getSalesDetailListForDownload(this.sortDirection, this.sortProperty);
		// List<SaleClientItemSellerDTO> saleClientItemSellerDTOList =
		// (List<SaleClientItemSellerDTO>)saleMap.get("saleList");
		// model.addAttribute("csvObjList", saleClientItemSellerDTOList);
		String[] headers = { "saleId", "sellerName", "cosmeticShopName", "clientName", "beautifySkinItemName",
				"createCardDate", "itemNumber", "createCardTotalAmount", "receivedAmount", "discount", "unpaidAmount",
				"earnedAmount", "receivedEarnedAmount", "unpaidEarnedAmount", "employeePremium", "shopPremium",
				"description" };
		saleMap.put("headers", headers);
		@SuppressWarnings("unchecked")
		List<Sort.Order> sortOrders = (List<Sort.Order>) saleMap.get("sortOrders");
		setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		try {
			new CsvView().buildCsvDocument(saleMap, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		// return " "; to fix the issue of java.lang.IllegalStateException:
		// getOutputStream() has already been called for this
		// responsejava.lang.IllegalStateException: getOutputStream() has already been
		// called for this response
	}

	private void buildSortOrderBeforeDBQuerying(HttpServletRequest request) {
		String sort = "id,asc"; // default sort column is sale id and in ascending order
		if (!StringUtils.isNullOrEmpty(request.getParameter("sort"))) {
			sort = request.getParameter("sort");
		}
		String[] sortStr = sort.split(",");
		this.sortProperty = sortStr[0];
		this.sortDirection = Direction.fromString(sortStr[1]);
	}

	private void setBackSortOrderAfterDBQuerying(List<Sort.Order> sortOrders, Model model, String sortProperty,
			Direction sortDirection) {
		if (sortOrders != null && !sortOrders.isEmpty()) {
			int sortOrdersLen = sortOrders.size();
			Sort.Order order = sortOrders.get(sortOrdersLen - 1);
			model.addAttribute("sortProperty", order.getProperty());
			model.addAttribute("sortDirectionFlag", order.getDirection() == Sort.Direction.DESC);
		} else {// set back sortProperty and sortDirectionFlag for the column that not in
				// table,like discount, unpaidAmount,etc.
			model.addAttribute("sortProperty", sortProperty);
			model.addAttribute("sortDirectionFlag", sortDirection == Sort.Direction.DESC);

		}
	}

	@GetMapping("/getSaleNewItem")
	public String saleNewItem(Model model) {
		getSaleNewItemDropDownDataList(model);
		model.addAttribute("subModule", "saleNewItem");

		return "user/saleNewItem.html";
	}

	@GetMapping("/sale/edit/{id}")
	public String saleEditItem(Model model, @PathVariable("id") String id) {
		getSaleDetail(model, id);
		return "user/saleEditItem.html";
	}

	/**
	 * 
	 * get the initial data for cosmeticshop, seller and beautify skin item list to
	 * create a new sale item.
	 * 
	 */
	public void getSaleNewItemDropDownDataList(Model model) {
		List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
		List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_EXPERT);
		List<BeautifySkinItem> beautifySkinItemList = yueHeCommonService.getAllBeautifySkinItems();

		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("beautifySkinItemList", beautifySkinItemList);
		model.addAttribute("dutyList", dutyList);
	}

	public void getSaleDetail(Model model, String id) {
		getSaleNewItemDropDownDataList(model);
		Sale sale = saleService.getById(id);
		model.addAttribute("sale", sale);
	}

	@PostMapping("/sale/update/{id}")
	public String updateSaleItem(Model model, @PathVariable("id") String id, @Valid Sale sale, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// sale.setId(id);

			List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
			List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService
					.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_EXPERT);
			List<BeautifySkinItem> beautifySkinItemList = yueHeCommonService.getAllBeautifySkinItems();
			// to add back the initial data of sale edit item and the error message
			attr.addFlashAttribute("org.springframework.validation.BindingResult.sale", result);
			// attr.addFlashAttribute("sale", sale);
			// attr.addFlashAttribute("cosmeticShopList", cosmeticShopList);
			// attr.addFlashAttribute("dutyList", dutyList);
			// attr.addFlashAttribute("beautifySkinItemList", beautifySkinItemList);
			// to set back the sale client name for client list dropdown since form object
			// "sale" won't save client name
			// only save the client id to the object "sale"
			sale.setClient(clientService.getById(sale.getClient().getId()));
			model.addAttribute("sale", sale);
			model.addAttribute("cosmeticShopList", cosmeticShopList);
			model.addAttribute("beautifySkinItemList", beautifySkinItemList);
			model.addAttribute("dutyList", dutyList);
			return "user/saleEditItem.html";
		}
		LOGGER.debug("update sale:", sale);

		if (sale != null) {
			LOGGER.info("updated {}", saleService.create(sale));
		}
		attr.addFlashAttribute("message", "销售卡-" + id + " 更新成功");
		return "redirect:/getSaleList";
	}

	@GetMapping("/sale/delete/{id}")
	public String deleteSaleItem(@PathVariable("id") String id, Sale sale, Model model,RedirectAttributes attr) {
		System.err.println("delete sale item with id=" + id);
		// Sale sale
		LOGGER.info("deleting {}", saleService.getById(id));
		LOGGER.info("deleting frontend sale{}", sale);
		// saleService.delete(sale);
		saleService.deleteById(id);
		attr.addFlashAttribute("message", "销售卡-" + id + " 删除成功");
		return "redirect:/getSaleList";
	}

	@GetMapping("/getSaleSummary")
	public String saleSummary(Model model) {
		List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("subModule", "saleSummary");

		return "user/saleSummary.html";
	}

	@PostMapping("/createSale")
	public String createSale(@RequestParam(name = "clientId", required = false) String clientId,
			@RequestParam(name = "beautifySkinItemId", required = false) String beautifySkinItemId,
			@RequestParam(name = "itemNumber", required = false) int itemNumber,
			@RequestParam(name = "createCardTotalAmount", required = false) long createCardTotalAmount,
			@RequestParam(name = "receivedAmount", required = false) long receivedAmount,
			@RequestParam(name = "receivedEarnedAmount", required = false) long receivedEarnedAmount,
			@RequestParam(name = "employeePremium", required = false) float employeePremium,
			@RequestParam(name = "shopPremium", required = false) float shopPremium,
			@RequestParam(name = "createCardDate", required = false) Date createCardDate,
			@RequestParam(name = "sellerId", required = false) String sellerId,
			@RequestParam(name = "description", required = false) String description) {
		int idNums = saleService.getBiggestIdNumber();
		String id = YueHeUtil.getId(IdType.SALE, idNums);
		Sale sale = new Sale();
		sale.setId(id);
		sale.setClient(yueHeCommonService.getClientById(clientId));
		sale.setEmployee(yueHeCommonService.getEmployeeById(sellerId));
		sale.setBeautifySkinItem(yueHeCommonService.getBeautifySkinItemById(beautifySkinItemId));
		sale.setItemNumber(itemNumber);
		sale.setCreateCardTotalAmount(createCardTotalAmount);
		sale.setReceivedAmount(receivedAmount);
		sale.setReceivedEarnedAmount(receivedEarnedAmount);
		sale.setEmployeePremium(employeePremium);
		sale.setShopPremium(shopPremium);
		sale.setCreateCardDate(simpleDateFormat.format(createCardDate));
		sale.setDescription(description);
		LOGGER.info("sale:", sale);

		if (sale != null) {
			LOGGER.info("Saved {}", saleService.create(sale));
		}

		return "redirect:/getSaleList";
	}

	@RequestMapping(value = "/getYueHeAllShopsSales", method = RequestMethod.GET)
	public @ResponseBody YueHeAllSalesPerformanceDetailDTO findAllSalesByYueHe() {
		YueHeAllSalesPerformanceDetailDTO yueHeAllSalesPerformanceDetailDTO = saleService
				.getYueHeAllSalesPerformanceDetail();
		System.out.println(yueHeAllSalesPerformanceDetailDTO);
		return yueHeAllSalesPerformanceDetailDTO;
	}

	@RequestMapping(value = "/getShopAllClientsSales", method = RequestMethod.GET)
	public @ResponseBody ShopAllSalesPerformanceDetailDTO findAllSalesByShop(
			@RequestParam(value = "shopId", required = true) String shopId) {
		System.err.println("shopId-" + shopId);
		ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = saleService
				.getShopAllSalesPerformanceDetail(shopId);
		System.out.println(shopAllSalesPerformanceDetailDTO);
		return shopAllSalesPerformanceDetailDTO;
	}

	@RequestMapping(value = "/getClientAllSales", method = RequestMethod.GET)
	public @ResponseBody ClientAllSalesPerformanceDetailDTO findAllSalesByClient(
			@RequestParam(value = "clientId", required = true) String clientId) {
		System.err.println("clientId-" + clientId);
		ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = saleService
				.getClientAllSalesPerformanceDetail(clientId);
		System.out.println(clientAllSalesPerformanceDetailDTO);
		return clientAllSalesPerformanceDetailDTO;
	}

	@RequestMapping(value = "/getSalePerformanceDetail", method = RequestMethod.GET)
	public @ResponseBody SalePerformanceDetailDTO findSalePerformanceDetailBySaleId(
			@RequestParam(value = "saleId", required = true) String saleId) {
		System.err.println("saleId-" + saleId);
		SalePerformanceDetailDTO salePerformanceDetailDTO = saleService.getSalePerformanceDetail(saleId);
		System.out.println(salePerformanceDetailDTO);
		return salePerformanceDetailDTO;
	}
}
