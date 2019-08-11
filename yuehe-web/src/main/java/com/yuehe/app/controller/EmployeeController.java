package com.yuehe.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
	@ModelAttribute("module")
	String module() {
		return "employee";
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
		model.addAttribute("subModule", "employeeList");
		model.addAttribute("employeeList", employeeList);

		return "user/employeeList.html";
	}
	@GetMapping("/getEmployeeNewItem")
	public String employeeNewItem(Model model) {
		model.addAttribute("subModule", "employeeNewItem");

		return "user/employeeNewItem.html";
	}
	
	@GetMapping("/employee/edit/{id}")
	public String employeeEditItem(Model model, @PathVariable("id") String id) {
		getEmployeeDetail(model, id);
		return "user/employeeEditItem.html";
	}
	public void getEmployeeDetail(Model model, String id) {
		Employee employee = employeeService.getById(id);
		model.addAttribute("employee", employee);
	}

	@PostMapping("/employee/update/{id}")
	public String updateEmployeeItem(Model model, @PathVariable("id") String id, @Valid Employee employee, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of employee edit item and the error message
			LOGGER.debug("result employee:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
			model.addAttribute("employee", employee);
			return "user/employeeEditItem.html";
		}
		LOGGER.debug("update employee:", employee);
		try {
			employee.setBirthday(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(employee.getBirthday())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (employee != null) {
			LOGGER.info("updated {}", employeeService.create(employee));
		}
		attr.addFlashAttribute("message", "员工-" + id + " 更新成功");
		return "redirect:/getEmployeeList";
	}

	@GetMapping("/employee/delete/{id}")
	public String deleteEmployeeItem(@PathVariable("id") String id, Employee employee, Model model, RedirectAttributes attr) {
		System.err.println("delete employee item with id=" + id);
		// Employee employee
		LOGGER.info("deleting {}", employeeService.getById(id));
		LOGGER.info("deleting frontend employee{}", employee);
		// employeeService.delete(employee);
		employeeService.deleteById(id);
		attr.addFlashAttribute("message", "员工-" + id + " 删除成功");
		return "redirect:/getEmployeeList";
	}
	@PostMapping("/createEmployee")
	public String createemployee(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "salary", required = false) int salary,
			@RequestParam(name = "birthday", required = false) Date birthday,
			@RequestParam(name = "description", required = false) String description,
			RedirectAttributes attr,
			@RequestParam(name = "resigned", required = false) String resigned) {
		int idNums = employeeService.getBiggestIdNumber();
		String id = YueHeUtil.getId(IdType.EMPLOYEE, idNums);
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setSalary(salary);
		employee.setBirthday(simpleDateFormat.format(birthday));
		employee.setDescription(description);
		employee.setResigned(resigned);
		LOGGER.info("employee:", employee);

		if (employee != null) {
			LOGGER.info("Saved {}", employeeService.create(employee));
		}

		attr.addFlashAttribute("message", "员工-" + id + " 创建成功");
		return "redirect:/getEmployeeList";
	}

}
