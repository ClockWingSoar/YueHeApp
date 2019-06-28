package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tool entity. @author Soveran Zhong
 */
@Entity
@Table(name="tool")
public class Tool implements Serializable{
	
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6197995435047062728L;

	public Tool(String id, String name, String major, int price, Date buyDate, String buyFrom, int operateExpense,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.major = major;
		this.price = price;
		this.buyDate = buyDate;
		this.buyFrom = buyFrom;
		this.operateExpense = operateExpense;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */
	@Id
	private String id;
	@OneToMany(targetEntity = Operation.class, mappedBy = "toolId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;
	private String name;
	private String major;
	private int price;
	private Date buyDate;
	private String buyFrom;
	private int operateExpense;
	private String description;
	
	// Constructors

	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	/** default constructor */
	public Tool() {
		
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

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getBuyFrom() {
		return buyFrom;
	}

	public void setBuyFrom(String buyFrom) {
		this.buyFrom = buyFrom;
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

	@Override
	public String toString() {
		return "Tool [id=" + id + ", name=" + name + ", major=" + major + ", price=" + price + ", buyDate=" + buyDate
				+ ", buyFrom=" + buyFrom + ", operateExpense=" + operateExpense + ", description=" + description + "]";
	}
}
