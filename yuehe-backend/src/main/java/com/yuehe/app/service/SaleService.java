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

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto;
import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.dto.SaleClientItemSellerForDBDto;
import com.yuehe.app.dto.SaleDetailForDBDto;
import com.yuehe.app.dto.SalePerformanceDetailDto;
import com.yuehe.app.dto.SalePerformanceDetailForDBDto;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDto;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDto;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.repository.SaleRepository;

/**
 * @author Shazin Sadakath
 */
@Service
@Transactional(readOnly = true)
public class SaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
    private final SaleRepository saleRepository;
    private SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private final ClientService clientService;
//	@Autowired
//	private final EmployeeService employeeService;
	private  Client client;
	private  BeautifySkinItem beautifySkinItem;
	@Autowired
	private final BeautifySkinItemService beautifySkinItemService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;

    public SaleService(SaleRepository saleRepository,ClientService clientService,
    							BeautifySkinItemService beautifySkinItemService,CosmeticShopService cosmeticShopService) {
        this.saleRepository = saleRepository;
        this.beautifySkinItemService = beautifySkinItemService;
        this.clientService = clientService;
        this.cosmeticShopService = cosmeticShopService;
    }


    @Transactional(rollbackFor = Exception.class)
    public Sale create(Sale sale) {
        return saleRepository.save(sale);
    }
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }
    public List<SaleBeautifySkinItemForFilterDto> getSalesByClientId(String clientId) {
    	return saleRepository.findByClientId(clientId);
    }
    
    public Sale getSaleById(String id) {
    	return saleRepository.findById(id);
    }
    public SaleDetailForDBDto getSaleBasicDetailById(String id) {
    	return saleRepository.fetchSaleBasicDetailById(id);
    }
    public List<SaleBeautifySkinItemForFilterDto> getByClientId(String ClientId) {
    	return saleRepository.findByClientId(ClientId);
    }
    public Sale getSaleByClientNameAndShopNameAndItemNameAndCreateCardDate(String clientName, String cosmeticShopName,
    																		String beautifySkinItemName, String createCardDate) {
    	client = clientService.getClientByClientNameAndShopName(clientName,cosmeticShopName);
    	beautifySkinItem = beautifySkinItemService.getBeautifySkinItemByName(beautifySkinItemName);
    	return saleRepository.findByClientIdAndItemIdAndCreateCardDate(client.getId(),beautifySkinItem.getId(),createCardDate);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<Sale> saveAll(List<Sale> sale) {
        LOGGER.info("Saving {}", sale);
        return saleRepository.saveAll(sale);
    }
	public List<SaleClientItemSellerDto> getSalesDetailList() {
		List<SaleClientItemSellerForDBDto> saleClientItemSellerForDBDtoList = saleRepository.fetchSaleClientItemSellerData();
		List<SaleClientItemSellerDto> saleClientItemSellerDtoList = new ArrayList<SaleClientItemSellerDto>();
		for(SaleClientItemSellerForDBDto saleClientItemSellerForDBDto : saleClientItemSellerForDBDtoList) {
			long beautifySkinItemPrice = saleClientItemSellerForDBDto.getBeautifySkinItemPrice();
			float cosmeticShopDiscount = saleClientItemSellerForDBDto.getCosmeticShopDiscount();
			long createCardTotalAmount =  saleClientItemSellerForDBDto.getCreateCardTotalAmount();
			long receivedAmount = saleClientItemSellerForDBDto.getReceivedAmount();
			int itemNumber = saleClientItemSellerForDBDto.getItemNumber();
			long receivedEarnedAmount = saleClientItemSellerForDBDto.getReceivedEarnedAmount();
			Double discount = new Double(createCardTotalAmount)/new Double(beautifySkinItemPrice * itemNumber);
			long employeePremium = new Float(saleClientItemSellerForDBDto.getEmployeePremium()).longValue();
		    long shopPremium = new Float(saleClientItemSellerForDBDto.getShopPremium()).longValue();
		    long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;
			
			SaleClientItemSellerDto saleClientItemSellerDto = new SaleClientItemSellerDto();
			saleClientItemSellerDto.setBeautifySkinItemName(saleClientItemSellerForDBDto.getBeautifySkinItemName());
			saleClientItemSellerDto.setClientName(saleClientItemSellerForDBDto.getClientName());
			saleClientItemSellerDto.setCosmeticShopName(saleClientItemSellerForDBDto.getCosmeticShopName());
			saleClientItemSellerDto.setCreateCardDate(saleClientItemSellerForDBDto.getCreateCardDate());
			saleClientItemSellerDto.setCreateCardTotalAmount(createCardTotalAmount);
			saleClientItemSellerDto.setDescription(saleClientItemSellerForDBDto.getDescription());
			saleClientItemSellerDto.setDiscount((float)(Math.round(discount*100.0)/100.0));
			saleClientItemSellerDto.setEarnedAmount(earnedAmount);
			saleClientItemSellerDto.setEmployeePremium(employeePremium);
			saleClientItemSellerDto.setItemNumber(itemNumber);
			saleClientItemSellerDto.setReceivedAmount(receivedAmount);
			saleClientItemSellerDto.setReceivedEarnedAmount(receivedEarnedAmount);
			saleClientItemSellerDto.setSaleId(saleClientItemSellerForDBDto.getSaleId());
			saleClientItemSellerDto.setSellerName(saleClientItemSellerForDBDto.getSellerName());
			saleClientItemSellerDto.setShopPremium(shopPremium);
			saleClientItemSellerDto.setUnpaidAmount(createCardTotalAmount - receivedAmount);
			saleClientItemSellerDto.setUnpaidEarnedAmount(earnedAmount - receivedEarnedAmount);
			
			saleClientItemSellerDtoList.add(saleClientItemSellerDto);
		}
		saleClientItemSellerForDBDtoList.forEach(l -> System.out.println(l));
		saleClientItemSellerDtoList.forEach(l -> System.out.println(l));
		return saleClientItemSellerDtoList;
	}
	 public long getEntityNumber() {
	    	return saleRepository.count();
	    }
	 public YueHeAllSalesPerformanceDetailDto getYueHeAllSalesPerformanceDetail() {
		 YueHeAllSalesPerformanceDetailDto yueHeAllSalesPerformanceDetailDto = new YueHeAllSalesPerformanceDetailDto();
		 List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		 List<ShopAllSalesPerformanceDetailDto> shopAllSalesPerformanceDetailDtoList = new ArrayList<ShopAllSalesPerformanceDetailDto>();
		 long allShopsSalesCreateCardTotalAmount = 0;
		 long allShopsSalesReceivedAmount = 0;
		 long allShopsSalesDebtAmount = 0;
		 long allShopsSalesEarnedAmount = 0;
		 long allShopsSalesReceivedEarnedAmount = 0;
		 long allShopsSalesDebtEarnedAmount = 0;
		 float allShopsSalesEmployeePremium = 0;
		 float allShopsSalesShopPremium = 0;
		 for(CosmeticShop cosmeticShop : cosmeticShopList) {
			 String shopId = cosmeticShop.getId();
			 ShopAllSalesPerformanceDetailDto shopAllSalesPerformanceDetailDto = getShopAllSalesPerformanceDetail(shopId);
			 allShopsSalesCreateCardTotalAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesCreateCardTotalAmount();
			 allShopsSalesReceivedAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesReceivedAmount();
			 allShopsSalesDebtAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesDebtAmount();
			 allShopsSalesEarnedAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesDebtEarnedAmount();
			 allShopsSalesReceivedEarnedAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesReceivedEarnedAmount();
			 allShopsSalesDebtEarnedAmount += shopAllSalesPerformanceDetailDto.getAllClientsSalesDebtEarnedAmount();
			 allShopsSalesEmployeePremium += shopAllSalesPerformanceDetailDto.getAllClientsSalesEmployeePremium();
			 allShopsSalesShopPremium += shopAllSalesPerformanceDetailDto.getAllClientsSalesShopPremium();
			 shopAllSalesPerformanceDetailDtoList.add(shopAllSalesPerformanceDetailDto);
			 
		 }
		 yueHeAllSalesPerformanceDetailDto.setCompanyName("悦和国际");
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesCreateCardTotalAmount(allShopsSalesCreateCardTotalAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesReceivedAmount(allShopsSalesReceivedAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesDebtAmount(allShopsSalesDebtAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesEarnedAmount(allShopsSalesEarnedAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesReceivedEarnedAmount(allShopsSalesReceivedEarnedAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesDebtEarnedAmount(allShopsSalesDebtEarnedAmount);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesEmployeePremium(allShopsSalesEmployeePremium);
		 yueHeAllSalesPerformanceDetailDto.setAllShopsSalesShopPremium(allShopsSalesShopPremium);
		 yueHeAllSalesPerformanceDetailDto.setShopAllSalesPerformanceDetailDtos(shopAllSalesPerformanceDetailDtoList);
		 return yueHeAllSalesPerformanceDetailDto;
	 }
	 public ShopAllSalesPerformanceDetailDto getShopAllSalesPerformanceDetail(String shopId) {
		 String shopName = cosmeticShopService.getCosmeticShopById(shopId).getName();
		 ShopAllSalesPerformanceDetailDto shopAllSalesPerformanceDetailDto = new ShopAllSalesPerformanceDetailDto();
		 List<Client> clientListByShop = clientService.getClientsByShopId(shopId);
		 List<ClientAllSalesPerformanceDetailDto> clientAllSalesPerformanceDetailDtoList = new ArrayList<ClientAllSalesPerformanceDetailDto>();
		 long allClientsSalesCreateCardTotalAmount = 0;
		 long allClientsSalesReceivedAmount = 0;
		 long allClientsSalesDebtAmount = 0;
		 long allClientsSalesEarnedAmount = 0;
		 long allClientsSalesReceivedEarnedAmount = 0;
		 long allClientsSalesDebtEarnedAmount = 0;
		 float allClientsSalesEmployeePremium = 0;
		 float allClientsSalesShopPremium = 0;
		 for(Client client : clientListByShop) {
			 String clientId = client.getId();
			 ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto = getClientAllSalesPerformanceDetail(clientId);
			 allClientsSalesCreateCardTotalAmount += clientAllSalesPerformanceDetailDto.getAllSalesCreateCardTotalAmount();
			 allClientsSalesReceivedAmount += clientAllSalesPerformanceDetailDto.getAllSalesReceivedAmount();
			 allClientsSalesDebtAmount += clientAllSalesPerformanceDetailDto.getAllSalesDebtAmount();
			 allClientsSalesEarnedAmount += clientAllSalesPerformanceDetailDto.getAllSalesEarnedAmount();
			 allClientsSalesReceivedEarnedAmount += clientAllSalesPerformanceDetailDto.getAllSalesReceivedEarnedAmount();
			 allClientsSalesDebtEarnedAmount += clientAllSalesPerformanceDetailDto.getAllSalesDebtEarnedAmount();
			 allClientsSalesEmployeePremium += clientAllSalesPerformanceDetailDto.getAllSalesEmployeePremium();
			 allClientsSalesShopPremium += clientAllSalesPerformanceDetailDto.getAllSalesShopPremium();
			 clientAllSalesPerformanceDetailDtoList.add(clientAllSalesPerformanceDetailDto);
			 
		 }
		 shopAllSalesPerformanceDetailDto.setCosmeticShopName(shopName);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesCreateCardTotalAmount(allClientsSalesCreateCardTotalAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesReceivedAmount(allClientsSalesReceivedAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesDebtAmount(allClientsSalesDebtAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesEarnedAmount(allClientsSalesEarnedAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesReceivedEarnedAmount(allClientsSalesReceivedEarnedAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesDebtEarnedAmount(allClientsSalesDebtEarnedAmount);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesEmployeePremium(allClientsSalesEmployeePremium);
		 shopAllSalesPerformanceDetailDto.setAllClientsSalesShopPremium(allClientsSalesShopPremium);
		 shopAllSalesPerformanceDetailDto.setClientAllSalesPerformanceDetailDtos(clientAllSalesPerformanceDetailDtoList);
		 return shopAllSalesPerformanceDetailDto;
	 }
	 public ClientAllSalesPerformanceDetailDto getClientAllSalesPerformanceDetail(String clientId) {
		 String clientName = clientService.getClientById(clientId).getName();
		 ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto = new ClientAllSalesPerformanceDetailDto();
		 List<SaleBeautifySkinItemForFilterDto> saleListByClient = saleRepository.findByClientId(clientId);
		 List<SalePerformanceDetailDto> salePerformanceDetailDtoList = new ArrayList<SalePerformanceDetailDto>();
		 long allSalesCreateCardTotalAmount = 0;
		 long allSalesReceivedAmount = 0;
		 long allSalesDebtAmount = 0;
		 long allSalesEarnedAmount = 0;
		 long allSalesReceivedEarnedAmount = 0;
		 long allSalesDebtEarnedAmount = 0;
		 float allSalesEmployeePremium = 0;
		 float allSalesShopPremium = 0;
		 for(SaleBeautifySkinItemForFilterDto saleBeautifySkinItemForFilterDto : saleListByClient) {
    		 String saleId = saleBeautifySkinItemForFilterDto.getSaleId();
    		 SalePerformanceDetailDto salePerformanceDetailDto = getSalePerformanceDetail(saleId);
    		 allSalesCreateCardTotalAmount += salePerformanceDetailDto.getCreateCardTotalAmount();
    		 allSalesReceivedAmount += salePerformanceDetailDto.getReceivedAmount();
    		 allSalesDebtAmount += salePerformanceDetailDto.getDebtAmount();
    		 allSalesEarnedAmount += salePerformanceDetailDto.getEarnedAmount();
    		 allSalesReceivedEarnedAmount += salePerformanceDetailDto.getReceivedEarnedAmount();
    		 allSalesDebtEarnedAmount += salePerformanceDetailDto.getDebtEarnedAmount();
    		 allSalesEmployeePremium += salePerformanceDetailDto.getEmployeePremium();
    		 allSalesShopPremium += salePerformanceDetailDto.getShopPremium();
    		 salePerformanceDetailDtoList.add(salePerformanceDetailDto);
    		 
    	 }
		 clientAllSalesPerformanceDetailDto.setClientName(clientName);
		 clientAllSalesPerformanceDetailDto.setAllSalesCreateCardTotalAmount(allSalesCreateCardTotalAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesReceivedAmount(allSalesReceivedAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesDebtAmount(allSalesDebtAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesEarnedAmount(allSalesEarnedAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesReceivedEarnedAmount(allSalesReceivedEarnedAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesDebtEarnedAmount(allSalesDebtEarnedAmount);
		 clientAllSalesPerformanceDetailDto.setAllSalesEmployeePremium(allSalesEmployeePremium);
		 clientAllSalesPerformanceDetailDto.setAllSalesShopPremium(allSalesShopPremium);
		 clientAllSalesPerformanceDetailDto.setSalePerformanceDetailDtos(salePerformanceDetailDtoList);
		 return clientAllSalesPerformanceDetailDto;
	 }
	 public SalePerformanceDetailDto getSalePerformanceDetail(String id){
		 SalePerformanceDetailForDBDto salePerformanceDetailForDBDto = saleRepository.fetchSalePerformanceDetailById(id);
		 SalePerformanceDetailDto salePerformanceDetail = new SalePerformanceDetailDto();
		 String saleId = salePerformanceDetailForDBDto.getSaleId();
		 String createCardDate = salePerformanceDetailForDBDto.getCreateCardDate();
		 String beautifySkinItemName = salePerformanceDetailForDBDto.getBeautifySkinItemName();
		 long createCardTotalAmount = salePerformanceDetailForDBDto.getCreateCardTotalAmount();
		 long receivedAmount = salePerformanceDetailForDBDto.getReceivedAmount();
		 int itemNumber = salePerformanceDetailForDBDto.getItemNumber();
		 float cosmeticShopDiscount = salePerformanceDetailForDBDto.getCosmeticShopDiscount();
		 long employeePremium = new Float(salePerformanceDetailForDBDto.getEmployeePremium()).longValue();
		 long shopPremium = new Float(salePerformanceDetailForDBDto.getShopPremium()).longValue();
		 String description = salePerformanceDetailForDBDto.getDescription();
		 long debtAmount = createCardTotalAmount - receivedAmount;
		 long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;
		 long receivedEarnedAmount = salePerformanceDetailForDBDto.getReceivedEarnedAmount();
		 long debtEarnedAmount = earnedAmount - receivedEarnedAmount;
		 salePerformanceDetail.setSaleId(saleId);
		 salePerformanceDetail.setCreateCardDate(createCardDate);
		 salePerformanceDetail.setBeautifySkinItemName(beautifySkinItemName);
		 salePerformanceDetail.setCreateCardTotalAmount(createCardTotalAmount);
		 salePerformanceDetail.setReceivedAmount(receivedAmount);
		 salePerformanceDetail.setDebtAmount(debtAmount);
		 salePerformanceDetail.setEarnedAmount(earnedAmount);
		 salePerformanceDetail.setReceivedEarnedAmount(receivedEarnedAmount);
		 salePerformanceDetail.setDebtEarnedAmount(debtEarnedAmount);
		 salePerformanceDetail.setItemNumber(itemNumber);
		 salePerformanceDetail.setCosmeticShopDiscount(cosmeticShopDiscount);
		 salePerformanceDetail.setEmployeePremium(employeePremium);
		 salePerformanceDetail.setShopPremium(shopPremium);
		 salePerformanceDetail.setDescription(description);
		 return salePerformanceDetail;
	 }
}
