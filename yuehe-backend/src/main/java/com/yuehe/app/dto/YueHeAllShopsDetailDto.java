package com.yuehe.app.dto;

import java.util.List;

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
public class YueHeAllShopsDetailDTO {
	private String yueheCompanyName;//悦和国际的大名
	private long allShopsReceivedAmount;//悦和所有店的总开卡实收金额
	private long allShopsEarnedAmount;//悦和所有店的总应回款金额
	private long allShopsReceivedEarnedAmount;//悦和所有店的总实际回款金额
	private long allShopsConsumedAmount;//悦和所有店的总消耗款
	private long allShopsConsumedEarnedAmount;//悦和所有店的总消耗回款
	private long allShopsAdvancedEarnedAmount;//悦和所有店的总预付款
	private List<ShopDetailDTO> shopDetailDTOs;//悦和下面所有的店的信息
	
}