package com.yuehe.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.SaleCardAmountAdjust;
import com.yuehe.app.service.SaleCardAmountAdjustService;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.service.YueHeCommonService;

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
public class SaleCardAmountAdjustController{
	 @ModelAttribute("module")
	    String module() {
	        return "saleCardAmountAdjust";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(SaleCardAmountAdjustController.class);
	 private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private final SaleCardAmountAdjustService saleCardAmountAdjustService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	@Autowired
	private final SaleService saleService;
	public SaleCardAmountAdjustController(SaleCardAmountAdjustService saleCardAmountAdjustService,YueHeCommonService yueHeCommonService,SaleService saleService) {
		this.saleCardAmountAdjustService = saleCardAmountAdjustService;
		this.yueHeCommonService = yueHeCommonService;
		this.saleService = saleService;
	}

	@GetMapping("/getSaleCardAmountAdjustList")
	public  String saleCardAmountAdjustOverview(Model model){
		List<SaleCardAmountAdjust> saleCardAmountAdjustList =new ArrayList<SaleCardAmountAdjust>();
		saleCardAmountAdjustList = saleCardAmountAdjustService.getAllSaleCardAmountAdjusts();
		 LOGGER.info("saleCardAmountAdjustList {}", saleCardAmountAdjustList);
         model.addAttribute("subModule", "saleCardAmountAdjustList");
		model.addAttribute("saleCardAmountAdjustList",saleCardAmountAdjustList);
		
		return "user/saleCardAmountAdjustList.html";
    }
    @GetMapping("/getSaleCardAmountAdjustNewItem/{saleId}")
	public String saleCardAmountAdjustNewItem(Model model,  @PathVariable("saleId") String saleId) {
		model.addAttribute("saleId", saleId);
		model.addAttribute("subModule", "saleCardAmountAdjustNewItem");
		LOGGER.info("saleId {}", saleId);
         
		return "user/saleCardAmountAdjustNewItem.html";
	}
	
	@GetMapping("/saleCardAmountAdjust/edit/{id}")
	public String saleCardAmountAdjustEditItem(Model model, @PathVariable("id") long id) {
		getSaleCardAmountAdjustDetail(model, id);
		return "user/saleCardAmountAdjustEditItem.html";
	}
	public void getSaleCardAmountAdjustDetail(Model model, long id) {
		SaleCardAmountAdjust saleCardAmountAdjust = saleCardAmountAdjustService.getById(id);
		model.addAttribute("saleId", saleCardAmountAdjust.getSale().getId());
		model.addAttribute("saleCardAmountAdjust", saleCardAmountAdjust);
	}

	@PostMapping("/saleCardAmountAdjust/update/{id}")
	public String updateSaleCardAmountAdjustItem( @RequestParam(name = "saleId", required = false) String saleId,
	Model model, @PathVariable("id") long id, @Valid SaleCardAmountAdjust saleCardAmountAdjust, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of saleCardAmountAdjust edit item and the error message
			LOGGER.debug("result saleCardAmountAdjust:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.saleCardAmountAdjust", result);
			model.addAttribute("saleCardAmountAdjust", saleCardAmountAdjust);
			return "user/saleCardAmountAdjustEditItem.html";
		}
		LOGGER.debug("update saleCardAmountAdjust:", saleCardAmountAdjust);
		if (saleCardAmountAdjust != null) {

			updateSale(saleCardAmountAdjust,saleId);
			LOGGER.info("updated {}", saleCardAmountAdjustService.create(saleCardAmountAdjust));
		}
		attr.addFlashAttribute("message", "销售卡修改项-" + id + " 更新成功");
		return "redirect:/getSaleCardAmountAdjustList";
	}

	private void updateSale(SaleCardAmountAdjust newSaleCardAmountAdjust, String saleId){
		Sale sale = yueHeCommonService.getSaleById(saleId);
		SaleCardAmountAdjust oldSaleCardAmountAdjust = saleCardAmountAdjustService.getById(newSaleCardAmountAdjust.getId());
			String adjustAction = newSaleCardAmountAdjust.getAdjustAction();
			newSaleCardAmountAdjust.setSale(sale);
			if(adjustAction.equals("add")){

				sale.setReceivedAmount(sale.getReceivedAmount()-oldSaleCardAmountAdjust.getAdjustAmount()+newSaleCardAmountAdjust.getAdjustAmount());
			}else{
				sale.setReceivedAmount(sale.getReceivedAmount()+oldSaleCardAmountAdjust.getAdjustAmount()-newSaleCardAmountAdjust.getAdjustAmount());
				// sale.setCreateCardTotalAmount(sale.getCreateCardTotalAmount()-saleCardAmountAdjust.getAdjustAmount());
				sale.setCreateCardTotalAmount(0l);
				sale.setReceivedEarnedAmount(0l);

			}
			LOGGER.info("updated {}", saleService.create(sale));
	}
	@GetMapping("/saleCardAmountAdjust/delete/{id}")
	public String deleteSaleCardAmountAdjustItem(@PathVariable("id") long id, SaleCardAmountAdjust saleCardAmountAdjust, Model model, RedirectAttributes attr) {
		System.err.println("delete saleCardAmountAdjust item with id=" + id);
		// SaleCardAmountAdjust saleCardAmountAdjust
		LOGGER.info("deleting {}", saleCardAmountAdjustService.getById(id));
		LOGGER.info("deleting frontend saleCardAmountAdjust{}", saleCardAmountAdjust);
		// saleCardAmountAdjustService.delete(saleCardAmountAdjust);
		saleCardAmountAdjustService.deleteById(id);
		attr.addFlashAttribute("message", "销售卡修改项-" + id + " 删除成功");
		return "redirect:/getSaleCardAmountAdjustList";
	}
	@PostMapping("/createSaleCardAmountAdjust")
    public String createSaleCardAmountAdjust( @RequestParam(name = "saleId", required = false) String saleId,
                                       @RequestParam(name = "adjustAction", required = false) String adjustAction,
                                       @RequestParam(name = "adjustAmount", required = false) long adjustAmount,
                                       @RequestParam(name = "adjustDate", required = false) String adjustDate,
                                       RedirectAttributes attr,
                                       @RequestParam(name = "description", required = false) String description) {
        SaleCardAmountAdjust saleCardAmountAdjust =new SaleCardAmountAdjust();
        saleCardAmountAdjust.setSale(yueHeCommonService.getSaleById(saleId));
        saleCardAmountAdjust.setAdjustAction(adjustAction);
        saleCardAmountAdjust.setAdjustAmount(adjustAmount);
        saleCardAmountAdjust.setAdjustDate(adjustDate);
        saleCardAmountAdjust.setDescription(description);
        LOGGER.info("saleCardAmountAdjust:",saleCardAmountAdjust);

        LOGGER.info("Saved {}", saleCardAmountAdjustService.create(saleCardAmountAdjust));
		updateSale(saleCardAmountAdjust,saleId);
		attr.addFlashAttribute("message", "销售卡-"+saleId + "金额修改成功");
        return "redirect:/getSaleCardAmountAdjustList";
    }
	
    

}
