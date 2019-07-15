package com.yuehe.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.ClientDetailDto;
import com.yuehe.app.dto.ClientShopDto;
import com.yuehe.app.dto.ProfileDetailDto;
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


    public ProfileDetailDto getProfileByClientId(String clientId) {
		ClientShopDto clientShopDto = clientService.getClientDetailById(clientId);
    	ClientDetailDto clientDetailDto = operationService.getClientSaleDetailByClientId(clientId);
    	ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto = saleService.getClientAllSalesPerformanceDetail(clientId);
    	ProfileDetailDto profileDetailDto = new ProfileDetailDto();
    	profileDetailDto.setClientShopDto(clientShopDto);
    	profileDetailDto.setClientDetailDto(clientDetailDto);
    	profileDetailDto.setClientAllSalesPerformanceDetailDto(clientAllSalesPerformanceDetailDto);
    	LOGGER.info("profileDetailDto {}",profileDetailDto);
    	return profileDetailDto;
    }
}
