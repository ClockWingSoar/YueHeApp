package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Sale entity. @author Soveran Zhong
 */
@Entity
@Table(name="sale")
public class Sale implements Serializable{
	
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -496619121626313549L;

	/**
	 * 
	 */
	@Id	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "client_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Client client;
	
	@Column(name="client_id")
	private String clientId;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "item_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private BeautifySkinItem beautifySkinItem;
	
	@Column(name="item_id")
	private String itemId;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "seller_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	

	@Column(name="seller_id")
	private String sellerId;
	
	@Column(name="item_number")
	private int itemNumber;
	
	private float discount;
	
	@Column(name="employee_premium")
	private float employeePremium;
	
	@Column(name="shop_premium")
	private float shopPremium;
	
	@Column(name="received_amount")
	private int receivedAmount;
	
	@Column(name="create_card_date")
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

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
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


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BeautifySkinItem getBeautifySkinItem() {
		return beautifySkinItem;
	}

	public void setBeautifySkinItem(BeautifySkinItem beautifySkinItem) {
		this.beautifySkinItem = beautifySkinItem;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Sale(String id, String clientId, String itemId, String sellerId, int itemNumber, float discount,
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

}
