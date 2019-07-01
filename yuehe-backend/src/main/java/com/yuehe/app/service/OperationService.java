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
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.OperationOperatorToolDto;
import com.yuehe.app.dto.OperationOperatorToolForDBDto;
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

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Operation create(Operation operation) {
        return operationRepository.save(operation);
    }
    public List<Operation> getAllOperation() {
        return operationRepository.findAll();
    }
    public Operation getOperationById(String id) {
    	return operationRepository.findById(id);
    }
    public int getOperationNumberBySaleId(String saleId) {
    	return operationRepository.findOperationNumBySaleId(saleId);
    }
    public HashMap<String,Object> getOperationsBySaleId(String saleId) {
    	List<OperationOperatorToolForDBDto> operationOperatorToolForDBDtoList = operationRepository.findBySaleId(saleId);
    	return convertOperationDto(operationOperatorToolForDBDtoList);
    }
//    public List<Operation> getOperationByName(String name) {
//    	return operationRepository.findByName(name);
//    }
    @Transactional(rollbackFor = Exception.class)
    public List<Operation> saveAll(List<Operation> operation) {
        LOGGER.info("Saving {}", operation);
        return operationRepository.saveAll(operation);
    }
	@SuppressWarnings("unchecked")
	public List<OperationOperatorToolDto> getOperationsDetailList() {
		List<OperationOperatorToolForDBDto> operationOperatorToolForDBDtoList = operationRepository.fetchOperationOpertatorToolData();
		HashMap<String,Object> operationHashMap = convertOperationDto(operationOperatorToolForDBDtoList);
		return (List<OperationOperatorToolDto>) operationHashMap.get("operationList");
	}
	 public long getEntityNumber() {
	    	return operationRepository.count();
	    }
	 public HashMap<String,Object> convertOperationDto(List<OperationOperatorToolForDBDto> operationOperatorToolForDBDtoList ){
		 
			List<OperationOperatorToolDto> operationOperatorToolDtoList = new ArrayList<OperationOperatorToolDto>();
			long totalConsumedAmount = 0;
			long totalAdvancedEarnedAmount = 0;
			HashMap<String,Object> operationMap = new HashMap<String,Object>();
			for(OperationOperatorToolForDBDto operationOperatorToolForDBDto : operationOperatorToolForDBDtoList) {
				//private String operationId;//操作ID
//				private String saleId;//销售卡ID
//				private String createCardDate;//开卡日期
//				private String operationDate;//操作日期
//				private String clientName;//客户名
//				private String cosmeticShopName;//美容院名===================
//				private String beautifySkinItemName;//美肤项目
//				private long createCardTotalAmount;//开卡金额
//				private long earnedTotalAmount;//回款金额------------------------------------------
//				private int totalItemNumber;//开卡次数-疗程总次数
//				private int restItemNumber;//剩余次数--------------------------------------------------
//				private long consumedTotalAmount;//已消耗总数----------------------------------------
//				private long consumedEarnedTotalAmount;//已消耗回款------------------------------
//				private long advancedEarnedTotalAmount;//预付款总数----------------------------------
//				private String operatorName;//操作人
//				private String toolName;//操作仪器=======================
//				private int operateExpense;//操作费用+++++++++++++++++++++
//				private String description;//备注
				String saleId = operationOperatorToolForDBDto.getSaleId();
				float cosmeticShopDiscount = operationOperatorToolForDBDto.getCosmeticShopDiscount();
				long createCardTotalAmount =  operationOperatorToolForDBDto.getCreateCardTotalAmount();//开卡总金额
				int itemNumber = operationOperatorToolForDBDto.getTotalItemNumber();//开卡次数
				int operationNumber = getOperationNumberBySaleId(saleId);//操作次数
				float unitPrice = createCardTotalAmount/itemNumber;//美肤卡单次价格
				int restItemNumber = itemNumber-operationNumber;//剩余次数
				long earnedTotalAmount = new Double(createCardTotalAmount*cosmeticShopDiscount).longValue();//回款金额
				long consumedTotalAmount = new Double(operationNumber*unitPrice).longValue();//已消耗总数
				long consumedEarnedTotalAmount = new Double(operationNumber*unitPrice*cosmeticShopDiscount).longValue();//已消耗回款
				long advancedEarnedTotalAmount = earnedTotalAmount-consumedEarnedTotalAmount;//预付款只计算回款的部分，不包括美容院的
				totalConsumedAmount = totalConsumedAmount + consumedTotalAmount;//计算整个列表的总消耗
				totalAdvancedEarnedAmount = totalAdvancedEarnedAmount + advancedEarnedTotalAmount;//计算整个列表的总预付款
				OperationOperatorToolDto operationOperatorToolDto = new OperationOperatorToolDto();
				
				operationOperatorToolDto.setOperationId(operationOperatorToolForDBDto.getOperationId());
				operationOperatorToolDto.setSaleId(operationOperatorToolForDBDto.getSaleId());
				operationOperatorToolDto.setCreateCardDate(operationOperatorToolForDBDto.getCreateCardDate());
				operationOperatorToolDto.setOperationDate(simpleDateFormat.format(operationOperatorToolForDBDto.getOperationDate()));
				operationOperatorToolDto.setClientName(operationOperatorToolForDBDto.getClientName());
				operationOperatorToolDto.setCosmeticShopName(operationOperatorToolForDBDto.getCosmeticShopName());
				operationOperatorToolDto.setBeautifySkinItemName(operationOperatorToolForDBDto.getBeautifySkinItemName());
				operationOperatorToolDto.setCreateCardTotalAmount(createCardTotalAmount);
				operationOperatorToolDto.setEarnedTotalAmount(earnedTotalAmount);
				operationOperatorToolDto.setTotalItemNumber(itemNumber);
				operationOperatorToolDto.setRestItemNumber(restItemNumber);
				operationOperatorToolDto.setEarnedTotalAmount(earnedTotalAmount);
				operationOperatorToolDto.setConsumedTotalAmount(consumedTotalAmount);
				operationOperatorToolDto.setConsumedEarnedTotalAmount(consumedEarnedTotalAmount);
				operationOperatorToolDto.setAdvancedEarnedTotalAmount(advancedEarnedTotalAmount);
				operationOperatorToolDto.setOperatorName(operationOperatorToolForDBDto.getOperatorName());
				operationOperatorToolDto.setToolName(operationOperatorToolForDBDto.getToolName());
				operationOperatorToolDto.setOperateExpense(operationOperatorToolForDBDto.getOperateExpense());
				operationOperatorToolDto.setDescription(operationOperatorToolForDBDto.getDescription());
				
				operationOperatorToolDtoList.add(operationOperatorToolDto);
			}
			operationOperatorToolForDBDtoList.forEach(l -> System.out.println(l));
			operationOperatorToolDtoList.forEach(l -> System.out.println(l));
			operationMap.put("operationList", operationOperatorToolDtoList);
			operationMap.put("totalConsumedAmount", totalConsumedAmount);
			operationMap.put("totalAdvancedEarnedAmount", totalAdvancedEarnedAmount);
			return operationMap;
	 }
}
