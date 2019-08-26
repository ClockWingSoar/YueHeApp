package com.yuehe.app.controller;

import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.dto.ClientQuestionareDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.ClientQuestionare;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.service.ClientQuestionareService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.ServiceUtil;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClientQuestionareController{
	 @ModelAttribute("module")
	    String module() {
	        return "client";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(ClientQuestionareController.class);
	
	@Autowired
	private final ClientQuestionareService clientQuestionareService;
	
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	@Autowired
	public ClientQuestionareController(ClientQuestionareService clientQuestionareService,YueHeCommonService yueHeCommonService) {
		this.clientQuestionareService = clientQuestionareService;
		this.yueHeCommonService = yueHeCommonService;
	}

	// @GetMapping("/getClientQuestionareList") 
	// public  String clientQuestionareOverview(Model model){
	// 	List<ClientQuestionare> clientQuestionareList =new ArrayList<ClientQuestionare>();
	// 	clientQuestionareList = clientQuestionareService.getAllClientQuestionares();
	// 	 LOGGER.info("clientQuestionareList {}", clientQuestionareList);
	// 	model.addAttribute("clientQuestionareList",clientQuestionareList);
	// 	model.addAttribute("subModule", "clientQuestionareList");
	// 	return "user/clientQuestionareList.html";
	// }
	@GetMapping("/getClientQuestionare")
	public String clientQuestionare(Model model) {
		List<CosmeticShop> cosmeticShopList =  yueHeCommonService.getAllCosmeticShops();
		model.addAttribute("cosmeticShopList", cosmeticShopList);
		model.addAttribute("subModule", "clientQuestionareNewItem");

		return "user/clientQuestionareNewItem.html";
	}
	// @GetMapping("/getClientQuestionareNewItem")
	@RequestMapping(value = "/getClientQuestionareNewItem", method = RequestMethod.GET)
	public @ResponseBody Client getClientBasicInfoForQuestionare(
			@RequestParam(value = "clientId", required = true) String clientId) {
				Client client = yueHeCommonService.getClientForQuestionareById(clientId);
			System.err.println(client);
			LOGGER.info("Client-{}",client);
			return client;
	}
	@RequestMapping(value = "/checkClientHasQuestionare", method = RequestMethod.GET)
	public @ResponseBody ClientQuestionare checkClientHasQuestionare(
			@RequestParam(value = "clientId", required = true) String clientId) {
				ClientQuestionare clientQuestionare = clientQuestionareService.getByClientId(clientId);
			System.err.println(clientQuestionare);
			LOGGER.info("ClientQuestionare-{}",clientQuestionare);
			if(clientQuestionare == null){//to set back the button to edit client questionare if there is no record in client_questionare table
				clientQuestionare = new ClientQuestionare();
			}
			return clientQuestionare;
	}
	
	@GetMapping("/clientQuestionare/edit/{id}")
	public String clientQuestionareEditItem(Model model, @PathVariable("id") String id) {
		getClientQuestionareDetail(model, id);
		return "user/clientQuestionareEditItem.html";
	}
	public void getClientQuestionareDetail(Model model, String id) {
		ClientQuestionare clientQuestionare = clientQuestionareService.getByClientId(id);
		ClientQuestionareDTO clientQuestionareDTO = new ClientQuestionareDTO();
		clientQuestionareDTO.setClientId(clientQuestionare.getClient().getId());
		clientQuestionareDTO.setClientGender(clientQuestionare.getClient().getGender());
		clientQuestionareDTO.setClientName(clientQuestionare.getClient().getName());
		clientQuestionareDTO.setClientAge(clientQuestionare.getClient().getAge());
		clientQuestionareDTO.setUsualBeautifyItem(clientQuestionare.getUsualBeautifyItem());
		clientQuestionareDTO.setIfAlergicBody(clientQuestionare.getIfAlergicBody());
		clientQuestionareDTO.setIfAlergicSkin(clientQuestionare.getIfAlergicSkin());
		clientQuestionareDTO.setAlergicSources(StringUtils.split(clientQuestionare.getAlergicSource(),';'));
		clientQuestionareDTO.setMedicineName(clientQuestionare.getMedicineName());
		clientQuestionareDTO.setIfHealthy(clientQuestionare.getIfHealthy());
		clientQuestionareDTO.setIfHadMedicine(clientQuestionare.getIfHadMedicine());
		clientQuestionareDTO.setIfPregnantOrBreastFeeding(clientQuestionare.getIfPregnantOrBreastFeeding());
		clientQuestionareDTO.setIfUsedWhiteningProduct(clientQuestionare.getIfUsedWhiteningProduct());
		clientQuestionareDTO.setIfSunExposure(clientQuestionare.getIfSunExposure());
		clientQuestionareDTO.setIfSunProtection(clientQuestionare.getIfSunProtection());
		clientQuestionareDTO.setIfSunBurnRecently(clientQuestionare.getIfSunBurnRecently());
		clientQuestionareDTO.setIfScabBody(clientQuestionare.getIfScabBody());
		clientQuestionareDTO.setEatingSituations(StringUtils.split(clientQuestionare.getEatingSituation(),';'));
		clientQuestionareDTO.setSleepSituations(StringUtils.split(clientQuestionare.getSleepSituation(),';'));
		clientQuestionareDTO.setDigestSituations(StringUtils.split(clientQuestionare.getDigestSituation(),';'));
		clientQuestionareDTO.setIncretionSituations(StringUtils.split(clientQuestionare.getIncretionSituation(),';'));
		clientQuestionareDTO.setPractiseSituation(clientQuestionare.getPractiseSituation());
		clientQuestionareDTO.setPractiseMethods(clientQuestionare.getPractiseMethods());
		clientQuestionareDTO.setWorkingEnvs(StringUtils.split(clientQuestionare.getWorkingEnv(),';'));
		clientQuestionareDTO.setCommonUsedSkinCareProducts(clientQuestionare.getCommonUsedSkinCareProducts());
		LOGGER.debug("result clientQuestionareDTO:{}", clientQuestionareDTO); 
		model.addAttribute("clientQuestionare", clientQuestionareDTO);
	}

	@PostMapping("/clientQuestionare/update/{id}")
	public String updateClientQuestionareItem(Model model, @PathVariable("id") String id, @Valid ClientQuestionare clientQuestionare, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of clientQuestionare edit item and the error message
			LOGGER.debug("result clientQuestionare:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.clientQuestionare", result);
			model.addAttribute("clientQuestionare", clientQuestionare);
			return "user/clientQuestionareEditItem.html";
		}
		LOGGER.debug("update clientQuestionare:", clientQuestionare);
		if (clientQuestionare != null) {
			LOGGER.info("updated {}", clientQuestionareService.create(clientQuestionare));
		}
		attr.addFlashAttribute("message",  clientQuestionare.getClient().getName() +"-问卷调查信息更新成功！");
		return "redirect:/getClientQuestionareList";
	}

	@GetMapping("/clientQuestionare/delete/{id}")
	public String deleteClientQuestionareItem(@PathVariable("id") String id, ClientQuestionare clientQuestionare, Model model, RedirectAttributes attr) {
		System.err.println("delete clientQuestionare item with id=" + id);
		// ClientQuestionare clientQuestionare
		LOGGER.info("deleting {}", clientQuestionareService.getById(id));
		LOGGER.info("deleting frontend clientQuestionare{}", clientQuestionare);
		// clientQuestionareService.delete(clientQuestionare);
		clientQuestionareService.deleteById(id);
		attr.addFlashAttribute("message", clientQuestionare.getClient().getName() +"-问卷调查信息删除成功！");
		return "redirect:/getClientQuestionareList";
	}
	@PostMapping("/createClientQuestionare")
    public String createClientQuestionare( @RequestParam(name = "clientId", required = false) String clientId,
                                       @RequestParam(name = "usualBeautifyItem", required = false) String usualBeautifyItem,
                                       @RequestParam(name = "ifAlergicBody", required = false) String ifAlergicBody,
                                       @RequestParam(name = "ifAlergicSkin", required = false) String ifAlergicSkin,
                                       @RequestParam(name = "alergicSource", required = false) String[] alergicSourceArray,
                                       @RequestParam(name = "medicineName", required = false) String medicineName,
									   @RequestParam(name = "ifHealthy", required = false) String ifHealthy, 
									   @RequestParam(name = "ifHadMedicine", required = false) String ifHadMedicine, 
									   @RequestParam(name = "ifPregnantOrBreastFeeding", required = false) String ifPregnantOrBreastFeeding, 
									   @RequestParam(name = "ifUsedWhiteningProduct", required = false) String ifUsedWhiteningProduct, 
									   @RequestParam(name = "ifSunExposure", required = false) String ifSunExposure, 
									   @RequestParam(name = "ifSunProtection", required = false) String ifSunProtection, 
									   @RequestParam(name = "ifSunBurnRecently", required = false) String ifSunBurnRecently, 
									   @RequestParam(name = "ifScabBody", required = false) String ifScabBody, 
									   @RequestParam(name = "eatingSituation", required = false) String[] eatingSituationArray, 
									   @RequestParam(name = "sleepSituation", required = false) String[] sleepSituationArray, 
									   @RequestParam(name = "digestSituation", required = false) String[] digestSituationArray, 
									   @RequestParam(name = "incretionSituation", required = false) String[] incretionSituationArray, 
									   @RequestParam(name = "practiseSituation", required = false) String practiseSituation, 
									   @RequestParam(name = "practiseMethods", required = false) String practiseMethods, 
									   @RequestParam(name = "workingEnv", required = false) String[] workingEnvArray, 
									   @RequestParam(name = "commonUsedSkinCareProducts", required = false) String commonUsedSkinCareProducts, 
									   RedirectAttributes attr
                                       ) 
	{
        ClientQuestionare clientQuestionare =new ClientQuestionare();
        clientQuestionare.setClient(yueHeCommonService.getClientById(clientId));
        clientQuestionare.setUsualBeautifyItem(usualBeautifyItem);
        clientQuestionare.setIfAlergicBody(ifAlergicBody);
        clientQuestionare.setIfAlergicSkin(ifAlergicSkin);
        clientQuestionare.setAlergicSource(ServiceUtil.convertStringArrayToString(alergicSourceArray));
        clientQuestionare.setMedicineName(medicineName);
        clientQuestionare.setIfHealthy(ifHealthy);
        clientQuestionare.setIfHadMedicine(ifHadMedicine);
        clientQuestionare.setIfPregnantOrBreastFeeding(ifPregnantOrBreastFeeding);
        clientQuestionare.setIfUsedWhiteningProduct(ifUsedWhiteningProduct);
        clientQuestionare.setIfSunExposure(ifSunExposure);
        clientQuestionare.setIfSunBurnRecently(ifSunBurnRecently);
        clientQuestionare.setIfScabBody(ifScabBody);
        clientQuestionare.setEatingSituation(ServiceUtil.convertStringArrayToString(eatingSituationArray));
        clientQuestionare.setSleepSituation(ServiceUtil.convertStringArrayToString(sleepSituationArray));
        clientQuestionare.setDigestSituation(ServiceUtil.convertStringArrayToString(digestSituationArray));
        clientQuestionare.setIncretionSituation(ServiceUtil.convertStringArrayToString(incretionSituationArray));
        clientQuestionare.setPractiseSituation(practiseSituation);
        clientQuestionare.setPractiseMethods(practiseMethods);
        clientQuestionare.setWorkingEnv(ServiceUtil.convertStringArrayToString(workingEnvArray));
        clientQuestionare.setCommonUsedSkinCareProducts(commonUsedSkinCareProducts);
        LOGGER.info("clientQuestionare:",clientQuestionare);

        if (clientQuestionare != null) {
            LOGGER.info("Saved {}", clientQuestionareService.create(clientQuestionare));
        }
		attr.addFlashAttribute("message", clientQuestionare.getClient().getName() +"-问卷调查信息创建成功！");
        return "redirect:/getClientQuestionare";
    }
	
    

}
