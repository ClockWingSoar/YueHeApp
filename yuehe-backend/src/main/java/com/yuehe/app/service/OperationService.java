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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.yuehe.app.common.OperationColumnsEnum;
import com.yuehe.app.common.PaginationAndSortModel;
import com.yuehe.app.common.YueHeEntitiesEnum;
import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.ClientShopDiscountDTO;
import com.yuehe.app.dto.OperationDetailDTO;
import com.yuehe.app.dto.OperationOperatorToolDTO;
import com.yuehe.app.dto.OperationOperatorToolForDBDTO;
import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleDetailDTO;
import com.yuehe.app.dto.SaleDetailForDBDTO;
import com.yuehe.app.dto.ShopDetailDTO;
import com.yuehe.app.dto.ShopRefundRuleDTO;
import com.yuehe.app.dto.YueHeAllShopsDetailDTO;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.property.BaseProperty;
import com.yuehe.app.repository.OperationRepository;
import com.yuehe.app.specification.SpecificationsBuilder;
import com.yuehe.app.util.ServiceUtil;
import com.yuehe.app.util.YueHeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
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
	Sort sort = null;
	boolean sortByJPA = true;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	Comparator<OperationDetailDTO> comparator = null;
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

	public Page<Operation> getOperationsDetailListWithPaginationAndSort(PaginationAndSortModel paginationAndSortModel) {
		Pageable pageable = buildPaginationAndSort(paginationAndSortModel);

		// Page<OperationDetailDTO> operationDetailDTOPage = operationRepository.findAllOperationList(pageable);
		// Map<String, Object> operationMap = buildOperationsDetailList(operationDetailDTOPage, paginationAndSortModel.getSortDirection());
		Page<Operation> operationPage = operationRepository.findAll(pageable);
		// Map<String, Object> operationMap = buildOperationsDetailList(operationPage, paginationAndSortModel.getSortDirection());

		return operationPage;
	}

	public Pageable buildPaginationAndSort(PaginationAndSortModel paginationAndSortModel) {
		buildSort(paginationAndSortModel.getSortDirection(), paginationAndSortModel.getSortProperty());
		return ServiceUtil.buildPageableObj(sortByJPA, sort, paginationAndSortModel);
	}
	/**
	 * to get all the operations detail with pagination and sort and filtering,  can't use @query to do the dynamic query
	 * can't use a DTO class to store the dynamic querired result, so use findAll method
	 */
	public Page<Operation> getOperationsDetailListWithPaginationAndSortAndFiltering(PaginationAndSortModel paginationAndSortModel, String searchParameters) {
		Pageable pageable = buildPaginationAndSort(paginationAndSortModel);
		SpecificationsBuilder<Operation> specificationsBuilder = new SpecificationsBuilder<>();
		Specification<Operation> spec = specificationsBuilder.resolveSpecification(searchParameters,YueHeEntitiesEnum.OPERATION);
		LOGGER.info("spec {}",spec);
		// Page<OperationDetailsDTO> operationClientItemSellerForDBDTOPage = operationRepository.fetchOperationClientItemSellerDataWithFiltering(spec,pageable);
		Page<Operation> operationPage = operationRepository.findAll(spec,pageable);

		// Map<String,Object> operationMap = buildFilteredOperationsDetailList(operationPage,sortDirection);
		
		return operationPage;
	}
	
	/**
	 * Get the operations detail list for downloading as csv, excel or pdf
	 * @param sortDirection
	 * @param sortProperty
	 * @return
	 */
	public Map<String, Object>  getOperationsDetailListForDownload(Direction sortDirection,String sortProperty){
		sort = Sort.by(sortDirection, sortProperty);
		List<OperationOperatorToolDTO> operationOperatorToolDTOList = new ArrayList<OperationOperatorToolDTO>();
		List<OperationOperatorToolForDBDTO> operationOperatorToolForDBDTOList = new ArrayList<OperationOperatorToolForDBDTO>();
		
		List<Sort.Order> sortOrders = null;
		// Comparator<OperationClientItemSellerDTO> comparator = null;
	
		Map<String, Object> operationMap = new HashMap<String, Object>();
		buildSort(sortDirection, sortProperty);
		if(sortByJPA){

			sortOrders = sort.get().collect(Collectors.toList());
			operationOperatorToolForDBDTOList = operationRepository.fetchOperationOperatorToolDataForDownloadWithSort(sort);
		}else{
			operationOperatorToolForDBDTOList = operationRepository.fetchOperationOperatorToolDataForDownload();

		}
		
		buildOperationsDetailList(operationOperatorToolDTOList, operationOperatorToolForDBDTOList);
		// sortByCollectionsIfPropertyNotInDB(sortDirection,operationClientItemSellerDTOList);
		operationMap.put("csvObjList",operationOperatorToolDTOList);
		operationMap.put("sortOrders", sortOrders);
		return operationMap;

	}
	

	private void buildSort(Direction sortDirection, String sortProperty) {

		// Below code is using the table field to do the sorting, when it comes to the
		// foreign table, you need to use the foreign
		// relationship to refer to it, like through entity "sale" to "client" to
		// "cosmeticShop", you got "client.cosmeticshop.name"
		// but you can't have any "." inside a enum class, so you have to remove all the
		// ".", then do the comparing
		OperationColumnsEnum operationSortBy = OperationColumnsEnum
				.valueOf(StringUtils.remove(sortProperty.toUpperCase(), '.'));
		switch (operationSortBy) {
		case ID:
		case SALEID:
		case OPERATIONDATE:
		case DESCRIPTION:
			sort = Sort.by(new Order(sortDirection, sortProperty));
			sortByJPA = true;
			break;
		case EMPLOYEENAME:
			sort = Sort.by(new Order(sortDirection, "employee.name"));
			sortByJPA = true;
			break;
		case TOOLNAME:
			sort = Sort.by(new Order(sortDirection, "tool.name"));
			sortByJPA = true;
			break;
		case OPERATEEXPENSE:
			sort = Sort.by(new Order(sortDirection, "tool.operateExpense"));
			sortByJPA = true;
			break;
		default:
			break;

		}
	}

	/**
	 * To build the operation list page for csv download
	 */
	private void buildOperationsDetailList(List<OperationOperatorToolDTO> operationOperatorToolDTOList,
											List<OperationOperatorToolForDBDTO> operationOperatorToolForDBDTOList){
	
		for(OperationOperatorToolForDBDTO operationOperatorToolForDBDTO : operationOperatorToolForDBDTOList) {
			String operationId = operationOperatorToolForDBDTO.getOperationId();//操作ID
			String saleId = operationOperatorToolForDBDTO.getSaleId();//销售卡ID
			String createCardDate = operationOperatorToolForDBDTO.getCreateCardDate();//开卡日期
			String operationDate = operationOperatorToolForDBDTO.getOperationDate();//操作日期
			String clientName = operationOperatorToolForDBDTO.getClientName();//客户名
			String cosmeticShopName = operationOperatorToolForDBDTO.getCosmeticShopName();//美容院名
			Float cosmeticShopDiscount = operationOperatorToolForDBDTO.getDiscount();//美容院的折扣点
			Long employeePremium = operationOperatorToolForDBDTO.getEmployeePremium().longValue();//员工奖励
			Long shopPremium = operationOperatorToolForDBDTO.getShopPremium().longValue();//美容院回扣
			String beautifySkinItemName = operationOperatorToolForDBDTO.getBeautifySkinItemName();//美肤项目
			Long createCardTotalAmount = operationOperatorToolForDBDTO.getCreateCardTotalAmount();//开卡金额
			Integer itemNumber = operationOperatorToolForDBDTO.getItemNumber();//开卡次数-疗程总次数
				// 回给公司的回款计算方法为：开卡金额 * 店家折扣点 - 给员工的奖励 - 给店家的回扣（柳叶需扣除业绩的1%）
			long earnedAmount = new Double(createCardTotalAmount * cosmeticShopDiscount).longValue() - employeePremium
			- shopPremium;
			int operationNumber = getOperationNumberBySaleId(saleId);// 操作次数
			float unitPrice = createCardTotalAmount / itemNumber;// 美肤卡单次价格
			int restItemNumber = itemNumber - operationNumber;// 剩余次数
			long consumedAmount = new Double(operationNumber * unitPrice).longValue();// 已消耗总数
			long consumedEarnedAmount = new Double(operationNumber * unitPrice * cosmeticShopDiscount).longValue();// 已消耗回款
			long advancedEarnedAmount = earnedAmount - consumedEarnedAmount;// 预付款只计算回款的部分，不包括美容院的
			String operatorName = operationOperatorToolForDBDTO.getOperatorName();//操作人
			String toolName = operationOperatorToolForDBDTO.getToolName();//操作仪器
			Integer operateExpense = operationOperatorToolForDBDTO.getOperateExpense();//操作费用
			String description = operationOperatorToolForDBDTO.getDescription();//备注
			
			OperationOperatorToolDTO operationOperatorToolDTO = new OperationOperatorToolDTO();
			operationOperatorToolDTO.setBeautifySkinItemName(beautifySkinItemName);
			operationOperatorToolDTO.setClientName(clientName);
			operationOperatorToolDTO.setCosmeticShopName(cosmeticShopName);
			operationOperatorToolDTO.setCreateCardDate(createCardDate);
			operationOperatorToolDTO.setCreateCardTotalAmount(createCardTotalAmount);
			operationOperatorToolDTO.setDescription(description);
			operationOperatorToolDTO.setDiscount((float)(Math.round(cosmeticShopDiscount*100.0)/100.0));
			operationOperatorToolDTO.setEarnedAmount(earnedAmount);
			operationOperatorToolDTO.setItemNumber(itemNumber);
			operationOperatorToolDTO.setOperationId(operationId);
			operationOperatorToolDTO.setSaleId(saleId);
			operationOperatorToolDTO.setOperationDate(operationDate);
			operationOperatorToolDTO.setOperatorName(operatorName);
			operationOperatorToolDTO.setToolName(toolName);
			operationOperatorToolDTO.setOperateExpense(operateExpense);
		    operationOperatorToolDTO.setRestItemNumber(restItemNumber);
			operationOperatorToolDTO.setConsumedAmount(consumedAmount);
			operationOperatorToolDTO.setConsumedEarnedAmount(consumedEarnedAmount);
			operationOperatorToolDTO.setAdvancedEarnedAmount(advancedEarnedAmount);
			
			operationOperatorToolDTOList.add(operationOperatorToolDTO);
		}
		// operationList.forEach(l -> System.out.println(l));
		// operationDetailDTOList.forEach(l -> System.out.println(l));
	}

	public Operation getById(String id) {
		return operationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid operation Id:" + id));
	}

	public int getOperationNumberBySaleId(String saleId) {
		return operationRepository.findOperationNumBySaleId(saleId);
	}

	/**
	 * To get all the operation details for yuehe all shops-all clients-all sales
	 * 
	 * @return yueHeAllShopsDetailDTO
	 */
	public YueHeAllShopsDetailDTO getYueHeAllShopsDetail(String startDate, String endDate) {
		YueHeAllShopsDetailDTO yueHeAllShopsDetailDTO = new YueHeAllShopsDetailDTO();
		List<ShopDetailDTO> shopDetailDTOList = new ArrayList<ShopDetailDTO>();
		List<CosmeticShop> cosmeticShopList = cosmeticShopService.getAllCosmeticShopForFiltering();
		// it only has one instance of YueHeAllShopsDetailDTO, but to use the common
		// method convertOperationDTO()
		// I have to put it in a list
		long allShopsConsumedAmount = 0;// for all shop's all client's all sales
		long allShopsAdvancedEarnedAmount = 0;// for all shop's all client's all sales
		long allShopsReceivedAmount = 0;// for all shop's all client's all sales
		long allShopsEarnedAmount = 0;// for all shop's all client's all sales
		long allShopsReceivedEarnedAmount = 0;// for all shop's all client's all sales
		long allShopsConsumedEarnedAmount = 0;// for all shop's all client's all sales
		for (CosmeticShop cosmeticShop : cosmeticShopList) {
			ShopDetailDTO shopDetailDTO = getShopClientDetailByShopId(cosmeticShop.getId(), startDate, endDate);
			shopDetailDTOList.add(shopDetailDTO);
			long allClientsConsumedAmount = shopDetailDTO.getAllClientsConsumedAmount();
			long allClientsAdvancedEarnedAmount = shopDetailDTO.getAllClientsAdvancedEarnedAmount();
			long allClientsReceivedAmount = shopDetailDTO.getAllClientsReceivedAmount();
			long allClientsEarnedAmount = shopDetailDTO.getAllClientsEarnedAmount();
			long allClientsReceivedEarnedAmount = shopDetailDTO.getAllClientsReceivedEarnedAmount();
			long allClientsConsumedEarnedAmount = shopDetailDTO.getAllClientsConsumedEarnedAmount();
			allShopsConsumedAmount += allClientsConsumedAmount;
			allShopsAdvancedEarnedAmount += allClientsAdvancedEarnedAmount;
			allShopsReceivedAmount += allClientsReceivedAmount;
			allShopsEarnedAmount += allClientsEarnedAmount;
			allShopsReceivedEarnedAmount += allClientsReceivedEarnedAmount;
			allShopsConsumedEarnedAmount += allClientsConsumedEarnedAmount;
		}
		yueHeAllShopsDetailDTO.setYueheCompanyName(BaseProperty.COMPANY_NAME);
		yueHeAllShopsDetailDTO.setShopDetailDTOs(shopDetailDTOList);
		yueHeAllShopsDetailDTO.setAllShopsAdvancedEarnedAmount(allShopsAdvancedEarnedAmount);
		yueHeAllShopsDetailDTO.setAllShopsConsumedAmount(allShopsConsumedAmount);
		yueHeAllShopsDetailDTO.setAllShopsConsumedEarnedAmount(allShopsConsumedEarnedAmount);
		yueHeAllShopsDetailDTO.setAllShopsReceivedAmount(allShopsReceivedAmount);
		yueHeAllShopsDetailDTO.setAllShopsEarnedAmount(allShopsEarnedAmount);
		yueHeAllShopsDetailDTO.setAllShopsReceivedEarnedAmount(allShopsReceivedEarnedAmount);
		return yueHeAllShopsDetailDTO;
	}

	public ShopDetailDTO getShopClientDetailByShopId(String shopId, String startDate, String endDate) {
		List<ClientShopDiscountDTO> clientListByShopId = clientService.getClientsByShopId(shopId);
		List<ClientDetailDTO> clientDetailDTOList = new ArrayList<ClientDetailDTO>();

		String shopName = cosmeticShopService.getById(shopId).getName();
		ShopDetailDTO shopDetailDTO = new ShopDetailDTO();
		long allClientsConsumedAmount = 0;// for one shop all client's all sales
		long allClientsAdvancedEarnedAmount = 0;// for one shop all client's all sales
		long allClientsReceivedAmount = 0;// for one shop all client's all sales
		long allClientsEarnedAmount = 0;// for one shop all client's all sales
		long allClientsReceivedEarnedAmount = 0;// for one shop all client's all sales
		long allClientsConsumedEarnedAmount = 0;// for one shop all client's all sales
		for (ClientShopDiscountDTO client : clientListByShopId) {
			ClientDetailDTO clientDetailDTO = getClientSaleDetailByClientId(client.getId(), startDate, endDate);
			clientDetailDTOList.add(clientDetailDTO);
			long allSalesConsumedAmount = clientDetailDTO.getAllSalesConsumedAmount();
			long allSalesAdvancedEarnedAmount = clientDetailDTO.getAllSalesAdvancedEarnedAmount();
			long allSalesReceivedAmount = clientDetailDTO.getAllSalesReceivedAmount();
			long allSalesEarnedAmount = clientDetailDTO.getAllSalesEarnedAmount();
			long allSalesReceivedEarnedAmount = clientDetailDTO.getAllSalesReceivedEarnedAmount();
			long allSalesConsumedEarnedAmount = clientDetailDTO.getAllSalesConsumedEarnedAmount();
			allClientsConsumedAmount += allSalesConsumedAmount;
			allClientsAdvancedEarnedAmount += allSalesAdvancedEarnedAmount;
			allClientsReceivedAmount += allSalesReceivedAmount;
			allClientsEarnedAmount += allSalesEarnedAmount;
			allClientsReceivedEarnedAmount += allSalesReceivedEarnedAmount;
			allClientsConsumedEarnedAmount += allSalesConsumedEarnedAmount;
		}
		shopDetailDTO.setClientDetailDTOs(clientDetailDTOList);
		shopDetailDTO.setCosmeticShopName(shopName);
		shopDetailDTO.setAllClientsAdvancedEarnedAmount(allClientsAdvancedEarnedAmount);
		shopDetailDTO.setAllClientsConsumedAmount(allClientsConsumedAmount);
		shopDetailDTO.setAllClientsConsumedEarnedAmount(allClientsConsumedEarnedAmount);
		shopDetailDTO.setAllClientsReceivedAmount(allClientsReceivedAmount);
		shopDetailDTO.setAllClientsEarnedAmount(allClientsEarnedAmount);
		shopDetailDTO.setAllClientsReceivedEarnedAmount(allClientsReceivedEarnedAmount);
		return shopDetailDTO;
	}

	public ClientDetailDTO getClientSaleDetailByClientId(String clientId, String startDate, String endDate) {
		List<SaleDetailDTO> saleDetailDTOList = getAllSalesDetailByClientId(clientId, startDate, endDate);
		
		String clientName = clientService.getById(clientId).getName();
		ClientDetailDTO clientDetailDTO = new ClientDetailDTO();
		long allSalesConsumedAmount = 0;// for one client's all sales
		long allSalesAdvancedEarnedAmount = 0;// for one client's all sales
		long allSalesReceivedAmount = 0;// for one client's all sales
		long allSalesEarnedAmount = 0;// for one client's all sales
		long allSalesReceivedEarnedAmount = 0;// for one client's all sales
		long allSalesConsumedEarnedAmount = 0;// for one client's all sales
		for (SaleDetailDTO saleDetailDTO : saleDetailDTOList) {
			long consumedAmount = saleDetailDTO.getConsumedAmount();
			long advancedEarnedAmount = saleDetailDTO.getAdvancedEarnedAmount();
			long receivedAmount = saleDetailDTO.getReceivedAmount();
			long earnedAmount = saleDetailDTO.getEarnedAmount();
			long receivedEarnedAmount = saleDetailDTO.getReceivedEarnedAmount();
			long consumedEarnedAmount = saleDetailDTO.getConsumedEarnedAmount();
			allSalesConsumedAmount += consumedAmount;
			allSalesAdvancedEarnedAmount += advancedEarnedAmount;
			allSalesReceivedAmount += receivedAmount;
			allSalesEarnedAmount += earnedAmount;
			allSalesReceivedEarnedAmount += receivedEarnedAmount;
			allSalesConsumedEarnedAmount += consumedEarnedAmount;
		}
		clientDetailDTO.setSaleDetailDTOs(saleDetailDTOList);
		clientDetailDTO.setClientName(clientName);
		clientDetailDTO.setAllSalesAdvancedEarnedAmount(allSalesAdvancedEarnedAmount);
		clientDetailDTO.setAllSalesConsumedAmount(allSalesConsumedAmount);
		clientDetailDTO.setAllSalesConsumedEarnedAmount(allSalesConsumedEarnedAmount);
		clientDetailDTO.setAllSalesReceivedAmount(allSalesReceivedAmount);
		clientDetailDTO.setAllSalesEarnedAmount(allSalesEarnedAmount);
		clientDetailDTO.setAllSalesReceivedEarnedAmount(allSalesReceivedEarnedAmount);
		return clientDetailDTO;
	}

	public SaleDetailDTO getSaleOperationDetailBySaleId(String saleId, String startDate, String endDate) {
		List<OperationDetailDTO> operationDetailDTOList = getOperationsBySaleId(saleId, startDate, endDate);
		SaleDetailDTO saleDetailDTO = getSaleBasicDetailById(saleId,startDate, endDate);
		saleDetailDTO.setOperationDetailDTOs(operationDetailDTOList);
		return saleDetailDTO;
	}

	public List<SaleDetailDTO> getAllSalesDetailByClientId(String clientId, String startDate, String endDate) {
		List<SaleDetailDTO> saleDetailDTOList = new ArrayList<SaleDetailDTO>();
		List<SaleBeautifySkinItemForFilterDTO> saleListByClient = saleService.getByClientId(clientId);
		Collections.sort(saleListByClient,SaleBeautifySkinItemForFilterDTO.saleIdComparator);
		for (SaleBeautifySkinItemForFilterDTO saleBeautifySkinItemForFilterDTO : saleListByClient) {
			String saleId = saleBeautifySkinItemForFilterDTO.getSaleId();
			SaleDetailDTO saleDetailDTO = getSaleBasicDetailById(saleId, startDate, endDate);
			saleDetailDTOList.add(saleDetailDTO);

		}
		return saleDetailDTOList;

	}

	private SaleDetailDTO getSaleBasicDetailById(String id, String startDate, String endDate) {
		SaleDetailForDBDTO saleDetailForDBDTO = saleService.getSaleBasicDetailById(id);
		
		SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
		String saleId = saleDetailForDBDTO.getSaleId();
		float cosmeticShopDiscount = saleDetailForDBDTO.getCosmeticShopDiscount();
		long createCardAmount = saleDetailForDBDTO.getCreateCardAmount();
		long receivedAmount = saleDetailForDBDTO.getReceivedAmount();
		long employeePremium = new Float(saleDetailForDBDTO.getEmployeePremium()).longValue();// 员工奖励
		long shopPremium = new Float(saleDetailForDBDTO.getShopPremium()).longValue();// 美容院回扣
		int itemNumber = saleDetailForDBDTO.getItemNumber();
		String createCardDate = saleDetailForDBDTO.getCreateCardDate();
		Date createCardDateObj = null;
		
		try {
			createCardDateObj = sdf.parse(createCardDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//获取该销售卡所属客户所属美容院的回款规则
		long tryoutEarnedAmount = 0l;//体验卡回款有时候不按美容院回扣折扣点计算，比如美之然680回款280
		ShopRefundRuleDTO shopRefundRuleDTO = saleService.getShopRefundRuleDetail(saleId, createCardDateObj);
		//如果有折扣更改，则用更改的折扣点，否则继续原有的折扣
		float cosmeticShopDiscountChangedValue = shopRefundRuleDTO.getCosmeticShopDiscount();
		if(cosmeticShopDiscountChangedValue > 0f)
			cosmeticShopDiscount = shopRefundRuleDTO.getCosmeticShopDiscount();
		tryoutEarnedAmount = shopRefundRuleDTO.getTryoutEarnedAmount();
		
		// 应回给公司的回款计算方法为：实收金额 * 店家折扣点 - 给员工的奖励 - 给店家的回扣（柳叶需扣除业绩的1%）
		long earnedAmount = new Double(createCardAmount * cosmeticShopDiscount).longValue() - employeePremium
				- shopPremium;
		long receivedEarnedAmount = saleDetailForDBDTO.getReceivedEarnedAmount();//实际回给公司的款
		int operationNumber = 0;
		if(tryoutEarnedAmount > 0l){//一般来说，体验卡都会收齐费用，所以开卡需汇款和实际需回款相等，也有送体验卡的行为，此时建卡的时候不会用该体验卡，而是用赠送项目，价值为0 
			earnedAmount = tryoutEarnedAmount;
			if(receivedAmount > 0l)
				receivedEarnedAmount = tryoutEarnedAmount;
		 }
		 //如果某个美容院有手动计算回款的规则，则忽略现有的系统计算回款规则，取用建卡时存入数据库的回款数额
		boolean isManualFilledRefund = shopRefundRuleDTO.isManualFilledRefund();
		if(isManualFilledRefund)
			earnedAmount = receivedEarnedAmount;
		else
			receivedEarnedAmount = new Double(receivedAmount*cosmeticShopDiscount).longValue() - employeePremium - shopPremium;//目前收到客户款应回款

		operationNumber = operationRepository.findOperationNumBySaleIdAndOperationDate(saleId, startDate, endDate);//用于计算一定时间内的消耗
		float unitPrice = receivedAmount / itemNumber;// 美肤卡单次价格-按目前单张卡已收到的费用计算
		int restItemNumber = itemNumber - getOperationNumberBySaleId(saleId);// 剩余次数
		long consumedAmount = new Double(operationNumber * unitPrice).longValue();// 已消耗总数
		long consumedEarnedAmount = new Double(consumedAmount * cosmeticShopDiscount).longValue();// 已消耗回款
		long advancedEarnedAmount = receivedEarnedAmount - consumedEarnedAmount;// 预付款只计算回款的部分，不包括美容院的

		saleDetailDTO.setSaleId(saleId);
		saleDetailDTO.setCreateCardDate(createCardDate);
		saleDetailDTO.setBeautifySkinItemName(saleDetailForDBDTO.getBeautifySkinItemName());
		saleDetailDTO.setSellerName(saleDetailForDBDTO.getSellerName());
		saleDetailDTO.setReceivedAmount(receivedAmount);
		saleDetailDTO.setEarnedAmount(earnedAmount);
		saleDetailDTO.setReceivedEarnedAmount(receivedEarnedAmount);
		saleDetailDTO.setItemNumber(itemNumber);
		saleDetailDTO.setDescription(saleDetailForDBDTO.getDescription());
		saleDetailDTO.setRestItemNumber(restItemNumber);
		saleDetailDTO.setConsumedAmount(consumedAmount);
		saleDetailDTO.setConsumedEarnedAmount(consumedEarnedAmount);
		saleDetailDTO.setAdvancedEarnedAmount(advancedEarnedAmount);

		return saleDetailDTO;
	}

	private List<OperationDetailDTO> getOperationsBySaleId(String saleId, String startDate, String endDate) {
		List<OperationDetailDTO> operationDetailDTOList = new ArrayList<OperationDetailDTO>();
		// if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){

		// 	operationDetailDTOList =  operationRepository.findBySaleId(saleId);
		// }else{
			operationDetailDTOList = operationRepository.findBySaleIdAndOperationDate(saleId, startDate, endDate);
		// }
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
	public void deleteById(String id) {
		operationRepository.deleteById(id);
   }
	/**
	 * To get the biggest number of the current string id
	 */
	public int getBiggestIdNumber() {
		List<Operation> operationList = operationRepository.findAllIds();
		Collections.sort(operationList, Operation.idComparator.reversed());
		String biggestId = operationList.get(0).getId();
		int biggestIdNum = YueHeUtil.extractIdDigitalNumber(biggestId);
		LOGGER.info("biggest Id Number-{}", biggestIdNum);
		return biggestIdNum;
	}
}
