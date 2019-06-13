package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Sale entity. @author Soveran Zhong
 */
public class Sale implements Serializable{
	
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -496619121626313549L;

	public Sale(String id, String clientId, String itemId, String sellerId, String itemNumber, float discount,
			float employeePremium, float shopPremium, int receivedAmount, Date createCardDate, String description) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.itemId = itemId;
		this.sellerId = sellerId;
		this.itemNumber = itemNumber;
		this.discount = discount;
		this.employeePremium = employeePremium;
		this.shopPremium = shopPremium;
		this.receivedAmount = receivedAmount;
		this.createCardDate = createCardDate;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */

	private String id;
	private String clientId;
	private String itemId;
	private String sellerId;
	private String itemNumber;
	private float discount;
	private float employeePremium;
	private float shopPremium;
	private int receivedAmount;
	private Date createCardDate;
	private String description;
	
	// Constructors

	/** default constructor */
	public Sale() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getEmployeePremium() {
		return employeePremium;
	}

	public void setEmployeePremium(float employeePremium) {
		this.employeePremium = employeePremium;
	}

	public float getShopPremium() {
		return shopPremium;
	}

	public void setShopPremium(float shopPremium) {
		this.shopPremium = shopPremium;
	}

	public int getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Date getCreateCardDate() {
		return createCardDate;
	}

	public void setCreateCardDate(Date createCardDate) {
		this.createCardDate = createCardDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
