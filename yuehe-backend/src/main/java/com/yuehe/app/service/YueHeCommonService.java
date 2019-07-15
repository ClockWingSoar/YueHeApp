package com.yuehe.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.yuehe.app.dto.ClientShopDto;
import com.yuehe.app.dto.DutyEmployeeRoleDto;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Role;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.repository.ClientRepository;

/**
 * This class is use to collect all common util methods such as get all cosmetic shop list, get all employee, role, client list, etc
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = true)
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

	public void getAllCosmeticShops(Model model) {
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
	}

	public void getAllPersonByRoleName(Model model,String roleName) {
		List<DutyEmployeeRoleDto> dutyList = dutyService.getAllPersonByRoleName(roleName);
		dutyList.forEach(l -> System.out.println(l));
		model.addAttribute("dutyList", dutyList);
	}
	public void getAllTools(Model model) {
		List<Tool> toolList = toolService.getAllTools();
		model.addAttribute("toolList", toolList);
	}
	public void getAllBeautifySkinItems(Model model) {
		List<BeautifySkinItem> beautifySkinItemList = beautifySkinItemService.getAllBeautifySkinItem();
		model.addAttribute("beautifySkinItemList", beautifySkinItemList);
	}
	public void getAllEmployees(Model model) {
		List<Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("employeeList", employeeList);
	}
	public void getAllRoles(Model model) {
		List<Role> roleList = roleService.getAllRoles();
		model.addAttribute("roleList", roleList);
	}
}
