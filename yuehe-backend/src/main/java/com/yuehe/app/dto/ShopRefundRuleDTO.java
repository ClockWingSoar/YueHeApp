package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopRefundRuleDTO {
	private float cosmeticShopDiscount;//不同时期不同店家销售卡的回款折扣
	private float shopPremiumDiscount;//店家回扣-如，店家总业绩达到某个数字需要给店家回扣
	private long awardEmployeeAmount;//员工奖励-卖一张卡给员工的奖励
	private long tryoutEarnedAmount;//体验卡回款-卖一张卡回给悦和的款

	
}