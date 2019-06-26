package com.yuehe.app.dto;

import java.util.Date;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it needs to join other tables
 * to get seller name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Client} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class SaleClientItemSellerDto {
	public SaleClientItemSellerDto() {
		//super();
	}
	private String saleId;
	private String clientName;
	private String beautifySkinItemName;
	private String cosmeticShopName;
	private String sellerName;
	private int itemNumber;
	private float discount;
	private float employeePremium;
	private float shopPremium;
	private int receivedAmount;
	private Date createCardDate;
	private String description;
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBeautifySkinItemName() {
		return beautifySkinItemName;
	}
	public void setBeautifySkinItemName(String beautifySkinItemName) {
		this.beautifySkinItemName = beautifySkinItemName;
	}
	public String getCosmeticShopName() {
		return cosmeticShopName;
	}
	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getEmployeePremium() {
		return employeePremium;
	}
	public void setEmployeePremium(float employeePremium) {
		this.employeePremium = employeePremium;
	}
	public float getShopPremium() {
		return shopPremium;
	}
	public void setShopPremium(float shopPremium) {
		this.shopPremium = shopPremium;
	}
	public float getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public Date getCreateCardDate() {
		return createCardDate;
	}
	public void setCreateCardDate(Date createCardDate) {
		this.createCardDate = createCardDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SaleClientItemSellerDto(String saleId, String clientName, String beautifySkinItemName, String cosmeticShopName,
			String sellerName, int itemNumber, float discount, float employeePremium, float shopPremium,
			int receivedAmount, Date createCardDate, String description) {
		super();
		this.saleId = saleId;
		this.clientName = clientName;
		this.beautifySkinItemName = beautifySkinItemName;
		this.cosmeticShopName = cosmeticShopName;
		this.sellerName = sellerName;
		this.itemNumber = itemNumber;
		this.discount = discount;
		this.employeePremium = employeePremium;
		this.shopPremium = shopPremium;
		this.receivedAmount = receivedAmount;
		this.createCardDate = createCardDate;
		this.description = description;
	}
	@Override
	public String toString() {
		return "SaleClientItemSellerDto [saleId=" + saleId + ", clientName=" + clientName + ", beautifySkinItemName="
				+ beautifySkinItemName + ", cosmeticShopName=" + cosmeticShopName + ", sellerName=" + sellerName
				+ ", itemNumber=" + itemNumber + ", discount=" + discount + ", employeePremium=" + employeePremium
				+ ", shopPremium=" + shopPremium + ", receivedAmount=" + receivedAmount + ", createCardDate="
				+ createCardDate + ", description=" + description + "]";
	}
	
	
}
