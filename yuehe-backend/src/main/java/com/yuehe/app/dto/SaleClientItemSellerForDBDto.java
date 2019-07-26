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
	// public SaleClientItemSellerForDBDTO() {
	// 	//super();
	// }
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
	// public SaleClientItemSellerForDBDTO(String saleId, String clientName, String beautifySkinItemName,
	// 		String cosmeticShopName, int itemNumber, long createCardTotalAmount, int beautifySkinItemPrice,
	// 		long receivedAmount,long receivedEarnedAmount, float cosmeticShopDiscount, float employeePremium, float shopPremium,
	// 		String createCardDate, String sellerName, String description) {
	// 	super();
	// 	this.saleId = saleId;
	// 	this.clientName = clientName;
	// 	this.beautifySkinItemName = beautifySkinItemName;
	// 	this.cosmeticShopName = cosmeticShopName;
	// 	this.itemNumber = itemNumber;
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// 	this.beautifySkinItemPrice = beautifySkinItemPrice;
	// 	this.receivedAmount = receivedAmount;
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// 	this.cosmeticShopDiscount = cosmeticShopDiscount;
	// 	this.employeePremium = employeePremium;
	// 	this.shopPremium = shopPremium;
	// 	this.createCardDate = createCardDate;
	// 	this.sellerName = sellerName;
	// 	this.description = description;
	// }
	// public String getSaleId() {
	// 	return saleId;
	// }
	// public void setSaleId(String saleId) {
	// 	this.saleId = saleId;
	// }
	// public String getClientName() {
	// 	return clientName;
	// }
	// public void setClientName(String clientName) {
	// 	this.clientName = clientName;
	// }
	// public String getBeautifySkinItemName() {
	// 	return beautifySkinItemName;
	// }
	// public void setBeautifySkinItemName(String beautifySkinItemName) {
	// 	this.beautifySkinItemName = beautifySkinItemName;
	// }
	// public String getCosmeticShopName() {
	// 	return cosmeticShopName;
	// }
	// public void setCosmeticShopName(String cosmeticShopName) {
	// 	this.cosmeticShopName = cosmeticShopName;
	// }
	// public String getSellerName() {
	// 	return sellerName;
	// }
	// public void setSellerName(String sellerName) {
	// 	this.sellerName = sellerName;
	// }
	// public int getItemNumber() {
	// 	return itemNumber;
	// }
	// public void setItemNumber(int itemNumber) {
	// 	this.itemNumber = itemNumber;
	// }
	// public float getEmployeePremium() {
	// 	return employeePremium;
	// }
	// public void setEmployeePremium(float employeePremium) {
	// 	this.employeePremium = employeePremium;
	// }
	// public float getShopPremium() {
	// 	return shopPremium;
	// }
	// public void setShopPremium(float shopPremium) {
	// 	this.shopPremium = shopPremium;
	// }
	// public long getReceivedAmount() {
	// 	return receivedAmount;
	// }
	// public void setReceivedAmount(long receivedAmount) {
	// 	this.receivedAmount = receivedAmount;
	// }
	
	// public long getReceivedEarnedAmount() {
	// 	return receivedEarnedAmount;
	// }
	// public void setReceivedEarnedAmount(long receivedEarnedAmount) {
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// }
	// public String getCreateCardDate() {
	// 	return createCardDate;
	// }
	// public void setCreateCardDate(String createCardDate) {
	// 	this.createCardDate = createCardDate;
	// }
	// public String getDescription() {
	// 	return description;
	// }
	// public void setDescription(String description) {
	// 	this.description = description;
	// }
	// public long getCreateCardTotalAmount() {
	// 	return createCardTotalAmount;
	// }
	// public void setCreateCardTotalAmount(long createCardTotalAmount) {
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// }
	// public float getCosmeticShopDiscount() {
	// 	return cosmeticShopDiscount;
	// }
	// public void setCosmeticShopDiscount(float cosmeticShopDiscount) {
	// 	this.cosmeticShopDiscount = cosmeticShopDiscount;
	// }
	// public int getBeautifySkinItemPrice() {
	// 	return beautifySkinItemPrice;
	// }
	// public void setBeautifySkinItemPrice(int beautifySkinItemPrice) {
	// 	this.beautifySkinItemPrice = beautifySkinItemPrice;
	// }
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
