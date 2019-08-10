package com.yuehe.app.controller;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.entity.Duty;
import com.yuehe.app.service.DutyService;
import com.yuehe.app.service.YueHeCommonService;
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
		List<DutyEmployeeRoleDTO> dutyList =new ArrayList<DutyEmployeeRoleDTO>();
		dutyList = dutyService.getDutyDetailList();
		 LOGGER.info("dutyList {}", dutyList);
		 getDutyNewItemDropDownDataList(model);
		 model.addAttribute("subModule", "dutyList");
		model.addAttribute("dutyList",dutyList);
		
		return "user/dutyList.html";
	}
	@GetMapping("/getDutyNewItem")
	public String dutyNewItem(Model model) {
		model.addAttribute("subModule", "dutyNewItem");
		getDutyNewItemDropDownDataList(model);
		return "user/dutyNewItem.html";
	}
	
	@GetMapping("/duty/edit/{id}")
	public String dutyEditItem(Model model, @PathVariable("id") String id) {
		getDutyNewItemDropDownDataList(model);
		getDutyDetail(model, id);
		return "user/dutyEditItem.html";
	}
	public void getDutyDetail(Model model, String id) {
		Duty duty = dutyService.getDutyById(id);
		model.addAttribute("duty", duty);
	}

	@PostMapping("/duty/update/{id}")
	public String updateDutyItem(Model model, @PathVariable("id") String id, @Valid Duty duty, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of duty edit item and the error message
			LOGGER.debug("result duty:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.duty", result);
			duty.setEmployee(yueHeCommonService.getEmployeeById(duty.getEmployee().getId()));
			duty.setRole(yueHeCommonService.getRoleById(duty.getRole().getId()));
			model.addAttribute("duty", duty);
			return "user/dutyEditItem.html";
		}
		LOGGER.debug("update duty:", duty);
		if (duty != null) {
			LOGGER.info("updated {}", dutyService.create(duty));
		}
		attr.addFlashAttribute("message", "职责分工-" + id + " 更新成功");
		return "redirect:/getDutyList";
	}
/**
	 * 
	 * get the initial data for cosmeticshop, seller and beautify skin item list to
	 * create a new duty item.
	 * 
	 */
	public void getDutyNewItemDropDownDataList(Model model) {

		yueHeCommonService.getAllEmployees(model);
		yueHeCommonService.getAllRoles(model);
	}
	@GetMapping("/duty/delete/{id}")
	public String deleteDutyItem(@PathVariable("id") String id, Duty duty, Model model, RedirectAttributes attr) {
		System.err.println("delete duty item with id=" + id);
		// Duty duty
		LOGGER.info("deleting {}", dutyService.getDutyById(id));
		LOGGER.info("deleting frontend duty{}", duty);
		// dutyService.delete(duty);
		dutyService.deleteById(id);
		attr.addFlashAttribute("message", "职责分工-" + id + " 删除成功");
		return "redirect:/getDutyList";
	}

	@PostMapping("/createDuty")
    public String createDuty( @RequestParam(name = "employeeId", required = false) String employeeId,
                                       @RequestParam(name = "roleId", required = false) String roleId,
                                       @RequestParam(name = "welfare", required = false) int welfare,
									   @RequestParam(name = "description", required = false) String description
									   , RedirectAttributes attr
                                       ) 
	{
		int idNums = dutyService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.DUTY,idNums);
        Duty duty =new Duty();
        duty.setId(id);
        duty.setWelfare(welfare);
        duty.setDescription(description);
        duty.setRole(yueHeCommonService.getRoleById(roleId));
        duty.setEmployee(yueHeCommonService.getEmployeeById(employeeId));
        LOGGER.info("duty:",duty);

        if (duty != null) {
            LOGGER.info("Saved {}", dutyService.create(duty));
        }

		attr.addFlashAttribute("message", "职责分工-" + id + " 创建成功");
        return "redirect:/getDutyList";
    }
	
    

}
