package com.yuehe.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuehe.app.config.BeanConfigurations;
import com.yuehe.app.entity.User;
import com.yuehe.app.service.UserService;
import com.yuehe.app.util.YueHeUtil;

@Controller
public class UserController {
 
	 @ModelAttribute("module")
	    String module() {
	        return "user";
	    }
//    @RequestMapping  adding this will cause frontend-not picking up js files, not sure why
//    public String viewUserProfile() {
//        return "user/profile";
//    }
   
	 private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	 @Autowired
		private final BeanConfigurations beanConfigurations;
	@Autowired
	private final UserService userService;
	
	public UserController(UserService userService,BeanConfigurations beanConfigurations) {
		this.userService = userService;
		this.beanConfigurations = beanConfigurations;
	}

	@GetMapping("/getUserList")
	public  String UserOverview(Model model){
		// TODO Auto-generated method stub
		List<User> userList =new ArrayList<User>();
		userList = userService.getAllUser();
		 LOGGER.info("userList:{}", userList);
		model.addAttribute("userList",userList);
		
		return "user/user";
	}
	@PostMapping("/createUser")
   public String createUser( @RequestParam(name = "username", required = false) String username,
                                      @RequestParam(name = "password", required = false) String password,
                                      @RequestParam(name = "role", required = false) String role) {
       List<User> userList = new ArrayList<>();
       int idNums = userService.getAllUser().size();
       String id = YueHeUtil.getId(8,idNums);
       User user =new User();
       user.setId(id);
       user.setUsername(username);
       user.setPassword(beanConfigurations.passwordEncoder().encode(password));
       user.setRole(role);
       userList.add(user);
       LOGGER.debug("User:",user);
      // }

       if (!userList.isEmpty()) {
           LOGGER.info("Saved {}", userService.saveAll(userList));
       }

       return "redirect:/getUserList";
      // return "UserOverview";
   }
	
}