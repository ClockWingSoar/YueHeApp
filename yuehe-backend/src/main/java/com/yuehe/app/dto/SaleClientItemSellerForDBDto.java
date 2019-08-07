package com.yuehe.app.dto;

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
public class SaleClientItemSellerForDBDTO {
	private String saleId;
	private String clientName;
	private String beautifySkinItemName;
	private String cosmeticShopName;
	private Integer itemNumber;
	private Long createCardTotalAmount;
	private Integer beautifySkinItemPrice;
	private Long receivedAmount;
	private Long receivedEarnedAmount;
	private Float cosmeticShopDiscount;
	private Float employeePremium;
	private Float shopPremium;
	private String createCardDate;
	private String sellerName;
	private String description;
	@Override
	public String toString() {
		return "SaleClientItemSellerForDBDTO [saleId=" + saleId + ", clientName=" + clientName
				+ ", beautifySkinItemName=" + beautifySkinItemName + ", cosmeticShopName=" + cosmeticShopName
				+ ", itemNumber=" + itemNumber + ", createCardTotalAmount=" + createCardTotalAmount
				+ ", beautifySkinItemPrice=" + beautifySkinItemPrice + ", receivedAmount=" + receivedAmount+", receivedEarnedAmount=" + receivedEarnedAmount
				+ ", cosmeticShopDiscount=" + cosmeticShopDiscount + ", employeePremium=" + employeePremium
				+ ", shopPremium=" + shopPremium + ", createCardDate=" + createCardDate + ", sellerName=" + sellerName
				+ ", description=" + description + "]";
	}
}
