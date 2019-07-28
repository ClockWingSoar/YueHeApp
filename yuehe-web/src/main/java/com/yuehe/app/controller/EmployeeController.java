package com.yuehe.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.yuehe.app.entity.Employee;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.util.IdType;
import com.yuehe.app.util.YueHeUtil;

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

@Controller
public class EmployeeController {
	@ModelAttribute("module")
	String module() {
		return "employee";
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/getEmployeeList")
	public String employeeOverview(Model model) {
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList = employeeService.getAllEmployees();
		LOGGER.info("employeeList {}", employeeList);
		model.addAttribute("employeeList", employeeList);

		return "user/employee.html";
	}

	@PostMapping("/createEmployee")
	public String createemployee(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "salary", required = false) int salary,
			@RequestParam(name = "birthday", required = false) String birthday,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "resigned", required = false) String resigned) {
		int idNums = employeeService.getBiggestIdNumber();
		String id = YueHeUtil.getId(IdType.EMPLOYEE, idNums);
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setSalary(salary);
		try {
			employee.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		employee.setDescription(description);
		employee.setResigned(resigned);
		LOGGER.debug("employee:", employee);

		if (employee != null) {
			LOGGER.info("Saved {}", employeeService.create(employee));
		}

		return "redirect:/getEmployeeList";
	}

}
