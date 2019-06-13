package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Tool entity. @author Soveran Zhong
 */
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

	private String id;
	private String name;
	private String major;
	private int price;
	private Date buyDate;
	private String buyFrom;
	private int operateExpense;
	private String description;
	
	// Constructors

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

	
}
