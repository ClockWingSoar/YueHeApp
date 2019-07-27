package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to
 * show the basic operation table view class
 * {@link com.yuehe.app.entity.Operation} which is aim to symplify the data
 * store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
public class SaleDetailForDBDTO{
	public SaleDetailForDBDTO() {
	}
//	(s.id,s.createCardDate, b.name,  "
//			+ "s.createCardTotalAmount,s.itemNumber,b.price,"
//			+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String beautifySkinItemName;//美肤项目
	private long createCardTotalAmount;//开卡金额
	private int itemNumber;//开卡次数-疗程总次数
	private float cosmeticShopDiscount;//美容院给悦和的折扣点
	private float employeePremium;//单张销售卡给员工的奖励
	private float shopPremium;//单张销售卡给美容院的回扣
	private String description;//备注
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
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SaleDetailForDBDTO(String saleId, String createCardDate, String beautifySkinItemName,
			long createCardTotalAmount, int itemNumber, float cosmeticShopDiscount,
			float employeePremium, float shopPremium, String description) {
		//super();
		this.saleId = saleId;
		this.createCardDate = createCardDate;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardTotalAmount = createCardTotalAmount;
		this.itemNumber = itemNumber;
		this.cosmeticShopDiscount = cosmeticShopDiscount;
		this.employeePremium = employeePremium;
		this.shopPremium = shopPremium;
		this.description = description;
	}
	@Override
	public String toString() {
		return "SaleDetailForDBDTO [saleId=" + saleId + ", createCardDate=" + createCardDate + ", beautifySkinItemName="
				+ beautifySkinItemName + ", createCardTotalAmount=" + createCardTotalAmount + ", itemNumber="
				+ itemNumber + ", cosmeticShopDiscount="
				+ cosmeticShopDiscount + ", employeePremium=" + employeePremium + ", shopPremium=" + shopPremium
				+ ", description=" + description + "]";
	}
	
	
}