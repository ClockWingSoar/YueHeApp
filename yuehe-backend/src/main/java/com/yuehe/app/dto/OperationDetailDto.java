package com.yuehe.app.dto;

import java.util.Date;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class OperationDetailDto {
	public OperationDetailDto() {
		//super();
	}
	private String operationId;//操作ID
	private String operationDate;//操作日期
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
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OperationDetailDto(String operationId, String operationDate, String operatorName, String toolName,
			int operateExpense, String description) {
		//super();
		this.operationId = operationId;
		this.operationDate = operationDate;
		this.operatorName = operatorName;
		this.toolName = toolName;
		this.operateExpense = operateExpense;
		this.description = description;
	}
	@Override
	public String toString() {
		return "OperationDetailDto [operationId=" + operationId + ", operationDate=" + operationDate + ", operatorName="
				+ operatorName + ", toolName=" + toolName + ", operateExpense=" + operateExpense + ", description="
				+ description + "]";
	}
	
	
}