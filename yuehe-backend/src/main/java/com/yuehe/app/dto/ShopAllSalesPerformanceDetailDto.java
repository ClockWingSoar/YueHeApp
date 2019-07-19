package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it needs to join other tables
 * to get seller name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Client} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ShopAllSalesPerformanceDetailDTO {
	public ShopAllSalesPerformanceDetailDTO() {
		//super();
	}
	private String cosmeticShopName;
	private long allClientsSalesCreateCardTotalAmount;
	private long allClientsSalesReceivedAmount;
	private long allClientsSalesDebtAmount;
	private long allClientsSalesEarnedAmount;
	private long allClientsSalesReceivedEarnedAmount;
	private long allClientsSalesDebtEarnedAmount;
	private float allClientsSalesEmployeePremium;
	private float allClientsSalesShopPremium;
	private List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOs;
	public long getAllClientsSalesCreateCardTotalAmount() {
		return allClientsSalesCreateCardTotalAmount;
	}
	public void setAllClientsSalesCreateCardTotalAmount(long allClientsSalesCreateCardTotalAmount) {
		this.allClientsSalesCreateCardTotalAmount = allClientsSalesCreateCardTotalAmount;
	}
	public long getAllClientsSalesReceivedAmount() {
		return allClientsSalesReceivedAmount;
	}
	public void setAllClientsSalesReceivedAmount(long allClientsSalesReceivedAmount) {
		this.allClientsSalesReceivedAmount = allClientsSalesReceivedAmount;
	}
	public long getAllClientsSalesDebtAmount() {
		return allClientsSalesDebtAmount;
	}
	public void setAllClientsSalesDebtAmount(long allClientsSalesDebtAmount) {
		this.allClientsSalesDebtAmount = allClientsSalesDebtAmount;
	}
	public long getAllClientsSalesEarnedAmount() {
		return allClientsSalesEarnedAmount;
	}
	public void setAllClientsSalesEarnedAmount(long allClientsSalesEarnedAmount) {
		this.allClientsSalesEarnedAmount = allClientsSalesEarnedAmount;
	}
	public long getAllClientsSalesReceivedEarnedAmount() {
		return allClientsSalesReceivedEarnedAmount;
	}
	public void setAllClientsSalesReceivedEarnedAmount(long allClientsSalesReceivedEarnedAmount) {
		this.allClientsSalesReceivedEarnedAmount = allClientsSalesReceivedEarnedAmount;
	}
	public long getAllClientsSalesDebtEarnedAmount() {
		return allClientsSalesDebtEarnedAmount;
	}
	public void setAllClientsSalesDebtEarnedAmount(long allClientsSalesDebtEarnedAmount) {
		this.allClientsSalesDebtEarnedAmount = allClientsSalesDebtEarnedAmount;
	}
	public float getAllClientsSalesEmployeePremium() {
		return allClientsSalesEmployeePremium;
	}
	public void setAllClientsSalesEmployeePremium(float allClientsSalesEmployeePremium) {
		this.allClientsSalesEmployeePremium = allClientsSalesEmployeePremium;
	}
	public float getAllClientsSalesShopPremium() {
		return allClientsSalesShopPremium;
	}
	public void setAllClientsSalesShopPremium(float allClientsSalesShopPremium) {
		this.allClientsSalesShopPremium = allClientsSalesShopPremium;
	}
	public List<ClientAllSalesPerformanceDetailDTO> getClientAllSalesPerformanceDetailDTOs() {
		return clientAllSalesPerformanceDetailDTOs;
	}
	public void setClientAllSalesPerformanceDetailDTOs(
			List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOs) {
		this.clientAllSalesPerformanceDetailDTOs = clientAllSalesPerformanceDetailDTOs;
	}
	
	public String getCosmeticShopName() {
		return cosmeticShopName;
	}
	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}
	public ShopAllSalesPerformanceDetailDTO(String cosmeticShopName, long allClientsSalesCreateCardTotalAmount,
			long allClientsSalesReceivedAmount, long allClientsSalesDebtAmount, long allClientsSalesEarnedAmount,
			long allClientsSalesReceivedEarnedAmount, long allClientsSalesDebtEarnedAmount,
			float allClientsSalesEmployeePremium, float allClientsSalesShopPremium,
			List<ClientAllSalesPerformanceDetailDTO> clientAllSalesPerformanceDetailDTOs) {
		super();
		this.cosmeticShopName = cosmeticShopName;
		this.allClientsSalesCreateCardTotalAmount = allClientsSalesCreateCardTotalAmount;
		this.allClientsSalesReceivedAmount = allClientsSalesReceivedAmount;
		this.allClientsSalesDebtAmount = allClientsSalesDebtAmount;
		this.allClientsSalesEarnedAmount = allClientsSalesEarnedAmount;
		this.allClientsSalesReceivedEarnedAmount = allClientsSalesReceivedEarnedAmount;
		this.allClientsSalesDebtEarnedAmount = allClientsSalesDebtEarnedAmount;
		this.allClientsSalesEmployeePremium = allClientsSalesEmployeePremium;
		this.allClientsSalesShopPremium = allClientsSalesShopPremium;
		this.clientAllSalesPerformanceDetailDTOs = clientAllSalesPerformanceDetailDTOs;
	}
	@Override
	public String toString() {
		return "ShopAllSalesPerformanceDetailDTO [cosmeticShopName=" + cosmeticShopName + ", allClientsSalesCreateCardTotalAmount="
				+ allClientsSalesCreateCardTotalAmount + ", allClientsSalesReceivedAmount="
				+ allClientsSalesReceivedAmount + ", allClientsSalesDebtAmount=" + allClientsSalesDebtAmount
				+ ", allClientsSalesEarnedAmount=" + allClientsSalesEarnedAmount
				+ ", allClientsSalesReceivedEarnedAmount=" + allClientsSalesReceivedEarnedAmount
				+ ", allClientsSalesDebtEarnedAmount=" + allClientsSalesDebtEarnedAmount
				+ ", allClientsSalesEmployeePremium=" + allClientsSalesEmployeePremium + ", allClientsSalesShopPremium="
				+ allClientsSalesShopPremium + ", clientAllSalesPerformanceDetailDTOs="
				+ clientAllSalesPerformanceDetailDTOs + "]";
	}
	
	
}
