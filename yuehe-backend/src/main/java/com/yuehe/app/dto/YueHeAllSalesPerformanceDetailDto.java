package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it needs to join other tables
 * to get seller name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Shop} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class YueHeAllSalesPerformanceDetailDTO {
	public YueHeAllSalesPerformanceDetailDTO() {
		//super();
	}
	private String companyName;
	private long allShopsSalesCreateCardTotalAmount;
	private long allShopsSalesReceivedAmount;
	private long allShopsSalesDebtAmount;
	private long allShopsSalesEarnedAmount;
	private long allShopsSalesReceivedEarnedAmount;
	private long allShopsSalesDebtEarnedAmount;
	private float allShopsSalesEmployeePremium;
	private float allShopsSalesShopPremium;
	private List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOs;
	public long getAllShopsSalesCreateCardTotalAmount() {
		return allShopsSalesCreateCardTotalAmount;
	}
	public void setAllShopsSalesCreateCardTotalAmount(long allShopsSalesCreateCardTotalAmount) {
		this.allShopsSalesCreateCardTotalAmount = allShopsSalesCreateCardTotalAmount;
	}
	public long getAllShopsSalesReceivedAmount() {
		return allShopsSalesReceivedAmount;
	}
	public void setAllShopsSalesReceivedAmount(long allShopsSalesReceivedAmount) {
		this.allShopsSalesReceivedAmount = allShopsSalesReceivedAmount;
	}
	public long getAllShopsSalesDebtAmount() {
		return allShopsSalesDebtAmount;
	}
	public void setAllShopsSalesDebtAmount(long allShopsSalesDebtAmount) {
		this.allShopsSalesDebtAmount = allShopsSalesDebtAmount;
	}
	public long getAllShopsSalesEarnedAmount() {
		return allShopsSalesEarnedAmount;
	}
	public void setAllShopsSalesEarnedAmount(long allShopsSalesEarnedAmount) {
		this.allShopsSalesEarnedAmount = allShopsSalesEarnedAmount;
	}
	public long getAllShopsSalesReceivedEarnedAmount() {
		return allShopsSalesReceivedEarnedAmount;
	}
	public void setAllShopsSalesReceivedEarnedAmount(long allShopsSalesReceivedEarnedAmount) {
		this.allShopsSalesReceivedEarnedAmount = allShopsSalesReceivedEarnedAmount;
	}
	public long getAllShopsSalesDebtEarnedAmount() {
		return allShopsSalesDebtEarnedAmount;
	}
	public void setAllShopsSalesDebtEarnedAmount(long allShopsSalesDebtEarnedAmount) {
		this.allShopsSalesDebtEarnedAmount = allShopsSalesDebtEarnedAmount;
	}
	public float getAllShopsSalesEmployeePremium() {
		return allShopsSalesEmployeePremium;
	}
	public void setAllShopsSalesEmployeePremium(float allShopsSalesEmployeePremium) {
		this.allShopsSalesEmployeePremium = allShopsSalesEmployeePremium;
	}
	public float getAllShopsSalesShopPremium() {
		return allShopsSalesShopPremium;
	}
	public void setAllShopsSalesShopPremium(float allShopsSalesShopPremium) {
		this.allShopsSalesShopPremium = allShopsSalesShopPremium;
	}
	public List<ShopAllSalesPerformanceDetailDTO> getShopAllSalesPerformanceDetailDTOs() {
		return shopAllSalesPerformanceDetailDTOs;
	}
	public void setShopAllSalesPerformanceDetailDTOs(
			List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOs) {
		this.shopAllSalesPerformanceDetailDTOs = shopAllSalesPerformanceDetailDTOs;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public YueHeAllSalesPerformanceDetailDTO(String companyName, long allShopsSalesCreateCardTotalAmount,
			long allShopsSalesReceivedAmount, long allShopsSalesDebtAmount, long allShopsSalesEarnedAmount,
			long allShopsSalesReceivedEarnedAmount, long allShopsSalesDebtEarnedAmount,
			float allShopsSalesEmployeePremium, float allShopsSalesShopPremium,
			List<ShopAllSalesPerformanceDetailDTO> shopAllSalesPerformanceDetailDTOs) {
		super();
		this.companyName = companyName;
		this.allShopsSalesCreateCardTotalAmount = allShopsSalesCreateCardTotalAmount;
		this.allShopsSalesReceivedAmount = allShopsSalesReceivedAmount;
		this.allShopsSalesDebtAmount = allShopsSalesDebtAmount;
		this.allShopsSalesEarnedAmount = allShopsSalesEarnedAmount;
		this.allShopsSalesReceivedEarnedAmount = allShopsSalesReceivedEarnedAmount;
		this.allShopsSalesDebtEarnedAmount = allShopsSalesDebtEarnedAmount;
		this.allShopsSalesEmployeePremium = allShopsSalesEmployeePremium;
		this.allShopsSalesShopPremium = allShopsSalesShopPremium;
		this.shopAllSalesPerformanceDetailDTOs = shopAllSalesPerformanceDetailDTOs;
	}
	@Override
	public String toString() {
		return "YueHeAllSalesPerformanceDetailDTO [companyName=" + companyName
				+ ", allShopsSalesCreateCardTotalAmount=" + allShopsSalesCreateCardTotalAmount
				+ ", allShopsSalesReceivedAmount=" + allShopsSalesReceivedAmount + ", allShopsSalesDebtAmount="
				+ allShopsSalesDebtAmount + ", allShopsSalesEarnedAmount=" + allShopsSalesEarnedAmount
				+ ", allShopsSalesReceivedEarnedAmount=" + allShopsSalesReceivedEarnedAmount
				+ ", allShopsSalesDebtEarnedAmount=" + allShopsSalesDebtEarnedAmount + ", allShopsSalesEmployeePremium="
				+ allShopsSalesEmployeePremium + ", allShopsSalesShopPremium=" + allShopsSalesShopPremium
				+ ", shopAllSalesPerformanceDetailDTOs=" + shopAllSalesPerformanceDetailDTOs + "]";
	}
	
}
