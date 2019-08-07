package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDetailDTO {
	private String saleId;//销售ID
	private String operationId;//操作ID
	private String operationDate;//操作日期
	private String operatorName;//操作人
	private String toolName;//操作仪器
	private int operateExpense;//操作费用
	private String description;//备注
	
}