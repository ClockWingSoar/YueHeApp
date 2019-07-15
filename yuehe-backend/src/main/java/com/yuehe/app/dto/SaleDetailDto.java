package com.yuehe.app.dto;

import java.util.List;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class SaleDetailDto extends OperationDetailDto{
	public SaleDetailDto() {
	}
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String beautifySkinItemName;//美肤项目
	private long createCardTotalAmount;//开卡金额
	private long earnedAmount;//回款金额
	private int itemNumber;//开卡次数-疗程总次数
	private int restItemNumber;//剩余次数
	private long consumedAmount;//已消耗总数
	private long consumedEarnedAmount;//已消耗回款
	private long advancedEarnedAmount;//预付款总数
	private String description;//备注
	private List<OperationDetailDto> operationDetailDtos;//单张美肤卡下面所有的操作的信息
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
	public int getRestItemNumber() {
		return restItemNumber;
	}
	public void setRestItemNumber(int restItemNumber) {
		this.restItemNumber = restItemNumber;
	}
	public long getConsumedAmount() {
		return consumedAmount;
	}
	public void setConsumedAmount(long consumedAmount) {
		this.consumedAmount = consumedAmount;
	}
	public long getConsumedEarnedAmount() {
		return consumedEarnedAmount;
	}
	public void setConsumedEarnedAmount(long consumedEarnedAmount) {
		this.consumedEarnedAmount = consumedEarnedAmount;
	}
	public long getAdvancedEarnedAmount() {
		return advancedEarnedAmount;
	}
	public void setAdvancedEarnedAmount(long advancedEarnedAmount) {
		this.advancedEarnedAmount = advancedEarnedAmount;
	}
	
	
	public long getEarnedAmount() {
		return earnedAmount;
	}
	public void setEarnedAmount(long earnedAmount) {
		this.earnedAmount = earnedAmount;
	}
	
	
	public List<OperationDetailDto> getOperationDetailDtos() {
		return operationDetailDtos;
	}
	public void setOperationDetailDtos(List<OperationDetailDto> operationDetailDtos) {
		this.operationDetailDtos = operationDetailDtos;
	}
	public SaleDetailDto(String saleId, String createCardDate, String beautifySkinItemName, long createCardTotalAmount,
			long earnedAmount,int itemNumber, String description, int restItemNumber, long consumedAmount, long consumedEarnedAmount,
			long advancedEarnedAmount) {
		super();
		this.saleId = saleId;
		this.createCardDate = createCardDate;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardTotalAmount = createCardTotalAmount;
		this.earnedAmount = earnedAmount;
		this.itemNumber = itemNumber;
		this.description = description;
		this.restItemNumber = restItemNumber;
		this.consumedAmount = consumedAmount;
		this.consumedEarnedAmount = consumedEarnedAmount;
		this.advancedEarnedAmount = advancedEarnedAmount;
	}
	public SaleDetailDto(String saleId, String createCardDate, String beautifySkinItemName, long createCardTotalAmount,
			long earnedAmount,int itemNumber, String description, int restItemNumber, long consumedAmount, long consumedEarnedAmount,
			long advancedEarnedAmount,String operationId, String operationDate, String operatorName, String toolName,
			int operateExpense, String operationDescription) {
		this.saleId = saleId;
		this.createCardDate = createCardDate;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardTotalAmount = createCardTotalAmount;
		this.earnedAmount = earnedAmount;
		this.itemNumber = itemNumber;
		this.description = description;
		this.restItemNumber = restItemNumber;
		this.consumedAmount = consumedAmount;
		this.consumedEarnedAmount = consumedEarnedAmount;
		this.advancedEarnedAmount = advancedEarnedAmount;
	}
	@Override
	public String toString() {
		return "SaleDetailDto [saleId=" + saleId + ", createCardDate=" + createCardDate + ", beautifySkinItemName="
				+ beautifySkinItemName + ", createCardTotalAmount=" + createCardTotalAmount + ", earnedAmount="
				+ earnedAmount + ", itemNumber=" + itemNumber + ", description=" + description + ", restItemNumber="
				+ restItemNumber + ", consumedAmount=" + consumedAmount + ", consumedEarnedAmount="
				+ consumedEarnedAmount + ", advancedEarnedAmount=" + advancedEarnedAmount + ", operationDetailDtos="
				+ operationDetailDtos + "]";
	}

	
}