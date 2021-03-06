/*
    Copyright (C) 2018  Shazin Sadakath

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.yuehe.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.ClientDetailDto;
import com.yuehe.app.dto.OperationDetailDto;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto;
import com.yuehe.app.dto.SaleDetailDto;
import com.yuehe.app.dto.SaleDetailForDBDto;
import com.yuehe.app.dto.ShopDetailDto;
import com.yuehe.app.dto.YueHeAllShopsDetailDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.repository.OperationRepository;

/**
 * @author yi xiang zhong
 */
@Service
@Transactional(readOnly = true)
public class OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationService.class);
    private final OperationRepository operationRepository;
    private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
    public OperationService(OperationRepository operationRepository,SaleService saleService,
    		ClientService clientService,CosmeticShopService cosmeticShopService) {
        this.operationRepository = operationRepository;
        this.saleService = saleService;
        this.clientService = clientService;
        this.cosmeticShopService = cosmeticShopService;
    }


    @Transactional(rollbackFor = Exception.class)
    public Operation create(Operation operation) {
        return operationRepository.save(operation);
    }
    public List<Operation> getAllOriginalOperation() {
        return operationRepository.findAll();
    }
    public List<OperationDetailDto> getAllOperationForOperationList() {
    	return operationRepository.findAllOperationList();
    }
    public Operation getOperationById(String id) {
    	return operationRepository.findById(id);
    }
    public int getOperationNumberBySaleId(String saleId) {
    	return operationRepository.findOperationNumBySaleId(saleId);
    }
	/**
	 * To get all the operation details for yuehe all shops-all clients-all sales
	 * @return yueHeAllShopsDetailDto
	 */
	public YueHeAllShopsDetailDto getYueHeAllShopsDetail() {
		YueHeAllShopsDetailDto yueHeAllShopsDetailDto = new YueHeAllShopsDetailDto();
		List<ShopDetailDto> shopDetailDtoList = new ArrayList<ShopDetailDto>();
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		//it only has one instance of YueHeAllShopsDetailDto, but to use the common method convertOperationDto()
		//I have to put it in a list
		long allShopsConsumedAmount = 0;//for all shop's all client's all sales
		long allShopsAdvancedEarnedAmount = 0;//for all shop's all client's all sales
		long allShopsCreateCardTotalAmount = 0;//for all shop's all client's all sales
		long allShopsEarnedAmount = 0;//for all shop's all client's all sales
		long allShopsConsumedEarnedAmount = 0;//for all shop's all client's all sales
		for(CosmeticShop cosmeticShop : cosmeticShopList) {
			ShopDetailDto shopDetailDto = getShopClientDetailByShopId(cosmeticShop.getId());
			shopDetailDtoList.add(shopDetailDto);
			long allClientsConsumedAmount = shopDetailDto.getAllClientsConsumedAmount();
    		long allClientsAdvancedEarnedAmount = shopDetailDto.getAllClientsAdvancedEarnedAmount();
    		long allClientsCreateCardTotalAmount = shopDetailDto.getAllClientsCreateCardAmount();
    		long allClientsEarnedAmount = shopDetailDto.getAllClientsEarnedAmount();
    		long allClientsConsumedEarnedAmount = shopDetailDto.getAllClientsConsumedEarnedAmount();
    		allShopsConsumedAmount += allClientsConsumedAmount;
    		allShopsAdvancedEarnedAmount += allClientsAdvancedEarnedAmount;
    		allShopsCreateCardTotalAmount += allClientsCreateCardTotalAmount;
    		allShopsEarnedAmount += allClientsEarnedAmount;
    		allShopsConsumedEarnedAmount += allClientsConsumedEarnedAmount;
		}
		yueHeAllShopsDetailDto.setYueheCompanyName("悦和国际");
		yueHeAllShopsDetailDto.setShopDetailDtos(shopDetailDtoList);
		yueHeAllShopsDetailDto.setAllShopsAdvancedEarnedAmount(allShopsAdvancedEarnedAmount);
		yueHeAllShopsDetailDto.setAllShopsConsumedAmount(allShopsConsumedAmount);
		yueHeAllShopsDetailDto.setAllShopsConsumedEarnedAmount(allShopsConsumedEarnedAmount);
		yueHeAllShopsDetailDto.setAllShopsCreateCardTotalAmount(allShopsCreateCardTotalAmount);
		yueHeAllShopsDetailDto.setAllShopsEarnedAmount(allShopsEarnedAmount);
		return yueHeAllShopsDetailDto;
	}
    
    public ShopDetailDto getShopClientDetailByShopId(String shopId) {
    	List<Client> clientListByShopId = clientService.getClientsByShopId(shopId);
    	List<ClientDetailDto> clientDetailDtoList = new ArrayList<ClientDetailDto>();
    
    	String shopName = cosmeticShopService.getCosmeticShopById(shopId).getName();
    	ShopDetailDto shopDetailDto = new ShopDetailDto();
    	long allClientsConsumedAmount = 0;//for one shop all client's all sales
		long allClientsAdvancedEarnedAmount = 0;//for one shop all client's all sales
		long allClientsCreateCardTotalAmount = 0;//for one shop all client's all sales
		long allClientsEarnedAmount = 0;//for one shop all client's all sales
		long allClientsConsumedEarnedAmount = 0;//for one shop all client's all sales
		for(Client client : clientListByShopId) {
    		ClientDetailDto clientDetailDto = getClientSaleDetailByClientId(client.getId());
    		clientDetailDtoList.add(clientDetailDto);
    		long allSalesConsumedAmount = clientDetailDto.getAllSalesConsumedAmount();
    		long allSalesAdvancedEarnedAmount = clientDetailDto.getAllSalesAdvancedEarnedAmount();
    		long allSalesCreateCardTotalAmount = clientDetailDto.getAllSalesCreateCardAmount();
    		long allSalesEarnedAmount = clientDetailDto.getAllSalesEarnedAmount();
    		long allSalesConsumedEarnedAmount = clientDetailDto.getAllSalesConsumedEarnedAmount();
    		allClientsConsumedAmount += allSalesConsumedAmount;
    		allClientsAdvancedEarnedAmount += allSalesAdvancedEarnedAmount;
    		allClientsCreateCardTotalAmount += allSalesCreateCardTotalAmount;
    		allClientsEarnedAmount += allSalesEarnedAmount;
    		allClientsConsumedEarnedAmount += allSalesConsumedEarnedAmount;
    	}
    	shopDetailDto.setClientDetailDtos(clientDetailDtoList);
    	shopDetailDto.setCosmeticShopName(shopName);
    	shopDetailDto.setAllClientsAdvancedEarnedAmount(allClientsAdvancedEarnedAmount);
    	shopDetailDto.setAllClientsConsumedAmount(allClientsConsumedAmount);
    	shopDetailDto.setAllClientsConsumedEarnedAmount(allClientsConsumedEarnedAmount);
    	shopDetailDto.setAllClientsCreateCardAmount(allClientsCreateCardTotalAmount);
    	shopDetailDto.setAllClientsEarnedAmount(allClientsEarnedAmount);
    	return shopDetailDto;
    }
    
    public ClientDetailDto getClientSaleDetailByClientId(String clientId) {
    	List<SaleDetailDto> saleDetailDtoList = getAllSalesDetailByClientId(clientId);
    	String clientName = clientService.getClientById(clientId).getName();
    	ClientDetailDto clientDetailDto = new ClientDetailDto();
    	long allSalesConsumedAmount = 0;//for one client's all sales
		long allSalesAdvancedEarnedAmount = 0;//for one client's all sales
		long allSalesCreateCardTotalAmount = 0;//for one client's all sales
		long allSalesEarnedAmount = 0;//for one client's all sales
		long allSalesConsumedEarnedAmount = 0;//for one client's all sales
    	for(SaleDetailDto saleDetailDto : saleDetailDtoList) {
    		long consumedAmount = saleDetailDto.getConsumedAmount();
    		long advancedEarnedAmount = saleDetailDto.getAdvancedEarnedAmount();
    		long createCardtotalAmount = saleDetailDto.getCreateCardTotalAmount();
    		long earnedAmount = saleDetailDto.getEarnedAmount();
    		long consumedEarnedAmount = saleDetailDto.getConsumedEarnedAmount();
    		allSalesConsumedAmount += consumedAmount;
    		allSalesAdvancedEarnedAmount += advancedEarnedAmount;
    		allSalesCreateCardTotalAmount += createCardtotalAmount;
    		allSalesEarnedAmount += earnedAmount;
    		allSalesConsumedEarnedAmount += consumedEarnedAmount;
    	}
    	clientDetailDto.setSaleDetailDtos(saleDetailDtoList);
    	clientDetailDto.setClientName(clientName);
    	clientDetailDto.setAllSalesAdvancedEarnedAmount(allSalesAdvancedEarnedAmount);
    	clientDetailDto.setAllSalesConsumedAmount(allSalesConsumedAmount);
    	clientDetailDto.setAllSalesConsumedEarnedAmount(allSalesConsumedEarnedAmount);
    	clientDetailDto.setAllSalesCreateCardAmount(allSalesCreateCardTotalAmount);
    	clientDetailDto.setAllSalesEarnedAmount(allSalesEarnedAmount);
    	return clientDetailDto;
    }
    
    public SaleDetailDto getSaleOperationDetailBySaleId(String saleId) {
    	List<OperationDetailDto> operationDetailDtoList = getOperationsBySaleId(saleId);
    	SaleDetailDto saleDetailDto = getSaleBasicDetailById(saleId);
    	saleDetailDto.setOperationDetailDtos(operationDetailDtoList);
    	return saleDetailDto;
    }
    public List<SaleDetailDto> getAllSalesDetailByClientId(String ClientId){
    	List<SaleDetailDto> saleDetailDtoList = new ArrayList<SaleDetailDto>();
    	 List<SaleBeautifySkinItemForFilterDto> saleListByClient =  saleService.getByClientId(ClientId);
    	 for(SaleBeautifySkinItemForFilterDto saleBeautifySkinItemForFilterDto : saleListByClient) {
    		 String saleId = saleBeautifySkinItemForFilterDto.getSaleId();
    		 SaleDetailDto saleDetailDto = getSaleBasicDetailById(saleId);
    		 saleDetailDtoList.add(saleDetailDto);
    		 
    	 }
    	 return saleDetailDtoList;
    	
    }
    private SaleDetailDto getSaleBasicDetailById(String id) {
    	SaleDetailForDBDto saleDetailForDBDto = saleService.getSaleBasicDetailById(id);
    	SaleDetailDto saleDetailDto = new SaleDetailDto();
		String saleId = saleDetailForDBDto.getSaleId();
		float cosmeticShopDiscount = saleDetailForDBDto.getCosmeticShopDiscount();
		long createCardTotalAmount =  saleDetailForDBDto.getCreateCardTotalAmount();
		long employeePremium = new Float(saleDetailForDBDto.getEmployeePremium()).longValue();//员工奖励
		long shopPremium = new Float(saleDetailForDBDto.getShopPremium()).longValue();//美容院回扣
		int itemNumber = saleDetailForDBDto.getItemNumber();
		//回给公司的回款计算方法为：开卡金额 * 店家折扣点 - 给员工的奖励 - 给店家的回扣（柳叶需扣除业绩的1%）
		long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue()-employeePremium-shopPremium;
		int operationNumber = getOperationNumberBySaleId(saleId);//操作次数
		float unitPrice = createCardTotalAmount/itemNumber;//美肤卡单次价格
		int restItemNumber = itemNumber-operationNumber;//剩余次数
		long consumedAmount = new Double(operationNumber*unitPrice).longValue();//已消耗总数
		long consumedEarnedAmount = new Double(operationNumber*unitPrice*cosmeticShopDiscount).longValue();//已消耗回款
		long advancedEarnedAmount = earnedAmount-consumedEarnedAmount;//预付款只计算回款的部分，不包括美容院的
		
		saleDetailDto.setSaleId(saleId);
		saleDetailDto.setCreateCardDate(saleDetailForDBDto.getCreateCardDate());
		saleDetailDto.setBeautifySkinItemName(saleDetailForDBDto.getBeautifySkinItemName());
		saleDetailDto.setCreateCardTotalAmount(createCardTotalAmount);
		saleDetailDto.setEarnedAmount(earnedAmount);
		saleDetailDto.setItemNumber(itemNumber);
		saleDetailDto.setDescription(saleDetailForDBDto.getDescription());
		saleDetailDto.setRestItemNumber(restItemNumber);
		saleDetailDto.setConsumedAmount(consumedAmount);
		saleDetailDto.setConsumedEarnedAmount(consumedEarnedAmount);
		saleDetailDto.setAdvancedEarnedAmount(advancedEarnedAmount);
			
    		
    	return saleDetailDto;
    }
    
    private List<OperationDetailDto> getOperationsBySaleId(String saleId) {
    	List<OperationDetailDto> operationDetailDtoList = operationRepository.findBySaleId(saleId);
    	return operationDetailDtoList;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public List<Operation> saveAll(List<Operation> operation) {
        LOGGER.info("Saving {}", operation);
        return operationRepository.saveAll(operation);
    }
	
	 public long getEntityNumber() {
	    	return operationRepository.count();
	    }
	 
}
