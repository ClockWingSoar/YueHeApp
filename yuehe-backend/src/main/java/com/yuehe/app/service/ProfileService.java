package com.yuehe.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.dto.ProfileDetailDTO;
import com.yuehe.app.entity.Client;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = true)
public class ProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);
//    private final ProfileRepository profileRepository;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final OperationService operationService;
//	@Autowired
//	private final EmployeeService employeeService;
//	private  Client client;
//	private  BeautifySkinItem beautifySkinItem;
//	@Autowired
//	private final BeautifySkinItemService beautifySkinItemService;
    public ProfileService(ClientService clientService,SaleService saleService , OperationService operationService) {
        this.clientService = clientService;
        this.saleService = saleService;
        this.operationService = operationService;
    }


    public ProfileDetailDTO getProfileByClientId(String clientId) {
		ClientShopDTO clientShopDTO = clientService.getClientDetailById(clientId);
    	ClientDetailDTO clientDetailDTO = operationService.getClientSaleDetailByClientId(clientId);
    	ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = saleService.getClientAllSalesPerformanceDetail(clientId);
    	ProfileDetailDTO profileDetailDTO = new ProfileDetailDTO();
    	profileDetailDTO.setClientShopDTO(clientShopDTO);
    	profileDetailDTO.setClientDetailDTO(clientDetailDTO);
    	profileDetailDTO.setClientAllSalesPerformanceDetailDTO(clientAllSalesPerformanceDetailDTO);
    	LOGGER.info("profileDetailDTO {}",profileDetailDTO);
    	return profileDetailDTO;
    }
}
