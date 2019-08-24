package com.yuehe.app.service;

import java.util.List;

import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.entity.Role;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.Tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

/**
 * This class is use to collect all common util methods such as get all cosmetic
 * shop list, get all employee, role, client list, etc
 * 
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)
public class YueHeCommonService {
	private static final Logger LOGGER = LoggerFactory.getLogger(YueHeCommonService.class);
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	@Autowired
	private final RoleService roleService;
	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final BeautifySkinItemService beautifySkinItemService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final DutyService dutyService;
	@Autowired
	private final ToolService toolService;
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final OperationService operationService;

	public YueHeCommonService(CosmeticShopService cosmeticShopService, RoleService roleService,
			EmployeeService employeeService, BeautifySkinItemService beautifySkinItemService,
			ClientService clientService, DutyService dutyService, ToolService toolService, SaleService saleService,
			OperationService operationService) {
		super();
		this.cosmeticShopService = cosmeticShopService;
		this.roleService = roleService;
		this.employeeService = employeeService;
		this.beautifySkinItemService = beautifySkinItemService;
		this.clientService = clientService;
		this.dutyService = dutyService;
		this.toolService = toolService;
		this.saleService = saleService;
		this.operationService = operationService;
	}

	public List<CosmeticShop> getAllCosmeticShops() {
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		return cosmeticShopList;
	}

	public List<DutyEmployeeRoleDTO> getAllPersonByRoleName(String roleName) {
		List<DutyEmployeeRoleDTO> dutyList = dutyService.getAllPersonByRoleName(roleName);
		dutyList.forEach(l -> System.out.println(l));
		LOGGER.info("dutyList{}",dutyList);
		return dutyList;
	}

	public List<Tool> getAllTools() {
		List<Tool> toolList = toolService.getAllTools();
		return toolList;
	}

	public List<BeautifySkinItem> getAllBeautifySkinItems() {
		List<BeautifySkinItem> beautifySkinItemList = beautifySkinItemService.getAllBeautifySkinItem();
		return beautifySkinItemList;
	}

	public void getAllEmployees(Model model) {
		List<Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employeeList", employeeList);
	}

	public void getAllRoles(Model model) {
		List<Role> roleList = roleService.getAllRoles();
		model.addAttribute("roleList", roleList);
	}

	public Client getClientById(String clientId) {
		return clientService.getById(clientId);
	}
	public Client getClientForQuestionareById(String clientId) {
		return clientService.getClientForQuestionareById(clientId);
	}

	public CosmeticShop getCosmeticShopById(String shopId) {
		return cosmeticShopService.getById(shopId);
	}

	public Tool getToolById(String toolId) {
		return toolService.getById(toolId);
	}
	public Sale getSaleById(String saleId) {
		return saleService.getById(saleId);
	}
	public Operation getOperationById(String operationId) {
		return operationService.getById(operationId);
	}

	public Employee getEmployeeById(String employeeId) {
		return employeeService.getById(employeeId);
	}
	public Role getRoleById(String roleId) {
		return roleService.getById(roleId);
	}

	public BeautifySkinItem getBeautifySkinItemById(String beautifySkinItemId) {
		return beautifySkinItemService.getById(beautifySkinItemId);
	}

}
