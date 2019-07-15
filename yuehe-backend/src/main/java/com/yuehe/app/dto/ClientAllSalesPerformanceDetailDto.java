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
public class ClientAllSalesPerformanceDetailDto {
	public ClientAllSalesPerformanceDetailDto() {
		//super();
	}
	private String clientName;
	private long allSalesCreateCardTotalAmount;
	private long allSalesReceivedAmount;
	private long allSalesDebtAmount;
	private long allSalesEarnedAmount;
	private long allSalesReceivedEarnedAmount;
	private long allSalesDebtEarnedAmount;
	private float allSalesEmployeePremium;
	private float allSalesShopPremium;
	private List<SalePerformanceDetailDto> salePerformanceDetailDtos;
	public long getAllSalesCreateCardTotalAmount() {
		return allSalesCreateCardTotalAmount;
	}
	public void setAllSalesCreateCardTotalAmount(long allSalesCreateCardTotalAmount) {
		this.allSalesCreateCardTotalAmount = allSalesCreateCardTotalAmount;
	}
	public long getAllSalesReceivedAmount() {
		return allSalesReceivedAmount;
	}
	public void setAllSalesReceivedAmount(long allSalesReceivedAmount) {
		this.allSalesReceivedAmount = allSalesReceivedAmount;
	}
	public long getAllSalesDebtAmount() {
		return allSalesDebtAmount;
	}
	public void setAllSalesDebtAmount(long allSalesDebtAmount) {
		this.allSalesDebtAmount = allSalesDebtAmount;
	}
	public long getAllSalesEarnedAmount() {
		return allSalesEarnedAmount;
	}
	public void setAllSalesEarnedAmount(long allSalesEarnedAmount) {
		this.allSalesEarnedAmount = allSalesEarnedAmount;
	}
	public long getAllSalesReceivedEarnedAmount() {
		return allSalesReceivedEarnedAmount;
	}
	public void setAllSalesReceivedEarnedAmount(long allSalesReceivedEarnedAmount) {
		this.allSalesReceivedEarnedAmount = allSalesReceivedEarnedAmount;
	}
	public long getAllSalesDebtEarnedAmount() {
		return allSalesDebtEarnedAmount;
	}
	public void setAllSalesDebtEarnedAmount(long allSalesDebtEarnedAmount) {
		this.allSalesDebtEarnedAmount = allSalesDebtEarnedAmount;
	}
	public float getAllSalesEmployeePremium() {
		return allSalesEmployeePremium;
	}
	public void setAllSalesEmployeePremium(float allSalesEmployeePremium) {
		this.allSalesEmployeePremium = allSalesEmployeePremium;
	}
	public float getAllSalesShopPremium() {
		return allSalesShopPremium;
	}
	public void setAllSalesShopPremium(float allSalesShopPremium) {
		this.allSalesShopPremium = allSalesShopPremium;
	}
	public List<SalePerformanceDetailDto> getSalePerformanceDetailDtos() {
		return salePerformanceDetailDtos;
	}
	public void setSalePerformanceDetailDtos(List<SalePerformanceDetailDto> salePerformanceDetailDtos) {
		this.salePerformanceDetailDtos = salePerformanceDetailDtos;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public ClientAllSalesPerformanceDetailDto(String clientName, long allSalesCreateCardTotalAmount,
			long allSalesReceivedAmount, long allSalesDebtAmount, long allSalesEarnedAmount,
			long allSalesReceivedEarnedAmount, long allSalesDebtEarnedAmount, float allSalesEmployeePremium,
			float allSalesShopPremium, List<SalePerformanceDetailDto> salePerformanceDetailDtos) {
		super();
		this.clientName = clientName;
		this.allSalesCreateCardTotalAmount = allSalesCreateCardTotalAmount;
		this.allSalesReceivedAmount = allSalesReceivedAmount;
		this.allSalesDebtAmount = allSalesDebtAmount;
		this.allSalesEarnedAmount = allSalesEarnedAmount;
		this.allSalesReceivedEarnedAmount = allSalesReceivedEarnedAmount;
		this.allSalesDebtEarnedAmount = allSalesDebtEarnedAmount;
		this.allSalesEmployeePremium = allSalesEmployeePremium;
		this.allSalesShopPremium = allSalesShopPremium;
		this.salePerformanceDetailDtos = salePerformanceDetailDtos;
	}
	@Override
	public String toString() {
		return "ClientAllSalesPerformanceDetailDto [clientName=" + clientName + ", allSalesCreateCardTotalAmount="
				+ allSalesCreateCardTotalAmount + ", allSalesReceivedAmount=" + allSalesReceivedAmount
				+ ", allSalesDebtAmount=" + allSalesDebtAmount + ", allSalesEarnedAmount=" + allSalesEarnedAmount
				+ ", allSalesReceivedEarnedAmount=" + allSalesReceivedEarnedAmount + ", allSalesDebtEarnedAmount="
				+ allSalesDebtEarnedAmount + ", allSalesEmployeePremium=" + allSalesEmployeePremium
				+ ", allSalesShopPremium=" + allSalesShopPremium + ", salePerformanceDetailDtos="
				+ salePerformanceDetailDtos + "]";
	}
	
}
