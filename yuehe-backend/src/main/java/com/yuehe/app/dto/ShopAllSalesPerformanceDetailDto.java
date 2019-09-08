package com.yuehe.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it needs to join other tables
 * to get seller name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Client} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopAllSalesPerformanceDetailDTO {
	private String cosmeticShopName;
	private long allClientsSalesCreateCardTotalAmount;
	private long allClientsSalesReceivedAmount;
	private long allClientsSalesDebtAmount;
	private long allClientsSalesEarnedAmount;
	private long allClientsSalesCurrentEarnedAmount;
	private long allClientsSalesDebtEarnedAmount;
	private float allClientsSalesEmployeePremium;
	private float allClientsSalesShopPremium;
	private List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOs;
	
	
}
