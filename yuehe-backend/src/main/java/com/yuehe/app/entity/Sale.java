package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "client_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Client client;
	
	@Column(name="client_id")
	private String clientId;
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "beautify_skin_item_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private BeautifySkinItem beautifySkinItem;
	
	@Column(name="beautify_skin_item_id")
	private String beautifySkinItemId;
	 @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "seller_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	
	@OneToMany(targetEntity = Operation.class, mappedBy = "saleId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;
	
	

	@Column(name="seller_id")
	private String sellerId;
	
	@Column(name="item_number")
	private int itemNumber;
	
	@Column(name="create_card_total_amount")
	private long createCardTotalAmount;
	
	@Column(name="received_amount")
	private long receivedAmount;
	
	@Column(name="employee_premium")
	private float employeePremium;
	
	@Column(name="shop_premium")
	private float shopPremium;
	
	
	@Column(name="create_card_date")
	private String createCardDate; 
	
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

	public String getBeautifySkinItemId() {
		return beautifySkinItemId;
	}

	public void setBeautifySkinItemId(String beautifySkinItemId) {
		this.beautifySkinItemId = beautifySkinItemId;
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

	public float getCreateCardTotalAmount() {
		return createCardTotalAmount;
	}

	public void setCreateCardTotalAmount(long createCardTotalAmount) {
		this.createCardTotalAmount = createCardTotalAmount;
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

	public long getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getCreateCardDate() {
		return createCardDate;
	}

	public void setCreateCardDate(String createCardDate) {
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
	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public void setReceivedAmount(long receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Sale(String id, String clientId, String beautifySkinItemId, String sellerId, int itemNumber, long createCardTotalAmount,
			float employeePremium, float shopPremium, int receivedAmount, String createCardDate, String description) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.beautifySkinItemId = beautifySkinItemId;
		this.sellerId = sellerId;
		this.itemNumber = itemNumber;
		this.createCardTotalAmount = createCardTotalAmount;
		this.employeePremium = employeePremium;
		this.shopPremium = shopPremium;
		this.receivedAmount = receivedAmount;
		this.createCardDate = createCardDate;
		this.description = description;
	}

}
