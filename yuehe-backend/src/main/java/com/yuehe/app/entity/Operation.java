package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Operation entity. @author Soveran Zhong
 */
@Entity
@Table(name = "operation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation implements Serializable {
	private static final long serialVersionUID = -7938239506561236760L;

	// Fields
	@Id
	private String id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Sale sale;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tool_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Tool tool;

	@Column(name = "operation_date")
	private String operationDate;
	private String description;
    /**
	 * use it to get the biggest id column of table operation 
	 * @param id
	 */
	public Operation(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "Operation [id=" + id + ", saleId=" + sale.getId() + ", operatorName=" + employee.getName()
				+ ", toolName=" + tool.getName() + ", operationDate=" + operationDate + ", description=" + description
				+ "]";
	}
	public static Comparator<Operation> idComparator = Comparator.comparing(Operation::getId);
}
