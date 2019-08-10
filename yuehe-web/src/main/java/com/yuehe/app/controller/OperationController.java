package com.yuehe.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yuehe.app.common.PaginationAndSortModel;
import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.DutyEmployeeRoleDTO;
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
import com.yuehe.app.util.IdType;
import com.yuehe.app.util.ServiceUtil;
import com.yuehe.app.util.YueHeUtil;
import com.yuehe.app.view.CsvView;

import org.apache.commons.lang3.StringUtils;
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
public class OperationController{
	 @ModelAttribute("module")
	    String module() {
	        return "operation";
	    }
	private final static Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	private PaginationAndSortModel paginationAndSortModel = new PaginationAndSortModel();
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
	public  String operationOverview(HttpServletRequest request, Model model){
		
		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		Page<Operation> operationPage = operationService.getOperationsDetailListWithPaginationAndSort(paginationAndSortModel);
		buildModelAfterDBQuerying(operationPage, model, sortProperty, sortDirection);
		
		return "user/operationList.html";
	}
	private void buildModelAfterDBQuerying(Page<Operation> operationPage, Model model, String sortProperty,
	Direction sortDirection) {
		// @SuppressWarnings("unchecked")
		// Page<OperationDetailDTO> operationMapPage = (Page<OperationDetailDTO>) operationMap.get("operationPage");
		List<Sort.Order> sortOrders = operationPage.getSort().stream().collect(Collectors.toList());
		ServiceUtil.setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		sortOrders.forEach(l -> System.out.println(l));
		LOGGER.info("operationPage {}", operationPage);
		model.addAttribute("operationPage",operationPage);
		model.addAttribute("operationList",operationPage.getContent());
		model.addAttribute("subModule", "operationList");
	}
	
	@GetMapping("/getOperationNewItem")
	public String operationNewItem(Model model) {
		getOperationNewItemDropDownDataList(model);
		model.addAttribute("subModule", "operationNewItem");

		return "user/operationNewItem.html";
	}
	// @GetMapping("/getOperationNewItem")
	// public  String operationNewItemOverview(Model model){
	// 	List<CosmeticShop> cosmeticShopList =  yueHeCommonService.getAllCosmeticShops();
	// 	List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_OPERATOR);
	// 	List<Tool> toolList = yueHeCommonService.getAllTools();
		
	// 	model.addAttribute("cosmeticShopList", cosmeticShopList);
	// 	model.addAttribute("toolList", toolList);
	// 	model.addAttribute("dutyList", dutyList);
	// 	model.addAttribute("subModule", "operationNewItem");
	// 	return "user/operationNewItem.html";
	// }
	@GetMapping(value = "/operations/search")
	public String getOperationsWithFiltering(HttpServletRequest request, Model model) {
		String searchParameters = "";
		if (!StringUtils.isEmpty(request.getParameter("searchParameters"))) {
			searchParameters = request.getParameter("searchParameters");
		}
		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		// Integer pageSize = paginationAndSortModel.getPageSize();
		// Integer pageNumber = paginationAndSortModel.getPageNumber();
		// String search = "id:xs000~,'itemNumber>5";
		Page<Operation> operationPage = operationService.getOperationsDetailListWithPaginationAndSortAndFiltering(paginationAndSortModel,searchParameters);
		buildModelAfterDBQuerying(operationPage, model, sortProperty, sortDirection);
		model.addAttribute("searchParameters", searchParameters);

		return "user/operationList.html";
	}

