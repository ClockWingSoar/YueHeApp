package com.yuehe.app.entity;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Operation entity. @author Soveran Zhong
 */
@Entity
@Table(name = "operation")
public class Operation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7938239506561236760L;

	//Fields
	
	public Operation(String id, String saleId, String operatorId, String toolId, String operationDate,
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
	@Id
	private String id;
	@Column(name="sale_id")
	private String saleId;
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) 
	@JoinColumn(name = "sale_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Sale sale;
	
	
	
	
	@Column(name="operator_id")
	private String operatorId;
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) 
	@JoinColumn(name = "operator_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	
	
	@Column(name="tool_id")
	private String toolId;
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) 
	@JoinColumn(name = "tool_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Tool tool;
	
	
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	@Column(name="operation_date")
	private String operationDate;
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

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Operation [id=" + id + ", saleId=" + saleId + ", operatorId=" + operatorId + ", toolId=" + toolId
				+ ", operationDate=" + operationDate + ", description=" + description + "]";
	}
	
}
