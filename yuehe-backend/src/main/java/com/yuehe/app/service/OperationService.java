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

import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.OperationDetailDTO;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleDetailDTO;
import com.yuehe.app.dto.SaleDetailForDBDTO;
import com.yuehe.app.dto.ShopDetailDTO;
import com.yuehe.app.dto.YueHeAllShopsDetailDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.repository.OperationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yi xiang zhong
 */
@Service
@Transactional(readOnly = false)
public class OperationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationService.class);
	private final OperationRepository operationRepository;
	// private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private final SaleService saleService;
	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;

	public OperationService(OperationRepository operationRepository, SaleService saleService,
			ClientService clientService, CosmeticShopService cosmeticShopService) {
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

	public List<OperationDetailDTO> getAllOperationForOperationList() {
		return operationRepository.findAllOperationList();
	}

	public Operation getById(String id) {
		return operationRepository.findById(id);
	}

	public int getOperationNumberBySaleId(String saleId) {
		return operationRepository.findOperationNumBySaleId(saleId);
	}

	/**
	 * To get all the operation details for yuehe all shops-all clients-all sales
	 * 
	 * @return yueHeAllShopsDetailDTO
	 */
	public YueHeAllShopsDetailDTO getYueHeAllShopsDetail() {
		YueHeAllShopsDetailDTO yueHeAllShopsDetailDTO = new YueHeAllShopsDetailDTO();
		List<ShopDetailDTO> shopDetailDTOList = new ArrayList<ShopDetailDTO>();
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		// it only has one instance of YueHeAllShopsDetailDTO, but to use the common
		// method convertOperationDTO()
		// I have to put it in a list
		long allShopsConsumedAmount = 0;// for all shop's all client's all sales
		long allShopsAdvancedEarnedAmount = 0;// for all shop's all client's all sales
		long allShopsCreateCardTotalAmount = 0;// for all shop's all client's all sales
		long allShopsEarnedAmount = 0;// for all shop's all client's all sales
		long allShopsConsumedEarnedAmount = 0;// for all shop's all client's all sales
		for (CosmeticShop cosmeticShop : cosmeticShopList) {
			ShopDetailDTO shopDetailDTO = getShopClientDetailByShopId(cosmeticShop.getId());
			shopDetailDTOList.add(shopDetailDTO);
			long allClientsConsumedAmount = shopDetailDTO.getAllClientsConsumedAmount();
			long allClientsAdvancedEarnedAmount = shopDetailDTO.getAllClientsAdvancedEarnedAmount();
			long allClientsCreateCardTotalAmount = shopDetailDTO.getAllClientsCreateCardAmount();
			long allClientsEarnedAmount = shopDetailDTO.getAllClientsEarnedAmount();
			long allClientsConsumedEarnedAmount = shopDetailDTO.getAllClientsConsumedEarnedAmount();
			allShopsConsumedAmount += allClientsConsumedAmount;
			allShopsAdvancedEarnedAmount += allClientsAdvancedEarnedAmount;
			allShopsCreateCardTotalAmount += allClientsCreateCardTotalAmount;
			allShopsEarnedAmount += allClientsEarnedAmount;
			allShopsConsumedEarnedAmount += allClientsConsumedEarnedAmount;
		}
		yueHeAllShopsDetailDTO.setYueheCompanyName("悦和国际");
		yueHeAllShopsDetailDTO.setShopDetailDTOs(shopDetailDTOList);
		yueHeAllShopsDetailDTO.setAllShopsAdvancedEarnedAmount(allShopsAdvancedEarnedAmount);
		yueHeAllShopsDetailDTO.setAllShopsConsumedAmount(allShopsConsumedAmount);
		yueHeAllShopsDetailDTO.setAllShopsConsumedEarnedAmount(allShopsConsumedEarnedAmount);
		yueHeAllShopsDetailDTO.setAllShopsCreateCardTotalAmount(allShopsCreateCardTotalAmount);
		yueHeAllShopsDetailDTO.setAllShopsEarnedAmount(allShopsEarnedAmount);
		return yueHeAllShopsDetailDTO;
	}

	public ShopDetailDTO getShopClientDetailByShopId(String shopId) {
		List<Client> clientListByShopId = clientService.getClientsByShopId(shopId);
		List<ClientDetailDTO> clientDetailDTOList = new ArrayList<ClientDetailDTO>();

		String shopName = cosmeticShopService.getById(shopId).getName();
		ShopDetailDTO shopDetailDTO = new ShopDetailDTO();
		long allClientsConsumedAmount = 0;// for one shop all client's all sales
		long allClientsAdvancedEarnedAmount = 0;// for one shop all client's all sales
		long allClientsCreateCardTotalAmount = 0;// for one shop all client's all sales
		long allClientsEarnedAmount = 0;// for one shop all client's all sales
		long allClientsConsumedEarnedAmount = 0;// for one shop all client's all sales
		for (Client client : clientListByShopId) {
			ClientDetailDTO clientDetailDTO = getClientSaleDetailByClientId(client.getId());
			clientDetailDTOList.add(clientDetailDTO);
			long allSalesConsumedAmount = clientDetailDTO.getAllSalesConsumedAmount();
			long allSalesAdvancedEarnedAmount = clientDetailDTO.getAllSalesAdvancedEarnedAmount();
			long allSalesCreateCardTotalAmount = clientDetailDTO.getAllSalesCreateCardAmount();
			long allSalesEarnedAmount = clientDetailDTO.getAllSalesEarnedAmount();
			long allSalesConsumedEarnedAmount = clientDetailDTO.getAllSalesConsumedEarnedAmount();
			allClientsConsumedAmount += allSalesConsumedAmount;
			allClientsAdvancedEarnedAmount += allSalesAdvancedEarnedAmount;
			allClientsCreateCardTotalAmount += allSalesCreateCardTotalAmount;
			allClientsEarnedAmount += allSalesEarnedAmount;
			allClientsConsumedEarnedAmount += allSalesConsumedEarnedAmount;
		}
		shopDetailDTO.setClientDetailDTOs(clientDetailDTOList);
		shopDetailDTO.setCosmeticShopName(shopName);
		shopDetailDTO.setAllClientsAdvancedEarnedAmount(allClientsAdvancedEarnedAmount);
		shopDetailDTO.setAllClientsConsumedAmount(allClientsConsumedAmount);
		shopDetailDTO.setAllClientsConsumedEarnedAmount(allClientsConsumedEarnedAmount);
		shopDetailDTO.setAllClientsCreateCardAmount(allClientsCreateCardTotalAmount);
		shopDetailDTO.setAllClientsEarnedAmount(allClientsEarnedAmount);
		return shopDetailDTO;
	}

	public ClientDetailDTO getClientSaleDetailByClientId(String clientId) {
		List<SaleDetailDTO> saleDetailDTOList = getAllSalesDetailByClientId(clientId);
		String clientName = clientService.getById(clientId).getName();
		ClientDetailDTO clientDetailDTO = new ClientDetailDTO();
		long allSalesConsumedAmount = 0;// for one client's all sales
		long allSalesAdvancedEarnedAmount = 0;// for one client's all sales
		long allSalesCreateCardTotalAmount = 0;// for one client's all sales
		long allSalesEarnedAmount = 0;// for one client's all sales
		long allSalesConsumedEarnedAmount = 0;// for one client's all sales
		for (SaleDetailDTO saleDetailDTO : saleDetailDTOList) {
			long consumedAmount = saleDetailDTO.getConsumedAmount();
			long advancedEarnedAmount = saleDetailDTO.getAdvancedEarnedAmount();
			long createCardtotalAmount = saleDetailDTO.getCreateCardTotalAmount();
			long earnedAmount = saleDetailDTO.getEarnedAmount();
			long consumedEarnedAmount = saleDetailDTO.getConsumedEarnedAmount();
			allSalesConsumedAmount += consumedAmount;
			allSalesAdvancedEarnedAmount += advancedEarnedAmount;
			allSalesCreateCardTotalAmount += createCardtotalAmount;
			allSalesEarnedAmount += earnedAmount;
			allSalesConsumedEarnedAmount += consumedEarnedAmount;
		}
		clientDetailDTO.setSaleDetailDTOs(saleDetailDTOList);
		clientDetailDTO.setClientName(clientName);
		clientDetailDTO.setAllSalesAdvancedEarnedAmount(allSalesAdvancedEarnedAmount);
		clientDetailDTO.setAllSalesConsumedAmount(allSalesConsumedAmount);
		clientDetailDTO.setAllSalesConsumedEarnedAmount(allSalesConsumedEarnedAmount);
		clientDetailDTO.setAllSalesCreateCardAmount(allSalesCreateCardTotalAmount);
		clientDetailDTO.setAllSalesEarnedAmount(allSalesEarnedAmount);
		return clientDetailDTO;
	}

	public SaleDetailDTO getSaleOperationDetailBySaleId(String saleId) {
		List<OperationDetailDTO> operationDetailDTOList = getOperationsBySaleId(saleId);
		SaleDetailDTO saleDetailDTO = getSaleBasicDetailById(saleId);
		saleDetailDTO.setOperationDetailDTOs(operationDetailDTOList);
		return saleDetailDTO;
	}

	public List<SaleDetailDTO> getAllSalesDetailByClientId(String ClientId) {
		List<SaleDetailDTO> saleDetailDTOList = new ArrayList<SaleDetailDTO>();
		List<SaleBeautifySkinItemForFilterDTO> saleListByClient = saleService.getByClientId(ClientId);
		for (SaleBeautifySkinItemForFilterDTO saleBeautifySkinItemForFilterDTO : saleListByClient) {
			String saleId = saleBeautifySkinItemForFilterDTO.getSaleId();
			SaleDetailDTO saleDetailDTO = getSaleBasicDetailById(saleId);
			saleDetailDTOList.add(saleDetailDTO);

		}
		return saleDetailDTOList;

	}

	private SaleDetailDTO getSaleBasicDetailById(String id) {
		SaleDetailForDBDTO saleDetailForDBDTO = saleService.getSaleBasicDetailById(id);
		SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
		String saleId = saleDetailForDBDTO.getSaleId();
		float cosmeticShopDiscount = saleDetailForDBDTO.getCosmeticShopDiscount();
		long createCardTotalAmount = saleDetailForDBDTO.getCreateCardTotalAmount();
		long employeePremium = new Float(saleDetailForDBDTO.getEmployeePremium()).longValue();// 员工奖励
		long shopPremium = new Float(saleDetailForDBDTO.getShopPremium()).longValue();// 美容院回扣
		int itemNumber = saleDetailForDBDTO.getItemNumber();
		// 回给公司的回款计算方法为：开卡金额 * 店家折扣点 - 给员工的奖励 - 给店家的回扣（柳叶需扣除业绩的1%）
		long earnedAmount = new Double(createCardTotalAmount * cosmeticShopDiscount).longValue() - employeePremium
				- shopPremium;
		int operationNumber = getOperationNumberBySaleId(saleId);// 操作次数
		float unitPrice = createCardTotalAmount / itemNumber;// 美肤卡单次价格
		int restItemNumber = itemNumber - operationNumber;// 剩余次数
		long consumedAmount = new Double(operationNumber * unitPrice).longValue();// 已消耗总数
		long consumedEarnedAmount = new Double(operationNumber * unitPrice * cosmeticShopDiscount).longValue();// 已消耗回款
		long advancedEarnedAmount = earnedAmount - consumedEarnedAmount;// 预付款只计算回款的部分，不包括美容院的

		saleDetailDTO.setSaleId(saleId);
		saleDetailDTO.setCreateCardDate(saleDetailForDBDTO.getCreateCardDate());
		saleDetailDTO.setBeautifySkinItemName(saleDetailForDBDTO.getBeautifySkinItemName());
		saleDetailDTO.setCreateCardTotalAmount(createCardTotalAmount);
		saleDetailDTO.setEarnedAmount(earnedAmount);
		saleDetailDTO.setItemNumber(itemNumber);
		saleDetailDTO.setDescription(saleDetailForDBDTO.getDescription());
		saleDetailDTO.setRestItemNumber(restItemNumber);
		saleDetailDTO.setConsumedAmount(consumedAmount);
		saleDetailDTO.setConsumedEarnedAmount(consumedEarnedAmount);
		saleDetailDTO.setAdvancedEarnedAmount(advancedEarnedAmount);

		return saleDetailDTO;
	}

	private List<OperationDetailDTO> getOperationsBySaleId(String saleId) {
		List<OperationDetailDTO> operationDetailDTOList = operationRepository.findBySaleId(saleId);
		return operationDetailDTOList;
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
