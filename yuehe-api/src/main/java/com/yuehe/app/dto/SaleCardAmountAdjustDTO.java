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
public class SaleCardAmountAdjustDTO {
	private long createCardTotalAmount;//单个顾客所有销售卡的总实收金额
	private long receivedAmount;//单个顾客所有销售卡的总应回款金额
	private long employeePremium;//员工奖励
	private long shopPremium;//店家回扣
	private String adjustDate;//更改日期
	private String description;//备注

	
}