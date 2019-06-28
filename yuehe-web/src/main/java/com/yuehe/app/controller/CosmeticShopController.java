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

import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.util.YueHeUtil;


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
		// TODO Auto-generated method stub
		List<CosmeticShop> cosmeticShopList =new ArrayList<CosmeticShop>();
		cosmeticShopList = cosmeticShopService.getAllCosmeticShop();
		 LOGGER.info("cosmeticShopList {}", cosmeticShopList);
		model.addAttribute("cosmeticShopList",cosmeticShopList);
		
		return "user/cosmeticShop";
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
		long idNums = cosmeticShopService.getEntityNumber();
        String id = YueHeUtil.getId(2,Math.toIntExact(idNums));
        CosmeticShop cosmeticShop =new CosmeticShop();
        cosmeticShop.setId(id);
        cosmeticShop.setName(name);
        cosmeticShop.setOwner(owner);
        cosmeticShop.setContactMethod(contactMethod);
        cosmeticShop.setLocation(location);
        cosmeticShop.setSize(Integer.parseInt(size));
        cosmeticShop.setDiscount(Float.parseFloat(discount));
        cosmeticShop.setDescription(description);
        LOGGER.debug("cosmeticShop:",cosmeticShop);

        if (cosmeticShop != null) {
            LOGGER.info("Saved {}", cosmeticShopService.create(cosmeticShop));
        }

        return "redirect:/getCosmeticShopList";
    }
	
    

}
