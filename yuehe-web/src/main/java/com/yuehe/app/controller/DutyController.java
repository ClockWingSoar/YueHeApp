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
import com.yuehe.app.entity.Duty;
import com.yuehe.app.service.DutyService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class DutyController{
	 @ModelAttribute("module")
	    String module() {
	        return "duty";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(DutyController.class);
	
	@Autowired
	private final DutyService dutyService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	public DutyController(DutyService dutyService,YueHeCommonService yueHeCommonService) {
		this.dutyService = dutyService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getDutyList")
	public  String dutyOverview(Model model){
		// TODO Auto-generated method stub
		List<DutyEmployeeRoleDto> dutyList =new ArrayList<DutyEmployeeRoleDto>();
		dutyList = dutyService.getDutyDetailList();
		 LOGGER.info("dutyList {}", dutyList);
		 yueHeCommonService.getAllEmployees(model);
		 yueHeCommonService.getAllRoles(model);
		model.addAttribute("dutyList",dutyList);
		
		return "user/duty.html";
	}

	@PostMapping("/createDuty")
    public String createDuty( @RequestParam(name = "employeeId", required = false) String employeeId,
                                       @RequestParam(name = "roleId", required = false) String roleId,
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
        duty.setRoleId(roleId);
        duty.setEmployeeId(employeeId);
        LOGGER.debug("duty:",duty);

        if (duty != null) {
            LOGGER.info("Saved {}", dutyService.create(duty));
        }

        return "redirect:/getDutyList";
    }
	
    

}
