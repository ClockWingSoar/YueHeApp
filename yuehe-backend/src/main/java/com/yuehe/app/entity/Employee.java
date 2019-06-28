package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee entity. @author Soveran Zhong
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable{
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", birthday=" + birthday
				+ ", description=" + description + ", resigned=" + resigned + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5130639635185289129L;

	public Employee(String id, String name, int salary, Date birthday, String description, String resigned) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.birthday = birthday;
		this.description = description;
		this.resigned = resigned;
	}

	//Fields
	
	/**
	 * 
	 */
	@Id
	private String id;
	
	@OneToMany(targetEntity = Sale.class, mappedBy = "sellerId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Sale> sales;

	@OneToMany(targetEntity = Duty.class, mappedBy = "employeeId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Duty> duties;
	
	@OneToMany(targetEntity = Operation.class, mappedBy = "operatorId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;
	
	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Duty> getDuties() {
		return duties;
	}

	public void setDuties(Set<Duty> duties) {
		this.duties = duties;
	}

	private String name;
	private int salary;
	private Date birthday;
	private String description;
	private String resigned;
	
	// Constructors

	/** default constructor */
	public Employee() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDescription() {
		return description;
	} 

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResigned() {
		return resigned;
	}

	public void setResigned(String resigned) {
		this.resigned = resigned;
	}
	public Set<Sale> getSales() {
		return sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}


	
}
