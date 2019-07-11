package com.yuehe.app.controller;
//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuehe.app.dto.ProfileDetailDto;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.ProfileService;
import com.yuehe.app.service.YueHeCommonService;


@Controller
public class ProfileController{
	 @ModelAttribute("module")
	    String module() {
	        return "profile";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
	
	 private CosmeticShop cosmeticShop;
	@Autowired
	private final ProfileService profileService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	public ProfileController(ProfileService profileService,YueHeCommonService yueHeCommonService,ClientService clientService) {
		this.profileService = profileService;
		this.clientService = clientService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getProfileOverview")
	public  String getProfileOverview(Model model){
		// TODO Auto-generated method stub
		 yueHeCommonService.getAllCosmeticShops(model);
		
		return "user/profile";
	}
	@RequestMapping(value = "/getProfileDetail", method = RequestMethod.GET)
	public @ResponseBody
	ProfileDetailDto  getProfileByClientId(
			@RequestParam(value = "clientId", required = true) String clientId) {
		ProfileDetailDto profileDetailDto = profileService.getProfileByClientId(clientId);
		System.out.println(profileDetailDto);
		return profileDetailDto;
	}

	
	
//	@PostMapping("/createClient")
//    public String createClient( @RequestParam(name = "name", required = false) String name,
//                                       @RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
//                                       @RequestParam(name = "age", required = false) int age,
//                                       @RequestParam(name = "gender", required = false) String gender,
//                                       @RequestParam(name = "symptom", required = false) String symptom
//                                       ) 
//	{
//        long idNums = profileService.getEntityNumber();
//        String id = YueHeUtil.getId(1,Math.toIntExact(idNums));
//        Client client =new Client();
//        client.setId(id);
//        client.setName(name);
//        client.setShopId(cosmeticShopId);
//        client.setAge(age);
//        client.setGender(gender);
//        client.setSymptom(symptom);
//        LOGGER.debug("client:",client);
//
//        if (client != null) {
//            LOGGER.info("Saved {}", profileService.create(client));
//        }
//
//        return "redirect:/getClientList";
//    }
	
    

}
