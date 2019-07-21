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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleClientItemSellerDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SaleDetailForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;
import com.yuehe.app.dto.SalePerformanceDetailForDBDTO;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDTO;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.repository.SaleRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = true)
public class SaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
	private final SaleRepository saleRepository;
	Sort sort = null;
	boolean sortByJPA = true;
	Comparator<SaleClientItemSellerDTO> comparator = null;
	private static enum SaleSortBy{

		ID, EMPLOYEENAME, CLIENTCOSMETICSHOPNAME, CLIENTNAME, BEAUTIFYSKINITEMNAME, CREATECARDDATE, CREATECARDTOTALAMOUNT,
		ITEMNUMBER,RECEIVEDAMOUNT, DISCOUNT, UNPAIDAMOUNT, EARNEDAMOUNT, RECEIVEDEARNEDAMOUNT, UNPAIDEARNEDAMOUNT,
		EMPLOYEEPREMIUM, SHOPPREMIUM;
 
	}
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
    public List<SaleBeautifySkinItemForFilterDTO> getSalesByClientId(String clientId) {
    	return saleRepository.findByClientId(clientId);
    }
    
    public Sale getSaleById(String id) {
    	return saleRepository.findById(id);
    }
    public SaleDetailForDBDTO getSaleBasicDetailById(String id) {
    	return saleRepository.fetchSaleBasicDetailById(id);
    }
    public List<SaleBeautifySkinItemForFilterDTO> getByClientId(String ClientId) {
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
	public Map<String,Object> getSalesDetailListWithPaginationAndSort(int pageNumber, int pageSize,Direction sortDirection,String sortProperty ) {
	
		Pageable pageable = null;
		buildSort(this.sort, this.sortByJPA, this.comparator, sortDirection, sortProperty);
		if(this.sortByJPA){

			 pageable = PageRequest.of(pageNumber, pageSize,this.sort);
		}
		else{
			 pageable = PageRequest.of(pageNumber, pageSize);

		}
		Page<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOPage = saleRepository.fetchSaleClientItemSellerData(pageable);
		List<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOList = new ArrayList<SaleClientItemSellerForDBDTO>();
		Map<String,Object> saleMap = new HashMap<String,Object>();
		 if(saleClientItemSellerForDBDTOPage.hasContent()) {
			 saleClientItemSellerForDBDTOList =  saleClientItemSellerForDBDTOPage.getContent();
	        }
		List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = new ArrayList<SaleClientItemSellerDTO>();
		buildSalesDetailList(saleClientItemSellerDTOList, saleClientItemSellerForDBDTOList);
		sortByCollectionsIfPropertyNotInDB(sortByJPA, sortDirection, comparator, saleClientItemSellerDTOList);
		saleMap.put("saleList",saleClientItemSellerDTOList);
		saleMap.put("salePage",saleClientItemSellerForDBDTOPage);
		return saleMap;
	}
	/**
	 * Get the sales detail list for downloading as csv, excel or pdf
	 * @param sortDirection
	 * @param sortProperty
	 * @return
	 */
	public Map<String, Object>  getSalesDetailListForDownload(Direction sortDirection,String sortProperty){
		this.sort = Sort.by(sortDirection, sortProperty);
		// boolean sortByJPA = true;
		List<Sort.Order> sortOrders = null;
		// Comparator<SaleClientItemSellerDTO> comparator = null;
	
		Map<String, Object> saleMap = new HashMap<String, Object>();
		buildSort(this.sort, this.sortByJPA, this.comparator, sortDirection, sortProperty);
		if(this.sortByJPA){

			sortOrders = this.sort.get().collect(Collectors.toList());
		}
		List<SaleClientItemSellerForDBDTO> 	saleClientItemSellerForDBDTOList = saleRepository.fetchSaleClientItemSellerDataForDownload(this.sort);
		List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = new ArrayList<SaleClientItemSellerDTO>();
		buildSalesDetailList(saleClientItemSellerDTOList, saleClientItemSellerForDBDTOList);
		sortByCollectionsIfPropertyNotInDB(sortByJPA, sortDirection, comparator, saleClientItemSellerDTOList);
		saleMap.put("csvObjList",saleClientItemSellerDTOList);
		saleMap.put("sortOrders", sortOrders);
		return saleMap;

	}
	
	private void buildSort(Sort sort, boolean sortByJPA, Comparator<SaleClientItemSellerDTO> comparator,
							Direction sortDirection,String sortProperty ){
		
		//Below code is using the table field to do the sorting, when it comes to the foreign table, you need to use the foreign
		//relationship to refer to it, like through entity "sale" to "client" to "cosmeticShop", you got "client.cosmeticshop.name"
		//but you can't have any "." inside a enum class, so you have to remove all the ".", then do the comparing
		SaleSortBy saleSortBy = SaleSortBy.valueOf(StringUtils.remove(sortProperty.toUpperCase(),'.'));
		switch(saleSortBy){
			case ID:
			case CREATECARDDATE:
			case CREATECARDTOTALAMOUNT:
			case RECEIVEDAMOUNT:
			case ITEMNUMBER:
			case RECEIVEDEARNEDAMOUNT:
			case EMPLOYEEPREMIUM:
			case SHOPPREMIUM:
				this.sort = Sort.by(new Order(sortDirection,sortProperty));
				this.sortByJPA = true;
				break;
				case CLIENTCOSMETICSHOPNAME:
				this.sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name"));
				this.sortByJPA = true;
				break;
				case CLIENTNAME:
				this.sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name")
				,new Order(sortDirection,"client.name"));
				this.sortByJPA = true;
				break;
				//below are a special sorting group, it's due to the relationship of entity "client","cosmeticShop" and "beautifySkinItem"
				//first you sort all the cosmeticShop, then group by the shop, sort all the clients, then group by the client, sort all the
				//beautifyskinitems.
				case BEAUTIFYSKINITEMNAME:
				this.sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name"),
				new Order(sortDirection,"client.name"),
				new Order(sortDirection,"beautifySkinItem.name"));
				this.sortByJPA = true;
				break;
				case EMPLOYEENAME:
				this.sort = Sort.by(new Order(sortDirection,"employee.name"));
				this.sortByJPA = true;
				break;
			case DISCOUNT:
				this.comparator = SaleClientItemSellerDTO.discountComparator;
				this.sortByJPA = false;
				break;
			case UNPAIDAMOUNT:
				this.comparator = SaleClientItemSellerDTO.unpaidAmountComparator;
				this.sortByJPA = false;
				break;
			case EARNEDAMOUNT:
				this.comparator = SaleClientItemSellerDTO.earnedAmountComparator;
				this.sortByJPA = false;
				break;
			case UNPAIDEARNEDAMOUNT:
				this.comparator = SaleClientItemSellerDTO.unpaidEarnedAmountComparator;
				this.sortByJPA = false;
				break;
			default:break;
			
		}
	}
	private void sortByCollectionsIfPropertyNotInDB(boolean sortByJPA, Direction sortDirection, Comparator<SaleClientItemSellerDTO> comparator,
									 List<SaleClientItemSellerDTO> saleClientItemSellerDTOList){
		if(!sortByJPA){
			if(sortDirection == Direction.ASC){

				Collections.sort(saleClientItemSellerDTOList,comparator);
			}else{
				
				Collections.sort(saleClientItemSellerDTOList,comparator.reversed());
			}
		}
	}
	private void buildSalesDetailList(List<SaleClientItemSellerDTO> saleClientItemSellerDTOList,List<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOList){
	
		for(SaleClientItemSellerForDBDTO saleClientItemSellerForDBDTO : saleClientItemSellerForDBDTOList) {
			long beautifySkinItemPrice = saleClientItemSellerForDBDTO.getBeautifySkinItemPrice();
			float cosmeticShopDiscount = saleClientItemSellerForDBDTO.getCosmeticShopDiscount();
			long createCardTotalAmount =  saleClientItemSellerForDBDTO.getCreateCardTotalAmount();
			long receivedAmount = saleClientItemSellerForDBDTO.getReceivedAmount();
			int itemNumber = saleClientItemSellerForDBDTO.getItemNumber();
			long receivedEarnedAmount = saleClientItemSellerForDBDTO.getReceivedEarnedAmount();
			Double discount = new Double(createCardTotalAmount)/new Double(beautifySkinItemPrice * itemNumber);
			long employeePremium = new Float(saleClientItemSellerForDBDTO.getEmployeePremium()).longValue();
		    long shopPremium = new Float(saleClientItemSellerForDBDTO.getShopPremium()).longValue();
		    long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;
			
			SaleClientItemSellerDTO saleClientItemSellerDTO = new SaleClientItemSellerDTO();
			saleClientItemSellerDTO.setBeautifySkinItemName(saleClientItemSellerForDBDTO.getBeautifySkinItemName());
			saleClientItemSellerDTO.setClientName(saleClientItemSellerForDBDTO.getClientName());
			saleClientItemSellerDTO.setCosmeticShopName(saleClientItemSellerForDBDTO.getCosmeticShopName());
			saleClientItemSellerDTO.setCreateCardDate(saleClientItemSellerForDBDTO.getCreateCardDate());
			saleClientItemSellerDTO.setCreateCardTotalAmount(createCardTotalAmount);
			saleClientItemSellerDTO.setDescription(saleClientItemSellerForDBDTO.getDescription());
			saleClientItemSellerDTO.setDiscount((float)(Math.round(discount*100.0)/100.0));
			saleClientItemSellerDTO.setEarnedAmount(earnedAmount);
			saleClientItemSellerDTO.setEmployeePremium(employeePremium);
			saleClientItemSellerDTO.setItemNumber(itemNumber);
			saleClientItemSellerDTO.setReceivedAmount(receivedAmount);
			saleClientItemSellerDTO.setReceivedEarnedAmount(receivedEarnedAmount);
			saleClientItemSellerDTO.setSaleId(saleClientItemSellerForDBDTO.getSaleId());
			saleClientItemSellerDTO.setSellerName(saleClientItemSellerForDBDTO.getSellerName());
			saleClientItemSellerDTO.setShopPremium(shopPremium);
			saleClientItemSellerDTO.setUnpaidAmount(createCardTotalAmount - receivedAmount);
			saleClientItemSellerDTO.setUnpaidEarnedAmount(earnedAmount - receivedEarnedAmount);
			
			saleClientItemSellerDTOList.add(saleClientItemSellerDTO);
		}
		saleClientItemSellerForDBDTOList.forEach(l -> System.out.println(l));
		saleClientItemSellerDTOList.forEach(l -> System.out.println(l));
	}
	// public void sort
	 public long getEntityNumber() {
	    	return saleRepository.count();
	    }
	 public YueHeAllSalesPerformanceDetailDTO getYueHeAllSalesPerformanceDetail() {
		 YueHeAllSalesPerformanceDetailDTO yueHeAllSalesPerformanceDetailDTO = new YueHeAllSalesPerformanceDetailDTO();
		 List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		 List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOList = new ArrayList<ShopAllSalesPerformanceDetailDTO>();
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
			 ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = getShopAllSalesPerformanceDetail(shopId);
			 allShopsSalesCreateCardTotalAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesCreateCardTotalAmount();
			 allShopsSalesReceivedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesReceivedAmount();
			 allShopsSalesDebtAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesDebtAmount();
			 allShopsSalesEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesDebtEarnedAmount();
			 allShopsSalesReceivedEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesReceivedEarnedAmount();
			 allShopsSalesDebtEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesDebtEarnedAmount();
			 allShopsSalesEmployeePremium += shopAllSalesPerformanceDetailDTO.getAllClientsSalesEmployeePremium();
			 allShopsSalesShopPremium += shopAllSalesPerformanceDetailDTO.getAllClientsSalesShopPremium();
			 shopAllSalesPerformanceDetailDTOList.add(shopAllSalesPerformanceDetailDTO);
			 
		 }
		 yueHeAllSalesPerformanceDetailDTO.setCompanyName("悦和国际");
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesCreateCardTotalAmount(allShopsSalesCreateCardTotalAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesReceivedAmount(allShopsSalesReceivedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesDebtAmount(allShopsSalesDebtAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesEarnedAmount(allShopsSalesEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesReceivedEarnedAmount(allShopsSalesReceivedEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesDebtEarnedAmount(allShopsSalesDebtEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesEmployeePremium(allShopsSalesEmployeePremium);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesShopPremium(allShopsSalesShopPremium);
		 yueHeAllSalesPerformanceDetailDTO.setShopAllSalesPerformanceDetailDTOs(shopAllSalesPerformanceDetailDTOList);
		 return yueHeAllSalesPerformanceDetailDTO;
	 }
	 public ShopAllSalesPerformanceDetailDTO getShopAllSalesPerformanceDetail(String shopId) {
		 String shopName = cosmeticShopService.getCosmeticShopById(shopId).getName();
		 ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = new ShopAllSalesPerformanceDetailDTO();
		 List<Client> clientListByShop = clientService.getClientsByShopId(shopId);
		 List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOList = new ArrayList<ClientAllSalesPerformanceDetailDTO>();
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
			 ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = getClientAllSalesPerformanceDetail(clientId);
			 allClientsSalesCreateCardTotalAmount += clientAllSalesPerformanceDetailDTO.getAllSalesCreateCardTotalAmount();
			 allClientsSalesReceivedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesReceivedAmount();
			 allClientsSalesDebtAmount += clientAllSalesPerformanceDetailDTO.getAllSalesDebtAmount();
			 allClientsSalesEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesEarnedAmount();
			 allClientsSalesReceivedEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesReceivedEarnedAmount();
			 allClientsSalesDebtEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesDebtEarnedAmount();
			 allClientsSalesEmployeePremium += clientAllSalesPerformanceDetailDTO.getAllSalesEmployeePremium();
			 allClientsSalesShopPremium += clientAllSalesPerformanceDetailDTO.getAllSalesShopPremium();
			 clientAllSalesPerformanceDetailDTOList.add(clientAllSalesPerformanceDetailDTO);
			 
		 }
		 shopAllSalesPerformanceDetailDTO.setCosmeticShopName(shopName);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesCreateCardTotalAmount(allClientsSalesCreateCardTotalAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesReceivedAmount(allClientsSalesReceivedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesDebtAmount(allClientsSalesDebtAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesEarnedAmount(allClientsSalesEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesReceivedEarnedAmount(allClientsSalesReceivedEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesDebtEarnedAmount(allClientsSalesDebtEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesEmployeePremium(allClientsSalesEmployeePremium);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesShopPremium(allClientsSalesShopPremium);
		 shopAllSalesPerformanceDetailDTO.setClientAllSalesPerformanceDetailDTOs(clientAllSalesPerformanceDetailDTOList);
		 return shopAllSalesPerformanceDetailDTO;
	 }
	 public ClientAllSalesPerformanceDetailDTO getClientAllSalesPerformanceDetail(String clientId) {
		 String clientName = clientService.getClientById(clientId).getName();
		 ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = new ClientAllSalesPerformanceDetailDTO();
		 List<SaleBeautifySkinItemForFilterDTO> saleListByClient = saleRepository.findByClientId(clientId);
		 List<SalePerformanceDetailDTO> salePerformanceDetailDTOList = new ArrayList<SalePerformanceDetailDTO>();
		 long allSalesCreateCardTotalAmount = 0;
		 long allSalesReceivedAmount = 0;
		 long allSalesDebtAmount = 0;
		 long allSalesEarnedAmount = 0;
		 long allSalesReceivedEarnedAmount = 0;
		 long allSalesDebtEarnedAmount = 0;
		 float allSalesEmployeePremium = 0;
		 float allSalesShopPremium = 0;
		 for(SaleBeautifySkinItemForFilterDTO saleBeautifySkinItemForFilterDTO : saleListByClient) {
    		 String saleId = saleBeautifySkinItemForFilterDTO.getSaleId();
    		 SalePerformanceDetailDTO salePerformanceDetailDTO = getSalePerformanceDetail(saleId);
    		 allSalesCreateCardTotalAmount += salePerformanceDetailDTO.getCreateCardTotalAmount();
    		 allSalesReceivedAmount += salePerformanceDetailDTO.getReceivedAmount();
    		 allSalesDebtAmount += salePerformanceDetailDTO.getDebtAmount();
    		 allSalesEarnedAmount += salePerformanceDetailDTO.getEarnedAmount();
    		 allSalesReceivedEarnedAmount += salePerformanceDetailDTO.getReceivedEarnedAmount();
    		 allSalesDebtEarnedAmount += salePerformanceDetailDTO.getDebtEarnedAmount();
    		 allSalesEmployeePremium += salePerformanceDetailDTO.getEmployeePremium();
    		 allSalesShopPremium += salePerformanceDetailDTO.getShopPremium();
    		 salePerformanceDetailDTOList.add(salePerformanceDetailDTO);
    		 
    	 }
		 clientAllSalesPerformanceDetailDTO.setClientName(clientName);
		 clientAllSalesPerformanceDetailDTO.setAllSalesCreateCardTotalAmount(allSalesCreateCardTotalAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesReceivedAmount(allSalesReceivedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesDebtAmount(allSalesDebtAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesEarnedAmount(allSalesEarnedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesReceivedEarnedAmount(allSalesReceivedEarnedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesDebtEarnedAmount(allSalesDebtEarnedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesEmployeePremium(allSalesEmployeePremium);
		 clientAllSalesPerformanceDetailDTO.setAllSalesShopPremium(allSalesShopPremium);
		 clientAllSalesPerformanceDetailDTO.setSalePerformanceDetailDTOs(salePerformanceDetailDTOList);
		 return clientAllSalesPerformanceDetailDTO;
	 }
	 public SalePerformanceDetailDTO getSalePerformanceDetail(String id){
		 SalePerformanceDetailForDBDTO salePerformanceDetailForDBDTO = saleRepository.fetchSalePerformanceDetailById(id);
		 SalePerformanceDetailDTO salePerformanceDetail = new SalePerformanceDetailDTO();
		 String saleId = salePerformanceDetailForDBDTO.getSaleId();
		 String createCardDate = salePerformanceDetailForDBDTO.getCreateCardDate();
		 String beautifySkinItemName = salePerformanceDetailForDBDTO.getBeautifySkinItemName();
		 long createCardTotalAmount = salePerformanceDetailForDBDTO.getCreateCardTotalAmount();
		 long receivedAmount = salePerformanceDetailForDBDTO.getReceivedAmount();
		 int itemNumber = salePerformanceDetailForDBDTO.getItemNumber();
		 float cosmeticShopDiscount = salePerformanceDetailForDBDTO.getCosmeticShopDiscount();
		 long employeePremium = new Float(salePerformanceDetailForDBDTO.getEmployeePremium()).longValue();
		 long shopPremium = new Float(salePerformanceDetailForDBDTO.getShopPremium()).longValue();
		 String description = salePerformanceDetailForDBDTO.getDescription();
		 long debtAmount = createCardTotalAmount - receivedAmount;
		 long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;
		 long receivedEarnedAmount = salePerformanceDetailForDBDTO.getReceivedEarnedAmount();
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
