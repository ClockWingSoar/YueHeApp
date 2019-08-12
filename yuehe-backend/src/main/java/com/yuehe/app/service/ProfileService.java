package com.yuehe.app.service;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.dto.ProfileDetailDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = false)
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


    public ProfileDetailDTO getProfileByClientId(String clientId, String startDate, String endDate) {
		ClientShopDTO clientShopDTO = clientService.getClientDetailById(clientId);
    	ClientDetailDTO clientDetailDTO = operationService.getClientSaleDetailByClientId(clientId, startDate, endDate);
    	ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = saleService.getClientAllSalesPerformanceDetail(clientId, startDate, endDate);
    	ProfileDetailDTO profileDetailDTO = new ProfileDetailDTO();
    	profileDetailDTO.setClientShopDTO(clientShopDTO);
    	profileDetailDTO.setClientDetailDTO(clientDetailDTO);
    	profileDetailDTO.setClientAllSalesPerformanceDetailDTO(clientAllSalesPerformanceDetailDTO);
    	LOGGER.info("profileDetailDTO {}",profileDetailDTO);
    	return profileDetailDTO;
    }
}
