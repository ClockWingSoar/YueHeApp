package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Employee entity. @author Soveran Zhong
 */
@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
	private static final long serialVersionUID = 5130639635185289129L;

	// Fields
	@Id
	@NotBlank(message = "请输入正确的员工ID")
	private String id;
	@JsonManagedReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "employee", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Sale> sales;
	@JsonManagedReference
	@OneToMany(targetEntity = Duty.class, mappedBy = "employee", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Duty> duties;
	@JsonManagedReference
	@OneToMany(targetEntity = Operation.class, mappedBy = "employee", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;

	private String name;
	private int salary;
	private Date birthday;
	private String description;
	private String resigned;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", birthday=" + birthday
				+ ", description=" + description + ", resigned=" + resigned + "]";
	}

}
