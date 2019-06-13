package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Operation entity. @author Soveran Zhong
 */
public class Operation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7938239506561236760L;

	//Fields
	
	public Operation(String id, String saleId, String operatorId, String toolId, Date operationDate,
			String description) {
		super();
		this.id = id;
		this.saleId = saleId;
		this.operatorId = operatorId;
		this.toolId = toolId;
		this.operationDate = operationDate;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */

	private String id;
	private String saleId;
	private String operatorId;
	private String toolId;
	private Date operationDate;
	private String description;
	
	// Constructors

	/** default constructor */
	public Operation() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
