package com.yuehe.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.dto.ProfileDetailDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;

import org.apache.commons.lang3.StringUtils;
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
		//把存在补退款的销售卡重新处理，需要合并相同销售卡ID的SalePerformanceDetailDTO,补退款的开卡金额为0，开卡日期其实为补退款日期，备注需增加至开卡备注
		List<SalePerformanceDetailDTO> salePerformanceDetailDTOList = clientAllSalesPerformanceDetailDTO.getSalePerformanceDetailDTOs();
		List<SalePerformanceDetailDTO> distinctSalePerformanceDetailDTOList = new ArrayList<SalePerformanceDetailDTO>();
		Iterator<SalePerformanceDetailDTO> salePerformanceDetailDTOIterator =  salePerformanceDetailDTOList.iterator();
		SalePerformanceDetailDTO oldSalePerformanceDetailDTO = null;
		if(salePerformanceDetailDTOIterator.hasNext()){
			oldSalePerformanceDetailDTO = salePerformanceDetailDTOIterator.next();
		}
		Map<String, Set<SalePerformanceDetailDTO>> saleWithSaleAdjustPerformanceDetailDTOMap = new HashMap<String,Set<SalePerformanceDetailDTO>>();
		while(salePerformanceDetailDTOIterator.hasNext()){
			SalePerformanceDetailDTO newSalePerformanceDetailDTO = salePerformanceDetailDTOIterator.next();
			String oldSaleId = oldSalePerformanceDetailDTO.getSaleId();
			String newSaleId = newSalePerformanceDetailDTO.getSaleId();
			if(StringUtils.equals(oldSaleId, newSaleId)){
				if(saleWithSaleAdjustPerformanceDetailDTOMap.containsKey(oldSaleId)){
					Set<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSet = saleWithSaleAdjustPerformanceDetailDTOMap.get(oldSaleId);
					saleWithSaleAdjustPerformanceDetailDTOSet.add(newSalePerformanceDetailDTO);
					saleWithSaleAdjustPerformanceDetailDTOMap.put(oldSaleId,saleWithSaleAdjustPerformanceDetailDTOSet);

				}else{

					Set<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSet = new HashSet<SalePerformanceDetailDTO>();
					saleWithSaleAdjustPerformanceDetailDTOSet.add(oldSalePerformanceDetailDTO);
					saleWithSaleAdjustPerformanceDetailDTOSet.add(newSalePerformanceDetailDTO);
					saleWithSaleAdjustPerformanceDetailDTOMap.put(oldSaleId,saleWithSaleAdjustPerformanceDetailDTOSet);
				}
				

			}else{
				if(saleWithSaleAdjustPerformanceDetailDTOMap.containsKey(oldSaleId)){
					Set<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSet = saleWithSaleAdjustPerformanceDetailDTOMap.get(oldSaleId);
					oldSalePerformanceDetailDTO = buildSaleHasSaleAdjustmentPerformanceDetail(saleWithSaleAdjustPerformanceDetailDTOSet);
				}
				distinctSalePerformanceDetailDTOList.add(oldSalePerformanceDetailDTO);
			}
			oldSalePerformanceDetailDTO = newSalePerformanceDetailDTO;
		}
		//列表最后一个记录需要单独处理，因为while循环已不存在newSalePerformanceDetailDTO
		String lastOldSaleId = oldSalePerformanceDetailDTO.getSaleId();
		if(saleWithSaleAdjustPerformanceDetailDTOMap.containsKey(lastOldSaleId)){
			Set<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSet = saleWithSaleAdjustPerformanceDetailDTOMap.get(lastOldSaleId);
			oldSalePerformanceDetailDTO = buildSaleHasSaleAdjustmentPerformanceDetail(saleWithSaleAdjustPerformanceDetailDTOSet);
		}
		distinctSalePerformanceDetailDTOList.add(oldSalePerformanceDetailDTO);

    	ProfileDetailDTO profileDetailDTO = new ProfileDetailDTO();
    	profileDetailDTO.setClientShopDTO(clientShopDTO);
		profileDetailDTO.setClientDetailDTO(clientDetailDTO);
		clientAllSalesPerformanceDetailDTO.setSalePerformanceDetailDTOs(distinctSalePerformanceDetailDTOList);
    	profileDetailDTO.setClientAllSalesPerformanceDetailDTO(clientAllSalesPerformanceDetailDTO);
    	LOGGER.info("profileDetailDTO {}",profileDetailDTO);
    	return profileDetailDTO;
	}
	
	public SalePerformanceDetailDTO buildSaleHasSaleAdjustmentPerformanceDetail(Set<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSet){
														
		SalePerformanceDetailDTO saleHasSaleAdjustmentPerformanceDTO = new SalePerformanceDetailDTO();
		long createCardTotalAmount = 0l;
		long currentEarnedAmount = 0l;
		long debtAmount = 0l;
		long debtEarnedAmount = 0l;
		long earnedAmount = 0l;
		long receivedAmount = 0l;
		float employeePremium = 0f;
		float shopPremium = 0f;
		String createCardDate ="2016-01-01";
		StringBuilder description = new StringBuilder("该销售卡存在补退款:{");
		Iterator<SalePerformanceDetailDTO> saleWithSaleAdjustPerformanceDetailDTOSetIterator = saleWithSaleAdjustPerformanceDetailDTOSet.iterator();
		while(saleWithSaleAdjustPerformanceDetailDTOSetIterator.hasNext()){
			SalePerformanceDetailDTO salePerformanceDetailDTO = saleWithSaleAdjustPerformanceDetailDTOSetIterator.next();
			long createCardAmountOrZero = salePerformanceDetailDTO.getCreateCardTotalAmount();//如果是退补款的记录则开卡金额为0
			String createCardDateOrSaleAdjustDate = salePerformanceDetailDTO.getCreateCardDate();//可能是开卡日期或补退款日期
			if(createCardAmountOrZero != 0l)
			{
				createCardDate = createCardDateOrSaleAdjustDate;
			}
			createCardTotalAmount += createCardAmountOrZero;
			currentEarnedAmount += salePerformanceDetailDTO.getCurrentEarnedAmount();
			debtAmount += salePerformanceDetailDTO.getDebtAmount();
			debtEarnedAmount += salePerformanceDetailDTO.getDebtEarnedAmount();
			earnedAmount += salePerformanceDetailDTO.getEarnedAmount();
			receivedAmount += salePerformanceDetailDTO.getReceivedAmount();
			employeePremium += salePerformanceDetailDTO.getEmployeePremium();
			shopPremium += salePerformanceDetailDTO.getShopPremium();
			description.append("{").append(createCardDateOrSaleAdjustDate).append(":")
			.append(salePerformanceDetailDTO.getDescription()).append("},");
		}
		description.append("}");
		saleHasSaleAdjustmentPerformanceDTO.setCreateCardTotalAmount(createCardTotalAmount);
		saleHasSaleAdjustmentPerformanceDTO.setCurrentEarnedAmount(currentEarnedAmount);
		saleHasSaleAdjustmentPerformanceDTO.setDebtAmount(debtAmount);
		saleHasSaleAdjustmentPerformanceDTO.setDebtEarnedAmount(debtEarnedAmount);
		saleHasSaleAdjustmentPerformanceDTO.setEarnedAmount(earnedAmount);
		saleHasSaleAdjustmentPerformanceDTO.setReceivedAmount(receivedAmount);
		saleHasSaleAdjustmentPerformanceDTO.setEmployeePremium(employeePremium);
		saleHasSaleAdjustmentPerformanceDTO.setShopPremium(shopPremium);
		saleHasSaleAdjustmentPerformanceDTO.setDescription(description.toString());
		saleHasSaleAdjustmentPerformanceDTO.setCreateCardDate(createCardDate);
		return saleHasSaleAdjustmentPerformanceDTO;
	}


}
