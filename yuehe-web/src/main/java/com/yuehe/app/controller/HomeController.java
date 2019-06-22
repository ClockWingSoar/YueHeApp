/**
 * 
 */
package com.yuehe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class HomeController
{
	
	 @ModelAttribute("module")
	    String module() {
	        return "home";
	    }
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home/homeNotSignedIn.html";
	}
}
