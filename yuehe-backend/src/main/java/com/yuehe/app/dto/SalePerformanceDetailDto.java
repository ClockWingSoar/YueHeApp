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
public class SalePerformanceDetailDto {
	public SalePerformanceDetailDto() {
		//super();
	}
	private String saleId;
	private String createCardDate;
	private String beautifySkinItemName;
	private long createCardTotalAmount;
	private long receivedAmount;
	private long debtAmount;
	private long earnedAmount;
	private long receivedEarnedAmount;
	private long debtEarnedAmount;
	private int itemNumber;
	private float cosmeticShopDiscount;
	private float employeePremium;
	private float shopPremium;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getCreateCardDate() {
		return createCardDate;
	}
	public void setCreateCardDate(String createCardDate) {
		this.createCardDate = createCardDate;
	}
	public String getBeautifySkinItemName() {
		return beautifySkinItemName;
	}
	public void setBeautifySkinItemName(String beautifySkinItemName) {
		this.beautifySkinItemName = beautifySkinItemName;
	}
	public long getCreateCardTotalAmount() {
		return createCardTotalAmount;
	}
	public void setCreateCardTotalAmount(long createCardTotalAmount) {
		this.createCardTotalAmount = createCardTotalAmount;
	}
	public long getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(long receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public long getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(long debtAmount) {
		this.debtAmount = debtAmount;
	}
	public long getEarnedAmount() {
		return earnedAmount;
	}
	public void setEarnedAmount(long earnedAmount) {
		this.earnedAmount = earnedAmount;
	}
	public long getReceivedEarnedAmount() {
		return receivedEarnedAmount;
	}
	public void setReceivedEarnedAmount(long receivedEarnedAmount) {
		this.receivedEarnedAmount = receivedEarnedAmount;
	}
	public long getDebtEarnedAmount() {
		return debtEarnedAmount;
	}
	public void setDebtEarnedAmount(long debtEarnedAmount) {
		this.debtEarnedAmount = debtEarnedAmount;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public float getCosmeticShopDiscount() {
		return cosmeticShopDiscount;
	}
	public void setCosmeticShopDiscount(float cosmeticShopDiscount) {
		this.cosmeticShopDiscount = cosmeticShopDiscount;
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
	public SalePerformanceDetailDto(String saleId, String createCardDate, String beautifySkinItemName,
			long createCardTotalAmount, long receivedAmount, long debtAmount, long earnedAmount,
			long receivedEarnedAmount, long debtEarnedAmount, int itemNumber, float cosmeticShopDiscount,
			float employeePremium, float shopPremium, String description) {
		super();
		this.saleId = saleId;
		this.createCardDate = createCardDate;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardTotalAmount = createCardTotalAmount;
		this.receivedAmount = receivedAmount;
		this.debtAmount = debtAmount;
		this.earnedAmount = earnedAmount;
		this.receivedEarnedAmount = receivedEarnedAmount;
		this.debtEarnedAmount = debtEarnedAmount;
		this.itemNumber = itemNumber;
		this.cosmeticShopDiscount = cosmeticShopDiscount;
		this.employeePremium = employeePremium;
		this.shopPremium = shopPremium;
		this.description = description;
	}
	@Override
	public String toString() {
		return "SalePerformanceDetailDto [saleId=" + saleId + ", createCardDate=" + createCardDate
				+ ", beautifySkinItemName=" + beautifySkinItemName + ", createCardTotalAmount=" + createCardTotalAmount
				+ ", receivedAmount=" + receivedAmount + ", debtAmount=" + debtAmount + ", earnedAmount=" + earnedAmount
				+ ", receivedEarnedAmount=" + receivedEarnedAmount + ", debtEarnedAmount=" + debtEarnedAmount
				+ ", itemNumber=" + itemNumber + ", cosmeticShopDiscount=" + cosmeticShopDiscount + ", employeePremium="
				+ employeePremium + ", shopPremium=" + shopPremium + ", description=" + description + "]";
	}
	
}
