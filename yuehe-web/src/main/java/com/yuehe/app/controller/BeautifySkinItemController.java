package com.yuehe.app.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.repository.interfaces.BeautifySkinItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;



@Controller
public class BeautifySkinItemController{
	  // inject via application.properties
    @Value("${welcome.message}")
    private String message;
	
	@Autowired
	BeautifySkinItemRepository beautifySkinItemRepository;
	
	@RequestMapping(value = "/greeting") 
	public ModelAndView test(ModelAndView mv) {
		mv.setViewName("/greeting"); 
		mv.addObject("title","欢迎使用Thymeleaf!"); 
		return mv; }

	@GetMapping("/")
	public  String beautifySkinItemOverview(Model model){
		// TODO Auto-generated method stub
		List<BeautifySkinItem> beautifySkinItemList =new ArrayList<BeautifySkinItem>();
		beautifySkinItemList = beautifySkinItemRepository.findAll();
		model.addAttribute("message",message);
		model.addAttribute("beautifySkinItemList",beautifySkinItemList);
		
		return "beautifySkinItemOverview";
	}
    

}
