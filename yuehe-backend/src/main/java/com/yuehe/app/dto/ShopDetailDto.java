package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ShopDetailDto extends ClientDetailDto{
	public ShopDetailDto() {
	}
	private String cosmeticShopName;//美容院名
	private long allClientsCreateCardAmount;//单店所有顾客的总开卡金额
	private long allClientsEarnedAmount;//单店所有顾客的总回款金额
	private long allClientsConsumedAmount;//单店所有顾客的总消耗款
	private long allClientsConsumedEarnedAmount;//单店所有顾客的总消耗回款
	private long allClientsAdvancedEarnedAmount;//单店所有顾客的总预付款
	private List<ClientDetailDto> clientDetailDtos;//单店下面所有的客户的信息
	public String getCosmeticShopName() {
		return cosmeticShopName;
	}
	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}
	public long getAllClientsCreateCardAmount() {
		return allClientsCreateCardAmount;
	}
	public void setAllClientsCreateCardAmount(long totalClientCreateCardAmount) {
		allClientsCreateCardAmount = totalClientCreateCardAmount;
	}
	public long getAllClientsEarnedAmount() {
		return allClientsEarnedAmount;
	}
	public void setAllClientsEarnedAmount(long totalClientEarnedAmount) {
		allClientsEarnedAmount = totalClientEarnedAmount;
	}
	public long getAllClientsConsumedAmount() {
		return allClientsConsumedAmount;
	}
	public void setAllClientsConsumedAmount(long totalClientConsumedAmount) {
		allClientsConsumedAmount = totalClientConsumedAmount;
	}
	public long getAllClientsConsumedEarnedAmount() {
		return allClientsConsumedEarnedAmount;
	}
	public void setAllClientsConsumedEarnedAmount(long totalClientConsumedEarnedAmount) {
		allClientsConsumedEarnedAmount = totalClientConsumedEarnedAmount;
	}
	public long getAllClientsAdvancedEarnedAmount() {
		return allClientsAdvancedEarnedAmount;
	}
	public void setAllClientsAdvancedEarnedAmount(long totalClientAdvancedEarnedAmount) {
		allClientsAdvancedEarnedAmount = totalClientAdvancedEarnedAmount;
	}
	
	public List<ClientDetailDto> getClientDetailDtos() {
		return clientDetailDtos;
	}
	public void setClientDetailDtos(List<ClientDetailDto> clientDetailDtos) {
		this.clientDetailDtos = clientDetailDtos;
	}
	public ShopDetailDto(String cosmeticShopName, long allClientsCreateCardAmount, long allClientsEarnedAmount,
			long allClientsConsumedAmount, long allClientsConsumedEarnedAmount, long allClientsAdvancedEarnedAmount) {
		super();
		this.cosmeticShopName = cosmeticShopName;
		this.allClientsCreateCardAmount = allClientsCreateCardAmount;
		this.allClientsEarnedAmount = allClientsEarnedAmount;
		this.allClientsConsumedAmount = allClientsConsumedAmount;
		this.allClientsConsumedEarnedAmount = allClientsConsumedEarnedAmount;
		this.allClientsAdvancedEarnedAmount = allClientsAdvancedEarnedAmount;
	}
	@Override
	public String toString() {
		return "ShopDetailDto [cosmeticShopName=" + cosmeticShopName + ", allClientsCreateCardAmount="
				+ allClientsCreateCardAmount + ", allClientsEarnedAmount=" + allClientsEarnedAmount
				+ ", allClientsConsumedAmount=" + allClientsConsumedAmount + ", allClientsConsumedEarnedAmount="
				+ allClientsConsumedEarnedAmount + ", allClientsAdvancedEarnedAmount=" + allClientsAdvancedEarnedAmount
				+ "]";
	}
	
}