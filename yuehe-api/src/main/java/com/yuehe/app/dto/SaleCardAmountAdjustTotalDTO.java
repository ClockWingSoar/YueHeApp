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
public class SaleCardAmountAdjustTotalDTO {
	private long saleCardAmountAdjustTotalAmounts;//单个顾客所有销售卡的总实收金额
	private long saleCardAmountAdjustEmployeePremiumTotalAmounts;//单个顾客所有销售卡的总应回款金额
	private long saleCardAmountAdjustShopPremiumTotalAmounts;//单个顾客所有销售卡的总实际回款金额

	
}