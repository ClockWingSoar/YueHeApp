package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of operation.html,using when it
 * needs to join other tables to get employee name by it's employee id, it's
 * different with the entity class {@link com.yuehe.app.entity.Operation} which
 * is aim to symplify the data store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationOperatorToolDTO {
	private String operationId;//操作ID
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String operationDate;//操作日期
	private String clientName;//客户名
	private String cosmeticShopName;//美容院名
	private Float discount;//美容院的折扣点
	private Float employeePremium;//员工奖励
	private Float shopPremium;//店家回扣
	private String beautifySkinItemName;//美肤项目
	private Long createCardTotalAmount;//开卡金额
	private Long earnedAmount;//回款金额
	private Integer itemNumber;//开卡次数-疗程总次数
	private Integer restItemNumber;//剩余次数
	private Long consumedAmount;//已消耗总数
	private Long consumedEarnedAmount;//已消耗回款
	private Long advancedEarnedAmount;//预付款总数
	private String operatorName;//操作人
	private String toolName;//操作仪器
	private Integer operateExpense;//操作费用
	private String description;//备注
	
}