package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Employee entity. @author Soveran Zhong
 */
@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
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
    /**
	 * use it to get the biggest id column of table employee 
	 * @param id
	 */
	public Employee(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", birthday=" + birthday
				+ ", description=" + description + ", resigned=" + resigned + "]";
	}	
	public static Comparator<Employee> idComparator = Comparator.comparing(Employee::getId);
}
