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
public class ClientDetailDTO {
	private String clientName;//客户名
	private long allSalesCreateCardAmount;//单个顾客所有销售卡的总开卡金额
	private long allSalesEarnedAmount;//单个顾客所有销售卡的总应回款金额
	private long allSalesReceivedEarnedAmount;//单个顾客所有销售卡的总实际回款金额
	private long allSalesConsumedAmount;//单个顾客所有销售卡的总消耗款
	private long allSalesConsumedEarnedAmount;//单个顾客所有销售卡的总消耗回款
	private long allSalesAdvancedEarnedAmount;//单个顾客所有销售卡的总预付款
	private List<SaleDetailDTO> saleDetailDTOs;//单个顾客下面所有的销售卡信息

	
}