package com.yuehe.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.ShopRefundRule;
import com.yuehe.app.service.ShopRefundRuleService;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.service.YueHeCommonService;

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
import org.thymeleaf.util.StringUtils;

@Controller
public class ShopRefundRuleController {
	@ModelAttribute("module")
	String module() {
		return "shopRefundRule";
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(ShopRefundRuleController.class);
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private final ShopRefundRuleService shopRefundRuleService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;

	public ShopRefundRuleController(ShopRefundRuleService shopRefundRuleService,
			YueHeCommonService yueHeCommonService, CosmeticShopService cosmeticShopService) {
		this.shopRefundRuleService = shopRefundRuleService;
		this.yueHeCommonService = yueHeCommonService;
		this.cosmeticShopService = cosmeticShopService;
	}

	@GetMapping("/getShopRefundRuleList")
	public String shopRefundRuleOverview(Model model) {
		List<ShopRefundRule> shopRefundRuleList = new ArrayList<ShopRefundRule>();
		shopRefundRuleList = shopRefundRuleService.getAllShopRefundRules();
		LOGGER.info("shopRefundRuleList {}", shopRefundRuleList);
		model.addAttribute("subModule", "shopRefundRuleList");
		model.addAttribute("shopRefundRuleList", shopRefundRuleList);

		return "user/shopRefundRuleList.html";
	}

	@GetMapping("/getShopRefundRuleNewItem/{cosmeticShopId}")
	public String shopRefundRuleNewItem(Model model, @PathVariable("cosmeticShopId") String cosmeticShopId) {
		model.addAttribute("cosmeticShopId", cosmeticShopId);
		model.addAttribute("subModule", "shopRefundRuleNewItem");
		LOGGER.info("cosmeticShopId {}", cosmeticShopId);

		return "user/shopRefundRuleNewItem.html";
	}

	@GetMapping("/shopRefundRule/edit/{id}")
	public String shopRefundRuleEditItem(Model model, @PathVariable("id") long id) {
		getShopRefundRuleDetail(model, id);
		return "user/shopRefundRuleEditItem.html";
	}

	public void getShopRefundRuleDetail(Model model, long id) {
		ShopRefundRule shopRefundRule = shopRefundRuleService.getById(id);
		model.addAttribute("cosmeticShopId", shopRefundRule.getCosmeticShop().getId());
		model.addAttribute("shopRefundRule", shopRefundRule);
	}

	@PostMapping("/shopRefundRule/update/{id}")
	public String updateShopRefundRuleItem(@RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
			Model model, @PathVariable("id") long id, @Valid ShopRefundRule shopRefundRule,
			BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of shopRefundRule edit item and the error
			// message
			LOGGER.debug("result shopRefundRule:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.shopRefundRule", result);
			model.addAttribute("shopRefundRule", shopRefundRule);
			return "user/shopRefundRuleEditItem.html";
		}
		CosmeticShop cosmeticShop = yueHeCommonService.getCosmeticShopById(cosmeticShopId);
		shopRefundRule.setCosmeticShop(cosmeticShop);
		LOGGER.debug("update shopRefundRule:", shopRefundRule);
		if (shopRefundRule != null) {
			try {
				shopRefundRule.setAdjustDate(simpleDateFormat
						.format(new SimpleDateFormat("MM/dd/yyyy").parse(shopRefundRule.getAdjustDate())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			LOGGER.info("updated {}", shopRefundRuleService.create(shopRefundRule));
		}
		attr.addFlashAttribute("message", "店家回款规则-" + id + " 更新成功");
		return "redirect:/getShopRefundRuleList";
	}


	@GetMapping("/shopRefundRule/delete/{id}")
	public String deleteShopRefundRuleItem(@PathVariable("id") long id, ShopRefundRule shopRefundRule, Model model, RedirectAttributes attr) {
		System.err.println("delete shopRefundRule item with id=" + id);
		// ShopRefundRule shopRefundRule
		LOGGER.info("deleting {}", shopRefundRuleService.getById(id));
		LOGGER.info("deleting frontend shopRefundRule{}", shopRefundRule);
		// shopRefundRuleService.delete(shopRefundRule);
		shopRefundRuleService.deleteById(id);
		attr.addFlashAttribute("message", "店家回款规则-" + id + " 删除成功");
		return "redirect:/getShopRefundRuleList";
	}
	@PostMapping("/createShopRefundRule")
    public String createShopRefundRule( @RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
                                       @RequestParam(name = "adjustAction", required = false) String adjustAction,
                                       @RequestParam(name = "triggerPoint", required = false) String triggerPoint,
                                       @RequestParam(name = "adjustAmount", required = false) double adjustAmount,
                                       @RequestParam(name = "adjustDate", required = false) String adjustDate,
                                       RedirectAttributes attr,
                                       @RequestParam(name = "description", required = false) String description) {
        ShopRefundRule shopRefundRule =new ShopRefundRule();
        shopRefundRule.setCosmeticShop(yueHeCommonService.getCosmeticShopById(cosmeticShopId));
        shopRefundRule.setAdjustAction(adjustAction);
        shopRefundRule.setTriggerPoint(triggerPoint);
        shopRefundRule.setAdjustAmount(adjustAmount);
        try {
			shopRefundRule.setAdjustDate(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(adjustDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        shopRefundRule.setDescription(description);
        LOGGER.info("shopRefundRule:",shopRefundRule);

        LOGGER.info("Saved {}", shopRefundRuleService.create(shopRefundRule));
		attr.addFlashAttribute("message", "美容院-"+cosmeticShopId + "回款规则修改成功");
        return "redirect:/getShopRefundRuleList";
    }
	
    

}
