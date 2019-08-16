package com.yuehe.app.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yuehe.app.common.PaginationAndSortModel;
import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.dto.ProfileDetailDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.property.BaseProperty;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.ProfileService;
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
public class ClientController {
	@ModelAttribute("module")
	String module() {
		return "client";
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
	private PaginationAndSortModel paginationAndSortModel = new PaginationAndSortModel();

	@Autowired
	private final ClientService clientService;
	@Autowired
	private final ProfileService profileService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;

	public ClientController(ClientService clientService,ProfileService profileService, YueHeCommonService yueHeCommonService) {
		this.clientService = clientService;
		this.profileService = profileService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getClientList")
	public String clientOverview(HttpServletRequest request,Model model) {
	
		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		Page<Client> clientPage = clientService.getClientsDetailListWithPaginationAndSort(paginationAndSortModel);
		buildModelAfterDBQuerying(clientPage, model, sortProperty, sortDirection);
		
		return "user/clientList.html";
	}
	private void buildModelAfterDBQuerying(Page<Client> clientPage, Model model, String sortProperty,
	Direction sortDirection) {
		// @SuppressWarnings("unchecked")
		// Page<ClientDetailDTO> clientMapPage = (Page<ClientDetailDTO>) clientMap.get("clientPage");
		List<Sort.Order> sortOrders = clientPage.getSort().stream().collect(Collectors.toList());
		ServiceUtil.setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		sortOrders.forEach(l -> System.out.println(l));
		LOGGER.info("clientPage {}", clientPage);
		model.addAttribute("clientPage",clientPage);
		model.addAttribute("clientList",clientPage.getContent());
		model.addAttribute("subModule", "clientList");
	}
	
	@GetMapping("/getClientNewItem")
	public String clientNewItem(Model model) {
		getClientNewItemDropDownDataList(model);
		model.addAttribute("subModule", "clientNewItem");

		return "user/clientNewItem.html";
	}
	@GetMapping(value = "/clients/search")
	public String getClientsWithFiltering(HttpServletRequest request, Model model) {
		String searchParameters = "";
		if (!StringUtils.isEmpty(request.getParameter("searchParameters"))) {
			searchParameters = request.getParameter("searchParameters");
		}
		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		Page<Client> clientPage = clientService.getClientsDetailListWithPaginationAndSortAndFiltering(paginationAndSortModel,searchParameters);
		buildModelAfterDBQuerying(clientPage, model, sortProperty, sortDirection);
		model.addAttribute("searchParameters", searchParameters);

		return "user/clientList.html";
	}

	/**
	 * Handle request to download an Excel document
	 */
	@GetMapping("/clientCsvDownload")
	public void clientCsvDownload(Model model, HttpServletRequest request, HttpServletResponse response) {

		ServiceUtil.buildSortOrderBeforeDBQuerying(request,paginationAndSortModel);
		String sortProperty = paginationAndSortModel.getSortProperty();
		Direction sortDirection = paginationAndSortModel.getSortDirection();
		Map<String, Object> clientMap = clientService.getClientsDetailListForDownload(sortDirection, sortProperty);

		String[] headers = {"cosmeticShopName" , "id","name","age", "gender","symptom" };
		clientMap.put("headers", headers);
		@SuppressWarnings("unchecked")
		List<Sort.Order> sortOrders = (List<Sort.Order>) clientMap.get("sortOrders");
		ServiceUtil.setBackSortOrderAfterDBQuerying(sortOrders, model, sortProperty, sortDirection);
		try {
			new CsvView().buildCsvDocument(clientMap, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}


	@GetMapping("/client/edit/{id}")
	public String clientEditItem(Model model, @PathVariable("id") String id) {
		getClientDetail(model, id);
		return "user/clientEditItem.html";
	}
	/**
	 * 
	 * get the initial data for cosmeticshop, seller and beautify skin item list to
	 * create a new sale item.
	 * 
	 */
	public void getClientNewItemDropDownDataList(Model model) {
		List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
		List<DutyEmployeeRoleDTO> dutyList = yueHeCommonService.getAllPersonByRoleName(BaseProperty.YUEHE_ROLE_OPERATOR);
		List<Tool> toolList = yueHeCommonService.getAllTools();

		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("toolList", toolList);
		model.addAttribute("dutyList", dutyList);
	}
	public void getClientDetail(Model model, String id) {
		getClientNewItemDropDownDataList(model);
		Client client = clientService.getById(id);
		model.addAttribute("client", client);
	}

	@PostMapping("/client/update/{id}")
	public String updateClientItem(Model model, @PathVariable("id") String id, @Valid Client client, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// client.setId(id);

			List<CosmeticShop> cosmeticShopList = yueHeCommonService.getAllCosmeticShops();
			// to add back the initial data of client edit item and the error message
			attr.addFlashAttribute("org.springframework.validation.BindingResult.client", result);
			client.setCosmeticShop(yueHeCommonService.getCosmeticShopById(client.getCosmeticShop().getId()));
			model.addAttribute("client", client);
			model.addAttribute("cosmeticShopList", cosmeticShopList);
			return "user/clientEditItem.html";
		}
		LOGGER.debug("update client:", client);

		if (client != null) {
			LOGGER.info("updated {}", clientService.create(client));
		}
		attr.addFlashAttribute("message", "客户-" + id + " 更新成功");
		return "redirect:/getClientList";
	}

	@GetMapping("/client/delete/{id}")
	public String deleteClientItem(@PathVariable("id") String id, Client client, Model model, RedirectAttributes attr) {
		System.err.println("delete client item with id=" + id);
		// Client client
		LOGGER.info("deleting {}", clientService.getById(id));
		LOGGER.info("deleting frontend client{}", client);
		// clientService.delete(client);
		clientService.deleteById(id);
		attr.addFlashAttribute("message", "客户-" + id + " 删除成功");
		return "redirect:/getClientList";
	}
	@PostMapping("/createClient")
	public String createClient(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
			@RequestParam(name = "age", required = false) int age,
			@RequestParam(name = "gender", required = false) String gender,
			@RequestParam(name = "symptom", required = false) String symptom, 
			RedirectAttributes attr) {
		int idNums = clientService.getBiggestIdNumber();
		String id = YueHeUtil.getId(IdType.CLIENT, idNums);
		Client client = new Client();
		client.setId(id);
		client.setName(name);
		client.setCosmeticShop(yueHeCommonService.getCosmeticShopById(cosmeticShopId));
		client.setAge(age);
		client.setGender(gender);
		client.setSymptom(symptom);
		LOGGER.info("client:", client);

		if (client != null) {
			LOGGER.info("Saved {}", clientService.create(client));
		}
		attr.addFlashAttribute("message", "客户-" + id + " 创建成功");
		return "redirect:/getClientList";
	}
	@GetMapping("/getClientProfile")
	public  String clientProfile(Model model){
		List<CosmeticShop> cosmeticShopList =  yueHeCommonService.getAllCosmeticShops();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("subModule", "clientProfile");
		return "user/clientProfile.html";
	}
	@RequestMapping(value = "/getProfileDetail", method = RequestMethod.GET)
	public @ResponseBody ProfileDetailDTO getProfileByClientId(
			@RequestParam(value = "clientId", required = true) String clientId) {
		ProfileDetailDTO profileDetailDTO = profileService.getProfileByClientId(clientId,null,null);
		System.out.println(profileDetailDTO);
		LOGGER.info("ProfileDetailDTO{}",profileDetailDTO);
		return profileDetailDTO;
	}

}
