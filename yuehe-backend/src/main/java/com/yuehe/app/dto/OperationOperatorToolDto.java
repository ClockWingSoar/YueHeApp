package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of operation.html,using when it needs to join other tables
 * to get employee name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class OperationOperatorToolDTO {
	public OperationOperatorToolDTO() {
		//super();
	}
	//private String operationId;//操作ID
//	private String saleId;//销售卡ID
//	private String createCardDate;//开卡日期
//	private String operationDate;//操作日期
//	private String clientName;//客户名
//	private String cosmeticShopName;//美容院名===================
//	private String beautifySkinItemName;//美肤项目
//	private long createCardTotalAmount;//开卡金额
//	private long earnedTotalAmount;//回款金额------------------------------------------
//	private int totalItemNumber;//开卡次数-疗程总次数
//	private int restItemNumber;//剩余次数--------------------------------------------------
//	private long consumedTotalAmount;//已消耗总数----------------------------------------
//	private long consumedEarnedTotalAmount;//已消耗回款------------------------------
//	private long advancedEarnedTotalAmount;//预付款总数----------------------------------
//	private String operatorName;//操作人
//	private String toolName;//操作仪器=======================
//	private int operateExpense;//操作费用+++++++++++++++++++++
//	private String description;//备注
	private String operationId;//操作ID
	private String saleId;//销售卡ID
	private String createCardDate;//开卡日期
	private String operationDate;//操作日期
	private String clientName;//客户名
	private String cosmeticShopName;//美容院名
	private String beautifySkinItemName;//美肤项目
	private long createCardTotalAmount;//开卡金额
	private long earnedTotalAmount;//回款金额
	private int totalItemNumber;//开卡次数-疗程总次数
	private int restItemNumber;//剩余次数
	private long consumedTotalAmount;//已消耗总数
	private long consumedEarnedTotalAmount;//已消耗回款
	private long advancedEarnedTotalAmount;//预付款总数
	private String operatorName;//操作人
	private String toolName;//操作仪器
	private int operateExpense;//操作费用
	private String description;//备注
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
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
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
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
	public long getCreateCardTotalAmount() {
		return createCardTotalAmount;
	}
	public void setCreateCardTotalAmount(long createCardTotalAmount) {
		this.createCardTotalAmount = createCardTotalAmount;
	}
	public long getEarnedTotalAmount() {
		return earnedTotalAmount;
	}
	public void setEarnedTotalAmount(long earnedTotalAmount) {
		this.earnedTotalAmount = earnedTotalAmount;
	}
	public int getTotalItemNumber() {
		return totalItemNumber;
	}
	public void setTotalItemNumber(int totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	public int getRestItemNumber() {
		return restItemNumber;
	}
	public void setRestItemNumber(int restItemNumber) {
		this.restItemNumber = restItemNumber;
	}
	public long getConsumedTotalAmount() {
		return consumedTotalAmount;
	}
	public void setConsumedTotalAmount(long consumedTotalAmount) {
		this.consumedTotalAmount = consumedTotalAmount;
	}
	public long getConsumedEarnedTotalAmount() {
		return consumedEarnedTotalAmount;
	}
	public void setConsumedEarnedTotalAmount(long consumedEarnedTotalAmount) {
		this.consumedEarnedTotalAmount = consumedEarnedTotalAmount;
	}
	public long getAdvancedEarnedTotalAmount() {
		return advancedEarnedTotalAmount;
	}
	public void setAdvancedEarnedTotalAmount(long advancedEarnedTotalAmount) {
		this.advancedEarnedTotalAmount = advancedEarnedTotalAmount;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCosmeticShopName() {
		return cosmeticShopName;
	}
	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public int getOperateExpense() {
		return operateExpense;
	}
	public void setOperateExpense(int operateExpense) {
		this.operateExpense = operateExpense;
	}
	
	
	public OperationOperatorToolDTO(String operationId, String saleId, String createCardDate, String operationDate,
			String clientName, String cosmeticShopName, String beautifySkinItemName, long createCardTotalAmount,
			long earnedTotalAmount, int totalItemNumber, int restItemNumber, long consumedTotalAmount,
			long consumedEarnedTotalAmount, long advancedEarnedTotalAmount, String operatorName, String toolName,
			int operateExpense, String description) {
		super();
		this.operationId = operationId;
		this.saleId = saleId;
		this.createCardDate = createCardDate;
		this.operationDate = operationDate;
		this.clientName = clientName;
		this.cosmeticShopName = cosmeticShopName;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardTotalAmount = createCardTotalAmount;
		this.earnedTotalAmount = earnedTotalAmount;
		this.totalItemNumber = totalItemNumber;
		this.restItemNumber = restItemNumber;
		this.consumedTotalAmount = consumedTotalAmount;
		this.consumedEarnedTotalAmount = consumedEarnedTotalAmount;
		this.advancedEarnedTotalAmount = advancedEarnedTotalAmount;
		this.operatorName = operatorName;
		this.toolName = toolName;
		this.operateExpense = operateExpense;
		this.description = description;
	}
	@Override
	public String toString() {
		return "OperationOpertatorToolDTO [operationId=" + operationId + ", saleId=" + saleId + ", createCardDate="
				+ createCardDate + ", operationDate=" + operationDate + ", clientName=" + clientName
				+ ", cosmeticShopName=" + cosmeticShopName + ", beautifySkinItemName=" + beautifySkinItemName
				+ ", createCardTotalAmount=" + createCardTotalAmount + ", earnedTotalAmount=" + earnedTotalAmount
				+ ", totalItemNumber=" + totalItemNumber + ", restItemNumber=" + restItemNumber
				+ ", consumedTotalAmount=" + consumedTotalAmount + ", consumedEarnedTotalAmount="
				+ consumedEarnedTotalAmount + ", advancedEarnedTotalAmount=" + advancedEarnedTotalAmount
				+ ", operatorName=" + operatorName + ", toolName=" + toolName + ", operateExpense=" + operateExpense
				+ ", description=" + description + "]";
	}
	
	
	
	
}