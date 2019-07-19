package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ClientDetailDTO extends SaleDetailDTO{
	public ClientDetailDTO() {
	}
	private String clientName;//客户名
	private long allSalesCreateCardAmount;//单个顾客所有销售卡的总开卡金额
	private long allSalesEarnedAmount;//单个顾客所有销售卡的总回款金额
	private long allSalesConsumedAmount;//单个顾客所有销售卡的总消耗款
	private long allSalesConsumedEarnedAmount;//单个顾客所有销售卡的总消耗回款
	private long allSalesAdvancedEarnedAmount;//单个顾客所有销售卡的总预付款
	private List<SaleDetailDTO> saleDetailDTOs;//单个顾客下面所有的销售卡信息
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public long getAllSalesCreateCardAmount() {
		return allSalesCreateCardAmount;
	}
	public void setAllSalesCreateCardAmount(long allSalesCreateCardAmount) {
		this.allSalesCreateCardAmount = allSalesCreateCardAmount;
	}
	public long getAllSalesEarnedAmount() {
		return allSalesEarnedAmount;
	}
	public void setAllSalesEarnedAmount(long allSalesEarnedAmount) {
		this.allSalesEarnedAmount = allSalesEarnedAmount;
	}
	public long getAllSalesConsumedAmount() {
		return allSalesConsumedAmount;
	}
	public void setAllSalesConsumedAmount(long allSalesConsumedAmount) {
		this.allSalesConsumedAmount = allSalesConsumedAmount;
	}
	public long getAllSalesConsumedEarnedAmount() {
		return allSalesConsumedEarnedAmount;
	}
	public void setAllSalesConsumedEarnedAmount(long allSalesConsumedEarnedAmount) {
		this.allSalesConsumedEarnedAmount = allSalesConsumedEarnedAmount;
	}
	public long getAllSalesAdvancedEarnedAmount() {
		return allSalesAdvancedEarnedAmount;
	}
	public void setAllSalesAdvancedEarnedAmount(long allSalesAdvancedEarnedAmount) {
		this.allSalesAdvancedEarnedAmount = allSalesAdvancedEarnedAmount;
	}
	
	public List<SaleDetailDTO> getSaleDetailDTOs() {
		return saleDetailDTOs;
	}
	public void setSaleDetailDTOs(List<SaleDetailDTO> saleDetailDTOs) {
		this.saleDetailDTOs = saleDetailDTOs;
	}
	public ClientDetailDTO(String clientName, long allSalesCreateCardAmount, long allSalesEarnedAmount,
			long allSalesConsumedAmount, long allSalesConsumedEarnedAmount, long allSalesAdvancedEarnedAmount) {
		super();
		this.clientName = clientName;
		this.allSalesCreateCardAmount = allSalesCreateCardAmount;
		this.allSalesEarnedAmount = allSalesEarnedAmount;
		this.allSalesConsumedAmount = allSalesConsumedAmount;
		this.allSalesConsumedEarnedAmount = allSalesConsumedEarnedAmount;
		this.allSalesAdvancedEarnedAmount = allSalesAdvancedEarnedAmount;
	}
	@Override
	public String toString() {
		return "ClientDetailDTO [clientName=" + clientName + ", allSalesCreateCardAmount=" + allSalesCreateCardAmount
				+ ", allSalesEarnedAmount=" + allSalesEarnedAmount + ", allSalesConsumedAmount="
				+ allSalesConsumedAmount + ", allSalesConsumedEarnedAmount=" + allSalesConsumedEarnedAmount
				+ ", allSalesAdvancedEarnedAmount=" + allSalesAdvancedEarnedAmount + "]";
	}
	
}