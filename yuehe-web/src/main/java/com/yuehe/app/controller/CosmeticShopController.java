package com.yuehe.app.controller;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.service.CosmeticShopService;
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
public class CosmeticShopController{
	 @ModelAttribute("module")
	    String module() {
	        return "cosmeticShop";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(CosmeticShopController.class);
	
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	public CosmeticShopController(CosmeticShopService cosmeticShopService) {
		this.cosmeticShopService = cosmeticShopService;
	}

	@GetMapping("/getCosmeticShopList")
	public  String cosmeticShopOverview(Model model){
		List<CosmeticShop> cosmeticShopList =new ArrayList<CosmeticShop>();
		cosmeticShopList = cosmeticShopService.getAllCosmeticShop();
		 LOGGER.info("cosmeticShopList {}", cosmeticShopList);
		model.addAttribute("cosmeticShopList",cosmeticShopList);
		model.addAttribute("subModule", "cosmeticShopList");
		
		return "user/cosmeticShopList.html";
    }
    @GetMapping("/getCosmeticShopNewItem")
	public String cosmeticShopNewItem(Model model) {
		model.addAttribute("subModule", "cosmeticShopNewItem");

		return "user/cosmeticShopNewItem.html";
	}
	
	@GetMapping("/cosmeticShop/edit/{id}")
	public String cosmeticShopEditItem(Model model, @PathVariable("id") String id) {
		getCosmeticShopDetail(model, id);
		return "user/cosmeticShopEditItem.html";
	}
	public void getCosmeticShopDetail(Model model, String id) {
		CosmeticShop cosmeticShop = cosmeticShopService.getById(id);
		model.addAttribute("cosmeticShop", cosmeticShop);
	}

	@PostMapping("/cosmeticShop/update/{id}")
	public String updateCosmeticShopItem(Model model, @PathVariable("id") String id, @Valid CosmeticShop cosmeticShop, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of cosmeticShop edit item and the error message
			LOGGER.debug("result cosmeticShop:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.cosmeticShop", result);
			model.addAttribute("cosmeticShop", cosmeticShop);
			return "user/cosmeticShopEditItem.html";
		}
        LOGGER.debug("update cosmeticShop:", cosmeticShop);
        if (cosmeticShop != null) {
			LOGGER.info("updated {}", cosmeticShopService.create(cosmeticShop));
		}
		attr.addFlashAttribute("message", "美容院-" + id + " 更新成功");
		return "redirect:/getCosmeticShopList";
	}

	@GetMapping("/cosmeticShop/delete/{id}")
	public String deleteCosmeticShopItem(@PathVariable("id") String id, CosmeticShop cosmeticShop, Model model, RedirectAttributes attr) {
		System.err.println("delete cosmeticShop item with id=" + id);
		// CosmeticShop cosmeticShop
		LOGGER.info("deleting {}", cosmeticShopService.getById(id));
		LOGGER.info("deleting frontend cosmeticShop{}", cosmeticShop);
		// cosmeticShopService.delete(cosmeticShop);
		cosmeticShopService.deleteById(id);
		attr.addFlashAttribute("message", "美容院-" + id + " 删除成功");
		return "redirect:/getCosmeticShopList";
	}
	@PostMapping("/createCosmeticShop")
    public String createCosmeticShop( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "owner", required = false) String owner,
                                       @RequestParam(name = "contactMethod", required = false) String contactMethod,
                                       @RequestParam(name = "location", required = false) String location,
                                       @RequestParam(name = "size", required = false) String size,
                                       @RequestParam(name = "discount", required = false) String discount,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
		int idNums = cosmeticShopService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.COSMETIC_SHOP,idNums);
        CosmeticShop cosmeticShop =new CosmeticShop();
        cosmeticShop.setId(id);
        cosmeticShop.setName(name);
        cosmeticShop.setOwner(owner);
        cosmeticShop.setContactMethod(contactMethod);
        cosmeticShop.setLocation(location);
        cosmeticShop.setSize(Integer.parseInt(size));
        cosmeticShop.setDiscount(Float.parseFloat(discount));
        cosmeticShop.setDescription(description);
        LOGGER.info("cosmeticShop:",cosmeticShop);

        if (cosmeticShop != null) {
            LOGGER.info("Saved {}", cosmeticShopService.create(cosmeticShop));
        }

        return "redirect:/getCosmeticShopList";
    }
	
    

}
