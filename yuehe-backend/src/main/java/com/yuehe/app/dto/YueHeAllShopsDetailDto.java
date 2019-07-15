package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class YueHeAllShopsDetailDto extends ShopDetailDto{
	public YueHeAllShopsDetailDto() {
		//super();
	}
	private String yueheCompanyName;//悦和国际的大名
	private long allShopsCreateCardTotalAmount;//悦和所有店的总开卡金额
	private long allShopsEarnedAmount;//悦和所有店的总回款金额
	private long allShopsConsumedAmount;//悦和所有店的总消耗款
	private long allShopsConsumedEarnedAmount;//悦和所有店的总消耗回款
	private long allShopsAdvancedEarnedAmount;//悦和所有店的总预付款
	private List<ShopDetailDto> shopDetailDtos;//悦和下面所有的店的信息
	public String getYueheCompanyName() {
		return yueheCompanyName;
	}
	public void setYueheCompanyName(String yueheCompanyName) {
		this.yueheCompanyName = yueheCompanyName;
	}
	public long getAllShopsCreateCardTotalAmount() {
		return allShopsCreateCardTotalAmount;
	}
	public void setAllShopsCreateCardTotalAmount(long allShopsCreateCardTotalAmount) {
		this.allShopsCreateCardTotalAmount = allShopsCreateCardTotalAmount;
	}
	public long getAllShopsEarnedAmount() {
		return allShopsEarnedAmount;
	}
	public void setAllShopsEarnedAmount(long allShopsEarnedAmount) {
		this.allShopsEarnedAmount = allShopsEarnedAmount;
	}
	public long getAllShopsConsumedAmount() {
		return allShopsConsumedAmount;
	}
	public void setAllShopsConsumedAmount(long allShopsConsumedAmount) {
		this.allShopsConsumedAmount = allShopsConsumedAmount;
	}
	public long getAllShopsConsumedEarnedAmount() {
		return allShopsConsumedEarnedAmount;
	}
	public void setAllShopsConsumedEarnedAmount(long allShopsConsumedEarnedAmount) {
		this.allShopsConsumedEarnedAmount = allShopsConsumedEarnedAmount;
	}
	public long getAllShopsAdvancedEarnedAmount() {
		return allShopsAdvancedEarnedAmount;
	}
	public void setAllShopsAdvancedEarnedAmount(long allShopsAdvancedEarnedAmount) {
		this.allShopsAdvancedEarnedAmount = allShopsAdvancedEarnedAmount;
	}
	
	public List<ShopDetailDto> getShopDetailDtos() {
		return shopDetailDtos;
	}
	public void setShopDetailDtos(List<ShopDetailDto> shopDetailDtos) {
		this.shopDetailDtos = shopDetailDtos;
	}
	public YueHeAllShopsDetailDto(String yueheCompanyName, long allShopsCreateCardTotalAmount,
			long allShopsEarnedAmount, long allShopsConsumedAmount, long allShopsConsumedEarnedAmount,
			long allShopsAdvancedEarnedAmount) {
		super();
		this.yueheCompanyName = yueheCompanyName;
		this.allShopsCreateCardTotalAmount = allShopsCreateCardTotalAmount;
		this.allShopsEarnedAmount = allShopsEarnedAmount;
		this.allShopsConsumedAmount = allShopsConsumedAmount;
		this.allShopsConsumedEarnedAmount = allShopsConsumedEarnedAmount;
		this.allShopsAdvancedEarnedAmount = allShopsAdvancedEarnedAmount;
	}
	@Override
	public String toString() {
		return "YueHeAllShopsDetailDto [yueheCompanyName=" + yueheCompanyName + ", allShopsCreateCardTotalAmount="
				+ allShopsCreateCardTotalAmount + ", allShopsEarnedAmount=" + allShopsEarnedAmount
				+ ", allShopsConsumedAmount=" + allShopsConsumedAmount
				+ ", allShopsConsumedEarnedAmount=" + allShopsConsumedEarnedAmount
				+ ", allShopsAdvancedEarnedAmount=" + allShopsAdvancedEarnedAmount + "]";
	}
	
}