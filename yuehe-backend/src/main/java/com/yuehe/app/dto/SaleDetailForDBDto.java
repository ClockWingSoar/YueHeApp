package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to
 * show the basic operation table view class
 * {@link com.yuehe.app.entity.Operation} which is aim to symplify the data
 * store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleDetailForDBDTO{
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String beautifySkinItemName;//美肤项目
	private String sellerName;//售卡专家
	private long createCardAmount;//实收金额
	private long receivedAmount;//实收金额
	private long receivedEarnedAmount;//实际回款金额
	private int itemNumber;//开卡次数-疗程总次数
	private float cosmeticShopDiscount;//美容院给悦和的折扣点
	private float employeePremium;//单张销售卡给员工的奖励
	private float shopPremium;//单张销售卡给美容院的回扣
	private String description;//备注
	
	
	
}