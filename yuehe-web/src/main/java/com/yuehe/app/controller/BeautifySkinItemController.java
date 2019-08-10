package com.yuehe.app.controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.service.BeautifySkinItemService;
import com.yuehe.app.util.IdType;
import com.yuehe.app.util.YueHeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class BeautifySkinItemController{
	 @ModelAttribute("module")
	    String module() {
	        return "beautifySkinItem";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(BeautifySkinItemController.class);
    private String message;
	
	@Autowired
	private final BeautifySkinItemService beautifySkinItemService;
	public BeautifySkinItemController(BeautifySkinItemService beautifySkinItemService) {
		this.beautifySkinItemService = beautifySkinItemService;
	}

	@GetMapping("/getBeautifySkinItemList")
	public  String beautifySkinItemOverview(Model model){
		List<BeautifySkinItem> beautifySkinItemList =new ArrayList<BeautifySkinItem>();
		beautifySkinItemList = beautifySkinItemService.getAllBeautifySkinItem();
		 LOGGER.info("beautifySkinItemList {}", beautifySkinItemList);
		model.addAttribute("message",message);
		model.addAttribute("beautifySkinItemList",beautifySkinItemList);
		
		return "user/beautifySkinItem.html";
	}
	@PostMapping("/createBeautifySkinItem")
    public String createBeautifySkinItem( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "price", required = false) String price,
                                       @RequestParam(name = "description", required = false) String description) {
        List<BeautifySkinItem> beautifySkinItemList = new ArrayList<>();
        int idNums = beautifySkinItemService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.BEAUTIFY_SKIN_ITEM,idNums);
        BeautifySkinItem beautifySkinItem =new BeautifySkinItem();
        beautifySkinItem.setId(id);
        beautifySkinItem.setName(name);
        beautifySkinItem.setPrice(Integer.parseInt(price));
        beautifySkinItem.setDescription(description);
        beautifySkinItemList.add(beautifySkinItem);
        LOGGER.info("beautifySkinItem:",beautifySkinItem);
       // }

        if (!beautifySkinItemList.isEmpty()) {
            LOGGER.info("Saved {}", beautifySkinItemService.saveAll(beautifySkinItemList));
        }

        return "redirect:/getBeautifySkinItemList";
       // return "beautifySkinItemOverview";
    }
	
    

}
