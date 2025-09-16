package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Operation entity. @author Soveran Zhong
 */
@Entity
@Table(name = "operation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class Operation implements Serializable {
	private static final long serialVersionUID = -7938239506561236760L;

	// Fields
	@Id
	private String id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Sale sale;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tool_id", nullable = false)
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
