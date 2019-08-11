package com.yuehe.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.Role;
import com.yuehe.app.service.RoleService;
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
public class RoleController{
	 @ModelAttribute("module")
	    String module() {
	        return "role";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private final RoleService roleService;
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping("/getRoleList")
	public  String roleOverview(Model model){
		List<Role> roleList =new ArrayList<Role>();
		roleList = roleService.getAllRoles();
		 LOGGER.info("roleList {}", roleList);
		 model.addAttribute("subModule", "roleList");
		 model.addAttribute("roleList",roleList);
		
		return "user/roleList.html";
	}
	@GetMapping("/getRoleNewItem")
	public String roleNewItem(Model model) {
		model.addAttribute("subModule", "roleNewItem");

		return "user/roleNewItem.html";
	}
	
	@GetMapping("/role/edit/{id}")
	public String roleEditItem(Model model, @PathVariable("id") String id) {
		getRoleDetail(model, id);
		return "user/roleEditItem.html";
	}
	public void getRoleDetail(Model model, String id) {
		Role role = roleService.getById(id);
		model.addAttribute("role", role);
	}

	@PostMapping("/role/update/{id}")
	public String updateRoleItem(Model model, @PathVariable("id") String id, @Valid Role role, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of role edit item and the error message
			LOGGER.debug("result role:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
			model.addAttribute("role", role);
			return "user/roleEditItem.html";
		}
		LOGGER.debug("update role:", role);
		if (role != null) {
			LOGGER.info("updated {}", roleService.create(role));
		}
		attr.addFlashAttribute("message", "角色-" + id + " 更新成功");
		return "redirect:/getRoleList";
	}

	@GetMapping("/role/delete/{id}")
	public String deleteRoleItem(@PathVariable("id") String id, Role role, Model model, RedirectAttributes attr) {
		System.err.println("delete role item with id=" + id);
		// Role role
		LOGGER.info("deleting {}", roleService.getById(id));
		LOGGER.info("deleting frontend role{}", role);
		// roleService.delete(role);
		roleService.deleteById(id);
		attr.addFlashAttribute("message", "角色-" + id + " 删除成功");
		return "redirect:/getRoleList";
	}
	@PostMapping("/createRole")
    public String createRole( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "responsibility", required = false) String responsibility,
									   RedirectAttributes attr,
									   @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        int idNums = roleService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.ROLE,idNums);
        Role role =new Role();
        role.setId(id);
        role.setName(name);
        role.setResponsibility(responsibility);
        role.setDescription(description);
        LOGGER.info("role:",role);

        if (role != null) {
            LOGGER.info("Saved {}", roleService.create(role));
        }


		attr.addFlashAttribute("message", "角色-" + id + " 创建成功");
        return "redirect:/getRoleList";
    }
	
    

}
