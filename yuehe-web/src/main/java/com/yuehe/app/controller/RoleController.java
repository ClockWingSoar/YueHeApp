package com.yuehe.app.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.yuehe.app.entity.Role;
import com.yuehe.app.service.RoleService;
import com.yuehe.app.util.YueHeUtil;


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
		// TODO Auto-generated method stub
		List<Role> roleList =new ArrayList<Role>();
		roleList = roleService.getAllRoles();
		 LOGGER.info("roleList {}", roleList);
		model.addAttribute("roleList",roleList);
		
		return "user/role";
	}
	@PostMapping("/createRole")
    public String createrole( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "responsibility", required = false) String responsibility,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        long idNums = roleService.getEntityNumber();
        String id = YueHeUtil.getId(5,Math.toIntExact(idNums));
        Role role =new Role();
        role.setId(id);
        role.setName(name);
        role.setResponsibility(responsibility);
        role.setDescription(description);
        LOGGER.debug("role:",role);

        if (role != null) {
            LOGGER.info("Saved {}", roleService.create(role));
        }

        return "redirect:/getRoleList";
    }
	
    

}
