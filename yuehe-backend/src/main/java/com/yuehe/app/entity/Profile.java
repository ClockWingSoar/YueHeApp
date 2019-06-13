package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Profile entity. @author Soveran Zhong
 */
public class Profile implements Serializable{
	
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8255769962125087079L;

	public Profile(String saleId, int restCardAmount, Date createProfileDate, String description) {
		super();
		this.saleId = saleId;
		this.restCardAmount = restCardAmount;
		this.createProfileDate = createProfileDate;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */

	private String saleId;
	private int restCardAmount;
	private Date createProfileDate;
	private String description;
	
	// Constructors

	/** default constructor */
	public Profile() {
		
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public int getRestCardAmount() {
		return restCardAmount;
	}

	public void setRestCardAmount(int restCardAmount) {
		this.restCardAmount = restCardAmount;
	}

	public Date getCreateProfileDate() {
		return createProfileDate;
	}

	public void setCreateProfileDate(Date createProfileDate) {
		this.createProfileDate = createProfileDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