	/**
	 * Handle request to download an Excel document
	 */
	@GetMapping("/operationCsvDownload")
	public void operationCsvDownload(Model model, HttpServletRequest request, HttpServletResponse response) {

		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		Map<String, Object> operationMap = operationService.getOperationsDetailListForDownload(sortDirection, sortProperty);

		String[] headers = { "cosmeticShopName","discount", "clientName","saleId", "beautifySkinItemName","createCardDate", 
		 "createCardTotalAmount", "itemNumber","restItemNumber",  "employeePremium", "shopPremium","earnedAmount", "consumedAmount", "consumedEarnedAmount",  "advancedEarnedAmount",
				"operationId",  "operatorName", "toolName","operationDate", "operateExpense","description" };
		operationMap.put("headers", headers);
		@SuppressWarnings("unchecked")
		List<Sort.Order> sortOrders = (List<Sort.Order>) operationMap.get("sortOrders");
		ServiceUtil.setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		try {
			new CsvView().buildCsvDocument(operationMap, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		// return " "; to fix the issue of java.lang.IllegalStateException:
		// getOutputStream() has already been called for this
		// responsejava.lang.IllegalStateException: getOutputStream() has already been
		// called for this response
	}



	// private void buildModelAfterDBQuerying(Map<String, Object> operationMap, Model model, String sortProperty,
	// 		Direction sortDirection) {
	// 	@SuppressWarnings("unchecked")
	// 	Page<OperationClientItemSellerForDBDTO> operationMapPage = (Page<OperationClientItemSellerForDBDTO>) operationMap.get("operationPage");
	// 	List<Sort.Order> sortOrders = operationMapPage.getSort().stream().collect(Collectors.toList());
	// 	yueHeCommonService.setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
	// 	sortOrders.forEach(l -> System.out.println(l));
	// 	LOGGER.info("operationMap {}", operationMap);
	// 	model.addAllAttributes(operationMap);
	// 	model.addAttribute("subModule", "operationList");
	// }




	@GetMapping("/operation/edit/{id}")
	public String operationEditItem(Model model, @PathVariable("id") String id) {
		getOperationDetail(model, id);
		return "user/operationEditItem.html";
	}
	/**
	 * 
	 * get the initial data for cosmeticshop, seller and beautify skin item list to
	 * create a new sale item.
	 * 
	 */
	public void getOperationNewItemDropDownDataList(Model model) {
		List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
		List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_OPERATOR);
		List<Tool> toolList = yueHeCommonService.getAllTools();

		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("dutyList", dutyList);
	}
	public void getOperationDetail(Model model, String id) {
		getOperationNewItemDropDownDataList(model);
		Operation operation = operationService.getById(id);
		model.addAttribute("operation", operation);
	}

	@PostMapping("/operation/update/{id}")
	public String updateOperationItem(Model model, @PathVariable("id") String id, @Valid Operation operation, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// operation.setId(id);

			List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
			List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService
					.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_OPERATOR);
			List<Tool> toolList = yueHeCommonService.getAllTools();
			// to add back the initial data of operation edit item and the error message
			attr.addFlashAttribute("org.springframework.validation.BindingResult.operation", result);
			// attr.addFlashAttribute("operation", operation);
			// attr.addFlashAttribute("cosmeticShopList", cosmeticShopList);
			// attr.addFlashAttribute("dutyList", dutyList);
			// attr.addFlashAttribute("beautifySkinItemList", beautifySkinItemList);
			// to set back the operation client name for client list dropdown since form object
			// "operation" won't save client name
			// only save the client id to the object "operation"
			// operation.getSale().setClient(clientService.getById(operation.getSale().getClient().getId()));
			operation.setSale(yueHeCommonService.getSaleById(operation.getSale().getId()));
			operation.setEmployee(yueHeCommonService.getEmployeeById(operation.getEmployee().getId()));
			operation.setTool(yueHeCommonService.getToolById(operation.getTool().getId()));
			model.addAttribute("operation", operation);
			model.addAttribute("cosmeticShopList", cosmeticShopList);
			model.addAttribute("toolList", toolList);
			model.addAttribute("dutyList", dutyList);
			return "user/operationEditItem.html";
		}
		LOGGER.debug("update operation:", operation);
		try {
			operation.setOperationDate(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(operation.getOperationDate())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (operation != null) {
			LOGGER.info("updated {}", operationService.create(operation));
		}
		attr.addFlashAttribute("message", "操作记录-" + id + " 更新成功");
		return "redirect:/getOperationList";
	}

	@GetMapping("/operation/delete/{id}")
	public String deleteOperationItem(@PathVariable("id") String id, Operation operation, Model model, RedirectAttributes attr) {
		System.err.println("delete operation item with id=" + id);
		// Operation operation
		LOGGER.info("deleting {}", operationService.getById(id));
		LOGGER.info("deleting frontend operation{}", operation);
		// operationService.delete(operation);
		operationService.deleteById(id);
		attr.addFlashAttribute("message", "操作记录-" + id + " 删除成功");
		return "redirect:/getOperationList";
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
						            @RequestParam(name = "description", required = false) String description, RedirectAttributes attr
                                       ) 
	{
        int idNums = operationService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.OPERATION,idNums);
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
		attr.addFlashAttribute("message", "操作记录-" + id + " 创建成功");
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
	YueHeAllShopsDetailDTO  findAllOperationsByYueHe() {
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
