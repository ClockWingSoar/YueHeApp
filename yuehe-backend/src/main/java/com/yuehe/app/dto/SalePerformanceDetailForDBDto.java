package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it
 * needs to join other tables to get seller name by it's employee id, it's
 * different with the entity class {@link com.yuehe.app.entity.Client} which is
 * aim to symplify the data store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalePerformanceDetailForDBDTO {
	private String saleId;
	private String createCardDate;
	private String beautifySkinItemName;
	private long createCardTotalAmount;
	private long receivedAmount;
	private long currentEarnedAmount;
	private int itemNumber;
	private float cosmeticShopDiscount;
	private float employeePremium;
	private float shopPremium;
	private String description;
	
}
