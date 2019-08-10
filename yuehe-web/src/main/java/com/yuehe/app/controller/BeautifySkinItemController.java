package com.yuehe.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.service.BeautifySkinItemService;
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
public class BeautifySkinItemController{
	 @ModelAttribute("module")
	    String module() {
	        return "beautifySkinItem";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(BeautifySkinItemController.class);
	
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
         model.addAttribute("subModule", "beautifySkinItemList");
		model.addAttribute("beautifySkinItemList",beautifySkinItemList);
		
		return "user/beautifySkinItemList.html";
    }
    @GetMapping("/getBeautifySkinItemNewItem")
	public String beautifySkinItemNewItem(Model model) {
		model.addAttribute("subModule", "beautifySkinItemNewItem");

		return "user/beautifySkinItemNewItem.html";
	}
	
	@GetMapping("/beautifySkinItem/edit/{id}")
	public String beautifySkinItemEditItem(Model model, @PathVariable("id") String id) {
		getBeautifySkinItemDetail(model, id);
		return "user/beautifySkinItemEditItem.html";
	}
	public void getBeautifySkinItemDetail(Model model, String id) {
		BeautifySkinItem beautifySkinItem = beautifySkinItemService.getById(id);
		model.addAttribute("beautifySkinItem", beautifySkinItem);
	}

	@PostMapping("/beautifySkinItem/update/{id}")
	public String updateBeautifySkinItemItem(Model model, @PathVariable("id") String id, @Valid BeautifySkinItem beautifySkinItem, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of beautifySkinItem edit item and the error message
			LOGGER.debug("result beautifySkinItem:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.beautifySkinItem", result);
			model.addAttribute("beautifySkinItem", beautifySkinItem);
			return "user/beautifySkinItemEditItem.html";
		}
		LOGGER.debug("update beautifySkinItem:", beautifySkinItem);
		if (beautifySkinItem != null) {
			LOGGER.info("updated {}", beautifySkinItemService.create(beautifySkinItem));
		}
		attr.addFlashAttribute("message", "美肤项目-" + id + " 更新成功");
		return "redirect:/getBeautifySkinItemList";
	}

	@GetMapping("/beautifySkinItem/delete/{id}")
	public String deleteBeautifySkinItemItem(@PathVariable("id") String id, BeautifySkinItem beautifySkinItem, Model model, RedirectAttributes attr) {
		System.err.println("delete beautifySkinItem item with id=" + id);
		// BeautifySkinItem beautifySkinItem
		LOGGER.info("deleting {}", beautifySkinItemService.getById(id));
		LOGGER.info("deleting frontend beautifySkinItem{}", beautifySkinItem);
		// beautifySkinItemService.delete(beautifySkinItem);
		beautifySkinItemService.deleteById(id);
		attr.addFlashAttribute("message", "美肤项目-" + id + " 删除成功");
		return "redirect:/getBeautifySkinItemList";
	}
	@PostMapping("/createBeautifySkinItem")
    public String createBeautifySkinItem( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "price", required = false) String price,
                                       RedirectAttributes attr,
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

        if (!beautifySkinItemList.isEmpty()) {
            LOGGER.info("Saved {}", beautifySkinItemService.saveAll(beautifySkinItemList));
        }

		attr.addFlashAttribute("message", "美肤项目-" + id + " 创建成功");
        return "redirect:/getBeautifySkinItemList";
    }
	
    

}
