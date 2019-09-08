package com.yuehe.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it needs to join other tables
 * to get seller name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Shop} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YueHeAllSalesPerformanceDetailDTO {
	private String companyName;
	private long allShopsSalesCreateCardTotalAmount;
	private long allShopsSalesReceivedAmount;
	private long allShopsSalesDebtAmount;
	private long allShopsSalesEarnedAmount;
	private long allShopsSalesCurrentEarnedAmount;
	private long allShopsSalesDebtEarnedAmount;
	private float allShopsSalesEmployeePremium;
	private float allShopsSalesShopPremium;
	private List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOs;
}
