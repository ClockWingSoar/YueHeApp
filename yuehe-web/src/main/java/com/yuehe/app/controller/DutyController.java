package com.yuehe.app.controller;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.yuehe.app.dto.DutyEmployeeRoleDto;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.Duty;
import com.yuehe.app.entity.Duty;
import com.yuehe.app.entity.Role;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.service.DutyService;
import com.yuehe.app.service.RoleService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class DutyController{
	 @ModelAttribute("module")
	    String module() {
	        return "duty";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(DutyController.class);
	
	 private Role role;
	 private Employee employee;
	@Autowired
	private final DutyService dutyService;
	@Autowired
	private final RoleService roleService;
	@Autowired
	private final EmployeeService employeeService;
	public DutyController(DutyService dutyService,
								RoleService roleService,EmployeeService employeeService) {
		this.dutyService = dutyService;
		this.roleService = roleService;
		this.employeeService = employeeService;
	}

	@GetMapping("/getDutyList")
	public  String dutyOverview(Model model){
		// TODO Auto-generated method stub
		List<DutyEmployeeRoleDto> dutyList =new ArrayList<DutyEmployeeRoleDto>();
		dutyList = dutyService.getDutyDetailList();
		 LOGGER.info("dutyList {}", dutyList);
		model.addAttribute("dutyList",dutyList);
		
		return "user/duty";
	}
	@PostMapping("/createDuty")
    public String createduty( @RequestParam(name = "employeeName", required = false) String employeeName,
                                       @RequestParam(name = "roleName", required = false) String roleName,
                                       @RequestParam(name = "welfare", required = false) int welfare,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
		 long idNums = dutyService.getEntityNumber();
        String id = YueHeUtil.getId(9,Math.toIntExact(idNums));
        Duty duty =new Duty();
        duty.setId(id);
        duty.setWelfare(welfare);
        duty.setDescription(description);
        List<Role> roleList = roleService.getRoleByName(roleName);
        for(Role role : roleList) {
        	this.role = role;
        	   LOGGER.debug("role:",role);
        }
        if(role != null)
        	duty.setRoleId(role.getId());
        LOGGER.debug("duty:",duty);
        List<Employee> employeeList = employeeService.getEmployeeByName(employeeName);
        for(Employee employee : employeeList) {
        	this.employee = employee;
        	LOGGER.debug("employee:",employee);
        }
        if(employee != null)
        	duty.setEmployeeId(employee.getId());
        LOGGER.debug("duty:",duty);

        if (duty != null) {
            LOGGER.info("Saved {}", dutyService.create(duty));
        }

        return "redirect:/getDutyList";
    }
	
    

}
