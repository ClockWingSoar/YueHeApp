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

import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.service.CosmeticShopService;
import com.yuehe.app.service.YueHeCommonService;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class ClientController{
	 @ModelAttribute("module")
	    String module() {
	        return "client";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
	
	 private CosmeticShop cosmeticShop;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final YueHeCommonService yueHeCommonService;
	public ClientController(ClientService clientService,YueHeCommonService yueHeCommonService) {
		this.clientService = clientService;
		this.yueHeCommonService = yueHeCommonService;
	}

	@GetMapping("/getClientList")
	public  String clientOverview(Model model){
		// TODO Auto-generated method stub
		List<ClientShopDTO> clientList =new ArrayList<ClientShopDTO>();
		clientList = clientService.getClientsDetailList();
		 LOGGER.info("clientList {}", clientList);
		 yueHeCommonService.getAllCosmeticShops(model);
		model.addAttribute("clientList",clientList);
		
		return "user/client.html";
	}
	@PostMapping("/createClient")
    public String createClient( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "cosmeticShopId", required = false) String cosmeticShopId,
                                       @RequestParam(name = "age", required = false) int age,
                                       @RequestParam(name = "gender", required = false) String gender,
                                       @RequestParam(name = "symptom", required = false) String symptom
                                       ) 
	{
        long idNums = clientService.getEntityNumber();
        String id = YueHeUtil.getId(1,Math.toIntExact(idNums));
        Client client =new Client();
        client.setId(id);
        client.setName(name);
        client.setShopId(cosmeticShopId);
        client.setAge(age);
        client.setGender(gender);
        client.setSymptom(symptom);
        LOGGER.debug("client:",client);

        if (client != null) {
            LOGGER.info("Saved {}", clientService.create(client));
        }

        return "redirect:/getClientList";
    }
	
    

}
