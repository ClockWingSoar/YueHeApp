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
public class SaleDetailDTO{
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String beautifySkinItemName;//美肤项目
	private long createCardTotalAmount;//开卡金额
	private long earnedAmount;//回款金额
	private int itemNumber;//开卡次数-疗程总次数
	private int restItemNumber;//剩余次数
	private long consumedAmount;//已消耗总数
	private long consumedEarnedAmount;//已消耗回款
	private long advancedEarnedAmount;//预付款总数
	private String description;//备注
	private List<OperationDetailDTO> operationDetailDTOs;//单张美肤卡下面所有的操作的信息
	@Override
	public String toString() {
		return "SaleDetailDTO [saleId=" + saleId + ", createCardDate=" + createCardDate + ", beautifySkinItemName="
				+ beautifySkinItemName + ", createCardTotalAmount=" + createCardTotalAmount + ", earnedAmount="
				+ earnedAmount + ", itemNumber=" + itemNumber + ", description=" + description + ", restItemNumber="
				+ restItemNumber + ", consumedAmount=" + consumedAmount + ", consumedEarnedAmount="
				+ consumedEarnedAmount + ", advancedEarnedAmount=" + advancedEarnedAmount + ", operationDetailDTOs="
				+ operationDetailDTOs + "]";
	}

	
}