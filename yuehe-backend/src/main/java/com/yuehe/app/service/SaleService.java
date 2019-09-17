/*
    Copyright (C) 2019 Yi Xiang Zhong

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yuehe.app.common.SaleColumnsEnum;
import com.yuehe.app.common.YueHeEntitiesEnum;
import com.yuehe.app.dto.ClientAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.ClientShopDiscountDTO;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleCardAmountAdjustDTO;
import com.yuehe.app.dto.SaleCardAmountAdjustTotalDTO;
import com.yuehe.app.dto.SaleClientItemSellerDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SaleCreateOrAdjustDTO;
import com.yuehe.app.dto.SaleDetailForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailDTO;
import com.yuehe.app.dto.SalePerformanceDetailForDBDTO;
import com.yuehe.app.dto.ShopAllSalesPerformanceDetailDTO;
import com.yuehe.app.dto.YueHeAllSalesPerformanceDetailDTO;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.SaleCardAmountAdjust;
import com.yuehe.app.entity.ShopRefundRule;
import com.yuehe.app.property.BaseProperty;
import com.yuehe.app.repository.SaleRepository;
import com.yuehe.app.specification.SpecificationsBuilder;
import com.yuehe.app.util.YueHeUtil;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)//changed true to false to enable delete sale operation, otherwise it won't let it to modify the db
public class SaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
	private final SaleRepository saleRepository;
	Sort sort = null;
	boolean sortByJPA = true;
	Comparator<SaleClientItemSellerDTO> comparator = null;

	@Autowired
	private final ClientService clientService;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	@Autowired
	private final SaleCardAmountAdjustService saleCardAmountAdjustService;
	@Autowired
	private final ShopRefundRuleService shopRefundRuleService;

	public SaleService(SaleRepository saleRepository,ClientService clientService,ShopRefundRuleService shopRefundRuleService,
						CosmeticShopService cosmeticShopService, SaleCardAmountAdjustService saleCardAmountAdjustService) {
        this.saleRepository = saleRepository;
        this.clientService = clientService;
		this.cosmeticShopService = cosmeticShopService;
		this.saleCardAmountAdjustService = saleCardAmountAdjustService;
		this.shopRefundRuleService = shopRefundRuleService;
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
    
    public Sale getById(String id) {
    	return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid sale Id:" + id));
    }
    public void deleteById(String id) {
    	 saleRepository.deleteById(id);
    }
    public void delete(Sale sale) {
    	 saleRepository.delete(sale);
    }
    public SaleDetailForDBDTO getSaleBasicDetailById(String id) {
    	return saleRepository.fetchSaleBasicDetailById(id);
    }
    public List<SaleBeautifySkinItemForFilterDTO> getByClientId(String ClientId) {
    	return saleRepository.findByClientId(ClientId);
    }
    public List<SaleCreateOrAdjustDTO> getAllSalesByClientId(String ClientId) {
    	return saleRepository.findAllSalesByClientId(ClientId);
    }
    public List<SaleCreateOrAdjustDTO> getAllCreatSalesByClientIdAndCreateCardDate(String ClientId, String startDate, String endDate) {
    	return saleRepository.findByClientIdAndCreateCardDate(ClientId, startDate, endDate);
    }
    public List<SaleCreateOrAdjustDTO> getAllAdjustSalesByClientIdAndAdjustDate(String ClientId, String startDate, String endDate) {
    	return saleRepository.findByClientIdAndItsSaleAdjustDate(ClientId, startDate, endDate);
    }
    // public Sale getSaleByClientNameAndShopNameAndItemNameAndCreateCardDate(String clientName, String cosmeticShopName,
    // 																		String beautifySkinItemName, String createCardDate) {
    // 	client = clientService.getClientByClientNameAndShopName(clientName,cosmeticShopName);
    // 	beautifySkinItem = beautifySkinItemService.getBeautifySkinItemByName(beautifySkinItemName);
    // 	return saleRepository.findByClientIdAndItemIdAndCreateCardDate(client.getId(),beautifySkinItem.getId(),createCardDate);
    // }
    @Transactional(rollbackFor = Exception.class)
    public List<Sale> saveAll(List<Sale> sale) {
        LOGGER.info("Saving {}", sale);
        return saleRepository.saveAll(sale);
    }
	public Map<String,Object> getSalesDetailListWithPaginationAndSort(int pageNumber, int pageSize,Direction sortDirection,String sortProperty ) {
		Pageable pageable = buildPaginationAndSort(pageNumber,pageSize,sortDirection,sortProperty);
		
		Page<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOPage = saleRepository.fetchSaleClientItemSellerData(pageable);
		Map<String,Object> saleMap = buildSalesDetailList(saleClientItemSellerForDBDTOPage,sortDirection);
		
		return saleMap;
	}
	/**
	 * to get all the sales detail with pagination and sort and filtering,  can't use @query to do the dynamic query
	 * can't use a DTO class to store the dynamic querired result, so use findAll method
	 */
	public Map<String,Object> getSalesDetailListWithPaginationAndSortAndFiltering(int pageNumber, int pageSize,Direction sortDirection,String sortProperty,String searchParameters ) {
		Pageable pageable = buildPaginationAndSort(pageNumber,pageSize,sortDirection,sortProperty);
		SpecificationsBuilder<Sale> specificationsBuilder = new SpecificationsBuilder<>();
		Specification<Sale> spec = specificationsBuilder.resolveSpecification(searchParameters,YueHeEntitiesEnum.SALE);
		LOGGER.info("spec {}",spec);
		// Page<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOPage = saleRepository.fetchSaleClientItemSellerDataWithFiltering(spec,pageable);
		Page<Sale> salePage = saleRepository.findAll(spec,pageable);

		Map<String,Object> saleMap = buildFilteredSalesDetailList(salePage,sortDirection);
		
		return saleMap;
	}
	// public void  buildFilteringPredicate(String searchParameters){
	// 		String operationSetExper = Joiner.on("|")
	// 		.join(SearchOperation.SIMPLE_OPERATION_SET);
	// 	Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
	// 	Matcher matcher = pattern.matcher(searchParameters + ",");
	// 	while (matcher.find()) {
	// 		builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
	// 	}
	// }
	public Map<String,Object> buildSalesDetailList(Page<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOPage,Direction sortDirection){
		List<SaleClientItemSellerForDBDTO> saleClientItemSellerForDBDTOList = new ArrayList<SaleClientItemSellerForDBDTO>();
		Map<String,Object> saleMap = new HashMap<String,Object>();
		 if(saleClientItemSellerForDBDTOPage.hasContent()) {
			 saleClientItemSellerForDBDTOList =  saleClientItemSellerForDBDTOPage.getContent();
	        }
		List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = new ArrayList<SaleClientItemSellerDTO>();
		buildSalesDetailList(saleClientItemSellerDTOList, saleClientItemSellerForDBDTOList);
		sortByCollectionsIfPropertyNotInDB(sortDirection, saleClientItemSellerDTOList);
		saleMap.put("saleList",saleClientItemSellerDTOList);
		saleMap.put("salePage",saleClientItemSellerForDBDTOPage);
		return saleMap;
	}
	/**
	 *  can't use a DTO class to store the dynamic querired result, need to do the manual mapping from Sale to saleClientItemSellerDTO
	 * 
	 */
	public Map<String,Object> buildFilteredSalesDetailList(Page<Sale> salePage,Direction sortDirection){
		List<Sale> saleList = new ArrayList<Sale>();
		Map<String,Object> saleMap = new HashMap<String,Object>();
		 if(salePage.hasContent()) {
			saleList =  salePage.getContent();
	        }
		List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = new ArrayList<SaleClientItemSellerDTO>();
		buildFilteredSalesDetailListForFrontEnd(saleClientItemSellerDTOList, saleList);
		sortByCollectionsIfPropertyNotInDB( sortDirection, saleClientItemSellerDTOList);
		saleMap.put("saleList",saleClientItemSellerDTOList);
		saleMap.put("salePage",salePage);
		return saleMap;
	}
	public Pageable buildPaginationAndSort(int pageNumber, int pageSize,Direction sortDirection,String sortProperty){
		Pageable pageable = null;
		buildSort(sortDirection, sortProperty);
		if(sortByJPA){

			 pageable = PageRequest.of(pageNumber, pageSize,sort);
		}
		else{
			 pageable = PageRequest.of(pageNumber, pageSize);

		}
		return pageable;
	}
	/**
	 * Get the sales detail list for downloading as csv, excel or pdf
	 * @param sortDirection
	 * @param sortProperty
	 * @return
	 */
	public Map<String, Object>  getSalesDetailListForDownload(Direction sortDirection,String sortProperty){
		sort = Sort.by(sortDirection, sortProperty);
		List<SaleClientItemSellerForDBDTO> 	saleClientItemSellerForDBDTOList = new ArrayList<SaleClientItemSellerForDBDTO>();
		List<SaleClientItemSellerDTO> saleClientItemSellerDTOList = new ArrayList<SaleClientItemSellerDTO>();
		
		List<Sort.Order> sortOrders = null;
		// Comparator<SaleClientItemSellerDTO> comparator = null;
	
		Map<String, Object> saleMap = new HashMap<String, Object>();
		buildSort(sortDirection, sortProperty);
		if(sortByJPA){

			sortOrders = sort.get().collect(Collectors.toList());
			saleClientItemSellerForDBDTOList = saleRepository.fetchSaleClientItemSellerDataForDownloadWithSort(sort);
		}else{
			saleClientItemSellerForDBDTOList = saleRepository.fetchSaleClientItemSellerDataForDownload();

		}
		
		buildSalesDetailList(saleClientItemSellerDTOList, saleClientItemSellerForDBDTOList);
		sortByCollectionsIfPropertyNotInDB(sortDirection,saleClientItemSellerDTOList);
		saleMap.put("csvObjList",saleClientItemSellerDTOList);
		saleMap.put("sortOrders", sortOrders);
		return saleMap;

	}
	
	private void buildSort(	Direction sortDirection,String sortProperty ){
		
		//Below code is using the table field to do the sorting, when it comes to the foreign table, you need to use the foreign
		//relationship to refer to it, like through entity "sale" to "client" to "cosmeticShop", you got "client.cosmeticshop.name"
		//but you can't have any "." inside a enum class, so you have to remove all the ".", then do the comparing
		SaleColumnsEnum saleSortBy = SaleColumnsEnum.valueOf(StringUtils.remove(sortProperty.toUpperCase(),'.'));
		switch(saleSortBy){
			case ID:
			case CREATECARDDATE:
			case CREATECARDTOTALAMOUNT:
			case RECEIVEDAMOUNT:
			case ITEMNUMBER:
			case RECEIVEDEARNEDAMOUNT:
			case EMPLOYEEPREMIUM:
			case SHOPPREMIUM:
			case DESCRIPTION:
				sort = Sort.by(new Order(sortDirection,sortProperty));
				sortByJPA = true;
				break;
			case CLIENTCOSMETICSHOPNAME:
			sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name"));
			sortByJPA = true;
			break;
			case CLIENTNAME:
			sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name")
			,new Order(sortDirection,"client.name"));
			sortByJPA = true;
			break;
			//below are a special sorting group, it's due to the relationship of entity "client","cosmeticShop" and "beautifySkinItem"
			//first you sort all the cosmeticShop, then group by the shop, sort all the clients, then group by the client, sort all the
			//beautifyskinitems.
			case BEAUTIFYSKINITEMNAME:
			sort = Sort.by(new Order(sortDirection,"client.cosmeticShop.name"),
			new Order(sortDirection,"client.name"),
			new Order(sortDirection,"beautifySkinItem.name"));
			sortByJPA = true;
			break;
			case EMPLOYEENAME:
			sort = Sort.by(new Order(sortDirection,"employee.name"));
			sortByJPA = true;
			break;
			case DISCOUNT:
				comparator = SaleClientItemSellerDTO.discountComparator;
				sortByJPA = false;
				break;
			case UNPAIDAMOUNT:
				comparator = SaleClientItemSellerDTO.unpaidAmountComparator;
				sortByJPA = false;
				break;
			case EARNEDAMOUNT:
				comparator = SaleClientItemSellerDTO.earnedAmountComparator;
				sortByJPA = false;
				break;
			case UNPAIDEARNEDAMOUNT:
				comparator = SaleClientItemSellerDTO.unpaidEarnedAmountComparator;
				sortByJPA = false;
				break;
			default:break;
			
		}
	}
	private void sortByCollectionsIfPropertyNotInDB( Direction sortDirection,List<SaleClientItemSellerDTO> saleClientItemSellerDTOList){
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
			Integer beautifySkinItemPrice = saleClientItemSellerForDBDTO.getBeautifySkinItemPrice();
			Float cosmeticShopDiscount = saleClientItemSellerForDBDTO.getCosmeticShopDiscount();
			Long createCardTotalAmount =  saleClientItemSellerForDBDTO.getCreateCardTotalAmount();
			Long receivedAmount = saleClientItemSellerForDBDTO.getReceivedAmount();
			Integer itemNumber = saleClientItemSellerForDBDTO.getItemNumber();
			Long receivedEarnedAmount = saleClientItemSellerForDBDTO.getReceivedEarnedAmount();
			Double discount = new Double(createCardTotalAmount)/new Double(beautifySkinItemPrice * itemNumber);
			//using Optional orElse to handle null value of employeePremium and shopPremium, if null, assign 0 to it to avoid nullpointerexception
			Float employeePremium = Optional.ofNullable(saleClientItemSellerForDBDTO.getEmployeePremium()).orElse(new Float(0));
		    Float shopPremium = Optional.ofNullable(saleClientItemSellerForDBDTO.getShopPremium()).orElse(new Float(0));
		    Float earnedAmount = createCardTotalAmount*cosmeticShopDiscount - employeePremium - shopPremium;
			Float currentEarnedAmount = receivedAmount*cosmeticShopDiscount - employeePremium - shopPremium;//按实际收到客户款数额计算的应收回款
			
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
			saleClientItemSellerDTO.setCurrentEarnedAmount(currentEarnedAmount);
			saleClientItemSellerDTO.setSaleId(saleClientItemSellerForDBDTO.getSaleId());
			saleClientItemSellerDTO.setSellerName(saleClientItemSellerForDBDTO.getSellerName());
			saleClientItemSellerDTO.setShopPremium(shopPremium);
			saleClientItemSellerDTO.setUnpaidAmount(createCardTotalAmount - receivedAmount);
			saleClientItemSellerDTO.setUnpaidEarnedAmount(earnedAmount - receivedEarnedAmount);
			
			saleClientItemSellerDTOList.add(saleClientItemSellerDTO);
		}
		// saleClientItemSellerForDBDTOList.forEach(l -> System.out.println(l));
		// saleClientItemSellerDTOList.forEach(l -> System.out.println(l));
	}
	/**
	 * To build the sale list page with entity "Sale" while using dynamic query
	 */
	private void buildFilteredSalesDetailListForFrontEnd(List<SaleClientItemSellerDTO> saleClientItemSellerDTOList,List<Sale> saleList){
	
		for(Sale sale : saleList) {
			Integer beautifySkinItemPrice = sale.getBeautifySkinItem().getPrice();
			Float cosmeticShopDiscount = sale.getClient().getCosmeticShop().getDiscount();
			Long createCardTotalAmount =  sale.getCreateCardTotalAmount();
			Long receivedAmount = sale.getReceivedAmount();
			Integer itemNumber = sale.getItemNumber();
			Long receivedEarnedAmount = sale.getReceivedEarnedAmount();//店家实际回给悦和的款
			Double discount = new Double(createCardTotalAmount)/new Double(beautifySkinItemPrice * itemNumber);
			//using Optional orElse to handle null value of employeePremium and shopPremium, if null, assign 0 to it to avoid nullpointerexception
			Float employeePremium = Optional.ofNullable(sale.getEmployeePremium()).orElse(new Float(0));
		    Float shopPremium = Optional.ofNullable(sale.getShopPremium()).orElse(new Float(0));
		    Float earnedAmount = createCardTotalAmount*cosmeticShopDiscount - employeePremium - shopPremium;//按开卡金额计算的应收回款
		    Float currentEarnedAmount = receivedAmount*cosmeticShopDiscount - employeePremium - shopPremium;//按实际收到客户款数额计算的应收回款
			
			SaleClientItemSellerDTO saleClientItemSellerDTO = new SaleClientItemSellerDTO();
			saleClientItemSellerDTO.setBeautifySkinItemName(sale.getBeautifySkinItem().getName());
			saleClientItemSellerDTO.setClientName(sale.getClient().getName());
			saleClientItemSellerDTO.setCosmeticShopName(sale.getClient().getCosmeticShop().getName());
			saleClientItemSellerDTO.setCreateCardDate(sale.getCreateCardDate());
			saleClientItemSellerDTO.setCreateCardTotalAmount(createCardTotalAmount);
			saleClientItemSellerDTO.setDescription(sale.getDescription());
			saleClientItemSellerDTO.setDiscount((float)(Math.round(discount*100.0)/100.0));
			saleClientItemSellerDTO.setEarnedAmount(earnedAmount);
			saleClientItemSellerDTO.setEmployeePremium(employeePremium);
			saleClientItemSellerDTO.setItemNumber(itemNumber);
			saleClientItemSellerDTO.setReceivedAmount(receivedAmount);
			saleClientItemSellerDTO.setCurrentEarnedAmount(currentEarnedAmount);
			saleClientItemSellerDTO.setReceivedEarnedAmount(receivedEarnedAmount);
			saleClientItemSellerDTO.setSaleId(sale.getId());
			saleClientItemSellerDTO.setSellerName(sale.getEmployee().getName());
			saleClientItemSellerDTO.setShopPremium(shopPremium);
			saleClientItemSellerDTO.setUnpaidAmount(createCardTotalAmount - receivedAmount);
			saleClientItemSellerDTO.setUnpaidEarnedAmount(earnedAmount - receivedEarnedAmount);
			
			saleClientItemSellerDTOList.add(saleClientItemSellerDTO);
		}
		// saleList.forEach(l -> System.out.println(l));
		// saleClientItemSellerDTOList.forEach(l -> System.out.println(l));
	}
	/**
	 * To get the biggest number of the current string id 
	 */ 
	 public int getBiggestIdNumber() {
		List<Sale> saleList = saleRepository.findAllIds();
		Collections.sort(saleList,Sale.idComparator.reversed());
		String biggestId = saleList.get(0).getId();
		int biggestIdNum = YueHeUtil.extractIdDigitalNumber(biggestId);
		LOGGER.info("biggest Id Number-{}",biggestIdNum);
	    return biggestIdNum;
	}
	 public YueHeAllSalesPerformanceDetailDTO getYueHeAllSalesPerformanceDetail(String startDate, String endDate) {
		 YueHeAllSalesPerformanceDetailDTO yueHeAllSalesPerformanceDetailDTO = new YueHeAllSalesPerformanceDetailDTO();
		 List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		 List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOList = new ArrayList<ShopAllSalesPerformanceDetailDTO>();
		 long allShopsSalesCreateCardTotalAmount = 0;
		 long allShopsSalesReceivedAmount = 0;
		 long allShopsSalesDebtAmount = 0;
		 long allShopsSalesEarnedAmount = 0;
		 long allShopsSalesCurrentEarnedAmount = 0;
		 long allShopsSalesDebtEarnedAmount = 0;
		 float allShopsSalesEmployeePremium = 0;
		 float allShopsSalesShopPremium = 0;
		 for(CosmeticShop cosmeticShop : cosmeticShopList) {
			 String shopId = cosmeticShop.getId();
			 ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = getShopAllSalesPerformanceDetail(shopId, startDate, endDate);
			 allShopsSalesCreateCardTotalAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesCreateCardTotalAmount();
			 allShopsSalesReceivedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesReceivedAmount();
			 allShopsSalesDebtAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesDebtAmount();
			 allShopsSalesEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesEarnedAmount();
			 allShopsSalesCurrentEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesCurrentEarnedAmount();
			 allShopsSalesDebtEarnedAmount += shopAllSalesPerformanceDetailDTO.getAllClientsSalesDebtEarnedAmount();
			 allShopsSalesEmployeePremium += shopAllSalesPerformanceDetailDTO.getAllClientsSalesEmployeePremium();
			 allShopsSalesShopPremium += shopAllSalesPerformanceDetailDTO.getAllClientsSalesShopPremium();
			 shopAllSalesPerformanceDetailDTOList.add(shopAllSalesPerformanceDetailDTO);
			 
			//  LOGGER.debug("shopId {},allShopsSalesEarnedAmount {}",shopId,allShopsSalesEarnedAmount);
		 }
		 yueHeAllSalesPerformanceDetailDTO.setCompanyName(BaseProperty.COMPANY_NAME);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesCreateCardTotalAmount(allShopsSalesCreateCardTotalAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesReceivedAmount(allShopsSalesReceivedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesDebtAmount(allShopsSalesDebtAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesEarnedAmount(allShopsSalesEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesCurrentEarnedAmount(allShopsSalesCurrentEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesDebtEarnedAmount(allShopsSalesDebtEarnedAmount);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesEmployeePremium(allShopsSalesEmployeePremium);
		 yueHeAllSalesPerformanceDetailDTO.setAllShopsSalesShopPremium(allShopsSalesShopPremium);
		 yueHeAllSalesPerformanceDetailDTO.setShopAllSalesPerformanceDetailDTOs(shopAllSalesPerformanceDetailDTOList);
		//  LOGGER.debug("yueHeAllSalesPerformanceDetailDTO {}",yueHeAllSalesPerformanceDetailDTO);
		 return yueHeAllSalesPerformanceDetailDTO;
	 }
	 public ShopAllSalesPerformanceDetailDTO getShopAllSalesPerformanceDetail(String shopId, String startDate, String endDate) {
		 String shopName = cosmeticShopService.getById(shopId).getName();
		 ShopAllSalesPerformanceDetailDTO shopAllSalesPerformanceDetailDTO = new ShopAllSalesPerformanceDetailDTO();
		 List<ClientShopDiscountDTO> clientListByShop = clientService.getClientsByShopId(shopId);
		 List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOList = new ArrayList<ClientAllSalesPerformanceDetailDTO>();
		 long allClientsSalesCreateCardTotalAmount = 0;
		 long allClientsSalesReceivedAmount = 0;
		 long allClientsSalesDebtAmount = 0;
		 long allClientsSalesEarnedAmount = 0;
		 long allClientsSalesCurrentEarnedAmount = 0;
		 long allClientsSalesDebtEarnedAmount = 0;
		 float allClientsSalesEmployeePremium = 0;
		 float allClientsSalesShopPremium = 0;
		 for(ClientShopDiscountDTO client : clientListByShop) {
			 String clientId = client.getId();
			 ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = getClientAllSalesPerformanceDetail(clientId, startDate, endDate);
			 allClientsSalesCreateCardTotalAmount += clientAllSalesPerformanceDetailDTO.getAllSalesCreateCardTotalAmount();
			 allClientsSalesReceivedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesReceivedAmount();
			 allClientsSalesDebtAmount += clientAllSalesPerformanceDetailDTO.getAllSalesDebtAmount();
			 allClientsSalesEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesEarnedAmount();
			 allClientsSalesCurrentEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesCurrentEarnedAmount();
			 allClientsSalesDebtEarnedAmount += clientAllSalesPerformanceDetailDTO.getAllSalesDebtEarnedAmount();
			 allClientsSalesEmployeePremium += clientAllSalesPerformanceDetailDTO.getAllSalesEmployeePremium();
			 allClientsSalesShopPremium += clientAllSalesPerformanceDetailDTO.getAllSalesShopPremium();
			 clientAllSalesPerformanceDetailDTOList.add(clientAllSalesPerformanceDetailDTO);
			 
			//  LOGGER.debug("clientId {},allClientsSalesEarnedAmount {}",clientId,allClientsSalesEarnedAmount);
		 }
		 shopAllSalesPerformanceDetailDTO.setCosmeticShopName(shopName);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesCreateCardTotalAmount(allClientsSalesCreateCardTotalAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesReceivedAmount(allClientsSalesReceivedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesDebtAmount(allClientsSalesDebtAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesEarnedAmount(allClientsSalesEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesCurrentEarnedAmount(allClientsSalesCurrentEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesDebtEarnedAmount(allClientsSalesDebtEarnedAmount);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesEmployeePremium(allClientsSalesEmployeePremium);
		 shopAllSalesPerformanceDetailDTO.setAllClientsSalesShopPremium(allClientsSalesShopPremium);
		 shopAllSalesPerformanceDetailDTO.setClientAllSalesPerformanceDetailDTOs(clientAllSalesPerformanceDetailDTOList);
		 return shopAllSalesPerformanceDetailDTO;
	 }
	 public ClientAllSalesPerformanceDetailDTO getClientAllSalesPerformanceDetail(String clientId, String startDate, String endDate) {
		 String clientName = clientService.getById(clientId).getName();
		 ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO = new ClientAllSalesPerformanceDetailDTO();
		 List<SaleCreateOrAdjustDTO> saleListByClient = new ArrayList<SaleCreateOrAdjustDTO>();
		//  if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){

		// 	saleListByClient = getDistinctSaleList(getAllSalesByClientId(clientId));

		// }else{
			saleListByClient = getDistinctSaleList(getAllCreatSalesByClientIdAndCreateCardDate(clientId, startDate, endDate));//获取某个时段所有开卡的销售业绩
			saleListByClient.addAll(getAllAdjustSalesByClientIdAndAdjustDate(clientId, startDate, endDate));//获取某个时段所有退补款的销售业绩

		// }
		//  List<SaleBeautifySkinItemForFilterDTO> saleListByClient = saleRepository.findByClientId(clientId, startDate, endDate);
		 List<SalePerformanceDetailDTO> salePerformanceDetailDTOList = new ArrayList<SalePerformanceDetailDTO>();
		 long allSalesCreateCardTotalAmount = 0;
		 long allSalesReceivedAmount = 0;
		 long allSalesDebtAmount = 0;
		 long allSalesEarnedAmount = 0;
		 long allSalesCurrentEarnedAmount = 0;
		 long allSalesDebtEarnedAmount = 0;
		 float allSalesEmployeePremium = 0;
		 float allSalesShopPremium = 0;
		 //按照销售卡ID排序，显示开卡日期时的业绩和后期补退尾款时的业绩
		 Collections.sort(saleListByClient, SaleCreateOrAdjustDTO.saleIdComparator);
		 for(SaleCreateOrAdjustDTO saleCreateOrAdjustDTO : saleListByClient) {
    		 String saleId = saleCreateOrAdjustDTO.getSaleId();
    		 Long saleAdjustId = saleCreateOrAdjustDTO.getSaleAdjustId();
    		 SalePerformanceDetailDTO salePerformanceDetailDTO = getSalePerformanceDetail(saleId,saleAdjustId, startDate, endDate);
    		 allSalesCreateCardTotalAmount += salePerformanceDetailDTO.getCreateCardTotalAmount();
    		 allSalesReceivedAmount += salePerformanceDetailDTO.getReceivedAmount();
    		 allSalesDebtAmount += salePerformanceDetailDTO.getDebtAmount();
    		 allSalesEarnedAmount += salePerformanceDetailDTO.getEarnedAmount();
    		 allSalesCurrentEarnedAmount += salePerformanceDetailDTO.getCurrentEarnedAmount();
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
		 clientAllSalesPerformanceDetailDTO.setAllSalesCurrentEarnedAmount(allSalesCurrentEarnedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesDebtEarnedAmount(allSalesDebtEarnedAmount);
		 clientAllSalesPerformanceDetailDTO.setAllSalesEmployeePremium(allSalesEmployeePremium);
		 clientAllSalesPerformanceDetailDTO.setAllSalesShopPremium(allSalesShopPremium);
		 clientAllSalesPerformanceDetailDTO.setSalePerformanceDetailDTOs(salePerformanceDetailDTOList);
		 return clientAllSalesPerformanceDetailDTO;
	 }
	 private List<SaleCreateOrAdjustDTO> getDistinctSaleList(List<SaleCreateOrAdjustDTO> saleListByClient){
		 	//获取该客户的所有销售卡和销售卡更改表左连接后的列表，会有重复
			 for(SaleCreateOrAdjustDTO saleCreateOrAdjustDTO : saleListByClient) {
				if(saleCreateOrAdjustDTO.getSaleAdjustId() != null)
					saleCreateOrAdjustDTO.setSaleAdjustId(0l);//销售卡列表包括开卡业绩和补退款业绩，此处设为0，标识为计算销售卡开卡业绩
			}
			//去除重复
			saleListByClient = saleListByClient.stream().distinct().collect(Collectors.toList());
			return saleListByClient;
	 }
	 public SalePerformanceDetailDTO getSalePerformanceDetail(String saleId, Long saleAdjustId, String startDate, String endDate){
		
		//获取该销售卡的收款信息
		SalePerformanceDetailForDBDTO salePerformanceDetailForDBDTO = saleRepository.fetchSalePerformanceDetailById(saleId);
		long createCardTotalAmount = salePerformanceDetailForDBDTO.getCreateCardTotalAmount();
		long receivedAmount = salePerformanceDetailForDBDTO.getReceivedAmount();
		long currentEarnedAmount = 0l;
		long awardEmployeeAmount = 0l;
		long employeePremium = new Float(salePerformanceDetailForDBDTO.getEmployeePremium()).longValue();
		long shopPremium = new Float(salePerformanceDetailForDBDTO.getShopPremium()).longValue();
		String description = salePerformanceDetailForDBDTO.getDescription();
		SalePerformanceDetailDTO salePerformanceDetail = new SalePerformanceDetailDTO();
		float cosmeticShopDiscount = salePerformanceDetailForDBDTO.getCosmeticShopDiscount();
		String createCardDate = salePerformanceDetailForDBDTO.getCreateCardDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date createCardDateObj = null;
		Date startDateObj = null;
		Date endDateObj = null;
		Date startAdjustDateObj = null;
		Date endAdjustDateObj = null;
		try {
			createCardDateObj = sdf.parse(createCardDate);
			startDateObj = sdf.parse(startDate);
			endDateObj = sdf.parse(endDate);
			startAdjustDateObj = sdf.parse("2016-01-01");
			endAdjustDateObj = sdf.parse(LocalDate.now().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取该销售卡所属客户所属美容院的回款规则
		Sale sale = getById(saleId);
		String cosmeticShopId = sale.getClient().getCosmeticShop().getId();
		List<ShopRefundRule> shopRefundRuleList = shopRefundRuleService.getByShopId(cosmeticShopId);
		Collections.reverse(shopRefundRuleList);
		for(ShopRefundRule shopRefundRule : shopRefundRuleList){
			String refundType = shopRefundRule.getAdjustAction();
			Double adjustAmount = shopRefundRule.getAdjustAmount();
			String triggerPoint = shopRefundRule.getTriggerPoint();
			String adjustDate = shopRefundRule.getAdjustDate();
			try {
				startAdjustDateObj = sdf.parse(adjustDate);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(StringUtils.equals(BaseProperty.REFUND_TYPE_CHANGE_DISCOUNT, refundType)){
				if(createCardDateObj.after(startAdjustDateObj) && createCardDateObj.before(endAdjustDateObj)){
					cosmeticShopDiscount = adjustAmount.floatValue();
				}
				
			}else if(StringUtils.equals(BaseProperty.REFUND_TYPE_REACH_CERTAIN_AMOUNT, refundType)){

			}else if(StringUtils.equals(BaseProperty.REFUND_TYPE_AWARD_EMPLOYEE_PER_SALE, refundType)){
				cosmeticShopDiscount -= adjustAmount.floatValue();
				
			}else if(StringUtils.equals(BaseProperty.REFUND_TYPE_AWARD_EMPLOYEE_PER_TRYOUT, refundType)){
				if(sale.getBeautifySkinItem().getPrice() == Integer.parseInt(triggerPoint)){
					awardEmployeeAmount = adjustAmount.longValue();
				}
			}
			endAdjustDateObj = startAdjustDateObj;
			
		}
		
		//计算开卡时的业绩，即首付款
		SaleCardAmountAdjustTotalDTO saleCardAmountAdjustTotalDTOForCreatCardAmount = getSaleCardAmountAdjustTotalDetail("", "", saleId);
		long createCardEmployeePremium = employeePremium-saleCardAmountAdjustTotalDTOForCreatCardAmount.getSaleCardAmountAdjustEmployeePremiumTotalAmounts();
		long createCardShopPremium = shopPremium-saleCardAmountAdjustTotalDTOForCreatCardAmount.getSaleCardAmountAdjustShopPremiumTotalAmounts();
		long createCardReceivedAmount = receivedAmount-saleCardAmountAdjustTotalDTOForCreatCardAmount.getSaleCardAmountAdjustTotalAmounts();

		
		//如果有日期刷选，则需计算某个时间段内所有的开卡时业绩和补退款业绩，存在这个时间段内补退款但是在这个时间段外开卡的情况
		//所以需要把开卡时的业绩按照开卡日期，还有补退款时的业绩按照调整日期分别计算
		//如果开卡日期在开始日期和结束日期之间,则应把开卡时的业绩手动加入
		//如果没有日期刷选，则不做任何改动，目前的收款信息即为正确信息，因为每次调整销售卡都会自动更新原销售卡收款信息
		SaleCardAmountAdjustTotalDTO saleCardAmountAdjustTotalDTO = getSaleCardAmountAdjustTotalDetail(startDate, endDate, saleId);
	
		
			//此处开始做日期刷选
			// if(!(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))){
				if(createCardDateObj.after(startDateObj) && createCardDateObj.before(endDateObj) ){
					if(saleAdjustId == null){//计算单个销售卡某个时间段内的汇总业绩
						//两种情况，1.销售卡没有补退款，则首付款开卡实收全齐，补退款为0,
						// 2.销售卡有补退款，实收业绩=开卡业绩+退补款业绩
						//日期刷选后只会显示在日期区间之内的业绩(无论是开卡业绩还是补退款业绩)
						//此处为开卡日期处于刷选日期区间内，其业绩包括开卡业绩和日期区间内的补退款业绩
						receivedAmount = createCardReceivedAmount + saleCardAmountAdjustTotalDTO.getSaleCardAmountAdjustTotalAmounts();
						employeePremium = createCardEmployeePremium + saleCardAmountAdjustTotalDTO.getSaleCardAmountAdjustEmployeePremiumTotalAmounts();
						shopPremium = createCardShopPremium + saleCardAmountAdjustTotalDTO.getSaleCardAmountAdjustShopPremiumTotalAmounts();

					}else if(saleAdjustId == 0l){//计算单个客户的所有销售卡的汇总业绩时，某张带有退补款的销售卡开卡时的业绩
						//即会显示成开卡业绩，后面紧跟所有的退补款业绩
						receivedAmount = createCardReceivedAmount;
						employeePremium = createCardEmployeePremium;
						shopPremium = createCardShopPremium;
					}else{
						SaleCardAmountAdjustDTO saleCardAmountAdjustDTO = getSaleCardAmountAdjustDetail(startDate, endDate, saleAdjustId);
						createCardTotalAmount = saleCardAmountAdjustDTO.getCreateCardTotalAmount();
						receivedAmount = saleCardAmountAdjustDTO.getReceivedAmount();
						employeePremium = saleCardAmountAdjustDTO.getEmployeePremium();
						shopPremium = saleCardAmountAdjustDTO.getShopPremium();
						createCardDate = saleCardAmountAdjustDTO.getAdjustDate();
						description = saleCardAmountAdjustDTO.getDescription();

					}

				}else{//计算销售卡补退款时产生的业绩
					SaleCardAmountAdjustDTO saleCardAmountAdjustDTO = getSaleCardAmountAdjustDetail(startDate, endDate, saleAdjustId);
					createCardTotalAmount = saleCardAmountAdjustDTO.getCreateCardTotalAmount();
					receivedAmount = saleCardAmountAdjustDTO.getReceivedAmount();
					employeePremium = saleCardAmountAdjustDTO.getEmployeePremium();
					shopPremium = saleCardAmountAdjustDTO.getShopPremium();
					createCardDate = saleCardAmountAdjustDTO.getAdjustDate();
					description = saleCardAmountAdjustDTO.getDescription();
				}

	
			// } 
		
		
		currentEarnedAmount = new Double(receivedAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;//目前收到客户款应回款
		//  String saleId = salePerformanceDetailForDBDTO.getSaleId();
		 String beautifySkinItemName = salePerformanceDetailForDBDTO.getBeautifySkinItemName();
		 int itemNumber = salePerformanceDetailForDBDTO.getItemNumber();
		 long debtAmount = createCardTotalAmount - receivedAmount;
		 long earnedAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;
		 long debtEarnedAmount = earnedAmount - currentEarnedAmount;
		 
		 salePerformanceDetail.setSaleId(saleId);
		 salePerformanceDetail.setCreateCardDate(createCardDate);
		 salePerformanceDetail.setBeautifySkinItemName(beautifySkinItemName);
		 salePerformanceDetail.setCreateCardTotalAmount(createCardTotalAmount);
		 salePerformanceDetail.setReceivedAmount(receivedAmount);
		 salePerformanceDetail.setDebtAmount(debtAmount);
		 salePerformanceDetail.setEarnedAmount(earnedAmount);//开卡应收回款，即根据开卡金额及相关规则应该回的款
		 salePerformanceDetail.setCurrentEarnedAmount(currentEarnedAmount);//实需回款，根据实际收到的客户的款计算的回款，并不是实际回给悦和的款，是需要回给悦和的款
		 salePerformanceDetail.setDebtEarnedAmount(debtEarnedAmount);
		 salePerformanceDetail.setItemNumber(itemNumber);
		 salePerformanceDetail.setCosmeticShopDiscount(cosmeticShopDiscount);
		 salePerformanceDetail.setEmployeePremium(employeePremium);
		 salePerformanceDetail.setShopPremium(shopPremium);
		 salePerformanceDetail.setDescription(description);
		 return salePerformanceDetail;
	 }

	 private SaleCardAmountAdjustTotalDTO getSaleCardAmountAdjustTotalDetail(String startDate, String endDate, String saleId){
		List<SaleCardAmountAdjust> saleCardAmountAdjustList = new ArrayList<SaleCardAmountAdjust>();
		SaleCardAmountAdjustTotalDTO saleCardAmountAdjustTotalDTO = new SaleCardAmountAdjustTotalDTO();
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){//用来计算开卡时的业绩，开始日期和结束日期必须为空

			saleCardAmountAdjustList =  saleCardAmountAdjustService.getBySaleId(saleId);
		}else{
			saleCardAmountAdjustList = saleCardAmountAdjustService.getBySaleIdAndAdjustDate(saleId, startDate, endDate);
		}
		Long saleCardAmountAdjustTotalAmounts = 0l;
		Long saleCardAmountAdjustEmployeePremiumTotalAmounts = 0l;
		Long saleCardAmountAdjustShopPremiumTotalAmounts = 0l;
		//统计某个销售卡所有的退补款总额以计算其开卡时的业绩情况
		for(SaleCardAmountAdjust saleCardAmountAdjustForSaleCreatePerfCalc : saleCardAmountAdjustList){
			String adjustAction = saleCardAmountAdjustForSaleCreatePerfCalc.getAdjustAction();
			if(StringUtils.equals(adjustAction, "add")){

				saleCardAmountAdjustTotalAmounts += saleCardAmountAdjustForSaleCreatePerfCalc.getAdjustAmount();
				saleCardAmountAdjustEmployeePremiumTotalAmounts += saleCardAmountAdjustForSaleCreatePerfCalc.getEmployeePremiumAdjustAmount();
				saleCardAmountAdjustShopPremiumTotalAmounts += saleCardAmountAdjustForSaleCreatePerfCalc.getShopPremiumAdjustAmount();
			}else{
				saleCardAmountAdjustTotalAmounts -= saleCardAmountAdjustForSaleCreatePerfCalc.getAdjustAmount();
				saleCardAmountAdjustEmployeePremiumTotalAmounts -= saleCardAmountAdjustForSaleCreatePerfCalc.getEmployeePremiumAdjustAmount();
				saleCardAmountAdjustShopPremiumTotalAmounts -= saleCardAmountAdjustForSaleCreatePerfCalc.getShopPremiumAdjustAmount();
				
			}
		}
		saleCardAmountAdjustTotalDTO.setSaleCardAmountAdjustEmployeePremiumTotalAmounts(saleCardAmountAdjustEmployeePremiumTotalAmounts);
		saleCardAmountAdjustTotalDTO.setSaleCardAmountAdjustShopPremiumTotalAmounts(saleCardAmountAdjustShopPremiumTotalAmounts);
		saleCardAmountAdjustTotalDTO.setSaleCardAmountAdjustTotalAmounts(saleCardAmountAdjustTotalAmounts);
		return saleCardAmountAdjustTotalDTO;
	 }
	 private SaleCardAmountAdjustDTO getSaleCardAmountAdjustDetail(String startDate, String endDate, long saleAdjustId){
		SaleCardAmountAdjustDTO saleCardAmountAdjustDTO = new SaleCardAmountAdjustDTO();
		SaleCardAmountAdjust saleCardAmountAdjust = saleCardAmountAdjustService.getById(saleAdjustId);
		long createCardTotalAmount = 0l;
		long receivedAmount = 0l;
		long shopPremium = 0l;
		long employeePremium = 0l;
		String description = "";
		String adjustDate = "";
		//如果是退补款的销售卡则获取相应的数额计算其实收业绩和应回款，不计算开卡总额
		if(saleCardAmountAdjust != null){//销售卡有退补款时，计算相应的实收款和实需回款以及相应的员工奖励和店家回扣
			createCardTotalAmount = 0l;
			receivedAmount = saleCardAmountAdjust.getAdjustAmount();//如果是退补款的销售卡则获取相应的数额计算其实收业绩和应回款，不计算开卡总额
			employeePremium = saleCardAmountAdjust.getEmployeePremiumAdjustAmount();
			shopPremium = saleCardAmountAdjust.getShopPremiumAdjustAmount();
			adjustDate = saleCardAmountAdjust.getAdjustDate();//更改日期
			description = saleCardAmountAdjust.getDescription();//更改开卡时的备注为补退款时的备注
			//如果是退款，则需要减掉相应的业绩
			if(StringUtils.equals(saleCardAmountAdjust.getAdjustAction(),"return")){
				receivedAmount = -receivedAmount;
				employeePremium = -employeePremium;
				shopPremium = -shopPremium;
			}
		}
		saleCardAmountAdjustDTO.setCreateCardTotalAmount(createCardTotalAmount);
		saleCardAmountAdjustDTO.setReceivedAmount(receivedAmount);
		saleCardAmountAdjustDTO.setEmployeePremium(employeePremium);
		saleCardAmountAdjustDTO.setShopPremium(shopPremium);
		saleCardAmountAdjustDTO.setAdjustDate(adjustDate);
		saleCardAmountAdjustDTO.setDescription(description);
		return saleCardAmountAdjustDTO;
	 }
}
