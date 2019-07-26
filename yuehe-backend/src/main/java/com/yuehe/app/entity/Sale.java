package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sale entity. @author Soveran Zhong
 */
@Entity
@Table(name="sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "client_id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private Client client;
	
	// @Column(name="client_id")
	// @NotBlank(message = "请输入客户姓名")
	// private String clientId;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "beautify_skin_item_id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private BeautifySkinItem beautifySkinItem;
	

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY,  optional = false) 
	@JoinColumn(name = "seller_id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	
	@OneToMany(targetEntity = Operation.class, mappedBy = "saleId", orphanRemoval = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Operation> operations;
	
	
	@Column(name="item_number")
	@NotNull(message = "{sale.itemNumber.notNull}")
	@Range(min = 1, max = 36, message = "{sale.itemNumber.range}")
	private Integer itemNumber;
	
	@Column(name="create_card_total_amount")
	@NotNull(message = "请输入开卡总金额")
	private Long createCardTotalAmount;
	
	@Column(name="received_amount")
	@PositiveOrZero
	@NotNull(message = "请输入实际回款数额")
	private Long receivedAmount;
	
	@Column(name="received_earned_amount")
	@PositiveOrZero
	@NotNull(message = "请输入公司收到回款数额")
	private Long receivedEarnedAmount;
	
	@Column(name="employee_premium")
	@PositiveOrZero
	private Float employeePremium;
	
	@Column(name="shop_premium")
	@PositiveOrZero
	private Float shopPremium;
	
	
	@Column(name="create_card_date")
	@NotBlank(message = "请选择开卡日期")
	private String createCardDate; 
	@Size(min=0,max=250)
	private String description;
	
	// Constructors

	// /** default constructor */
	// public Sale() {
		
	// }

	// public String getId() {
	// 	return id;
	// }

	// public void setId(String id) {
	// 	this.id = id;
	// }


	// public Integer getItemNumber() {
	// 	return itemNumber;
	// }

	// public void setItemNumber(Integer itemNumber) {
	// 	this.itemNumber = itemNumber;
	// }

	// public Long getCreateCardTotalAmount() {
	// 	return createCardTotalAmount;
	// }

	// public void setCreateCardTotalAmount(Long createCardTotalAmount) {
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// }

	// public Float getEmployeePremium() {
	// 	return employeePremium;
	// }

	// public void setEmployeePremium(Float employeePremium) {
	// 	this.employeePremium = employeePremium;
	// }

	// public Float getShopPremium() {
	// 	return shopPremium;
	// }

	// public void setShopPremium(Float shopPremium) {
	// 	this.shopPremium = shopPremium;
	// }

	// public Long getReceivedAmount() {
	// 	return receivedAmount;
	// }

	// public void setReceivedAmount(Long receivedAmount) {
	// 	this.receivedAmount = receivedAmount;
	// }

	// public Long getReceivedEarnedAmount() {
	// 	return receivedEarnedAmount;
	// }

	// public void setReceivedEarnedAmount(Long receivedEarnedAmount) {
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// }

	// public String getCreateCardDate() {
	// 	return createCardDate;
	// }

	// public void setCreateCardDate(String createCardDate) {
	// 	this.createCardDate = createCardDate;
	// }

	// public String getDescription() {
	// 	return description;
	// }

	// public void setDescription(String description) {
	// 	this.description = description;
	// }


	// public Client getClient() {
	// 	return client;
	// }

	// public void setClient(Client client) {
	// 	this.client = client;
	// }

	// public BeautifySkinItem getBeautifySkinItem() {
	// 	return beautifySkinItem;
	// }

	// public void setBeautifySkinItem(BeautifySkinItem beautifySkinItem) {
	// 	this.beautifySkinItem = beautifySkinItem;
	// }

	// public Employee getEmployee() {
	// 	return employee;
	// }

	// public void setEmployee(Employee employee) {
	// 	this.employee = employee;
	// }
	// public Set<Operation> getOperations() {
	// 	return operations;
	// }

	// public void setOperations(Set<Operation> operations) {
	// 	this.operations = operations;
	// }

	// public Sale(String id, Client client, BeautifySkinItem beautifySkinItem, Employee employee,
	// 		Set<Operation> operations, @NotNull(message = "请输入开卡次数") int itemNumber,
	// 		@NotNull(message = "请输入开卡总金额") long createCardTotalAmount,
	// 		@NotNull(message = "请输入实际回款数额") long receivedAmount,
	// 		@NotNull(message = "请输入公司收到回款数额") long receivedEarnedAmount, @Min(0) float employeePremium,
	// 		@Min(0) float shopPremium, @NotBlank(message = "请选择开卡日期") String createCardDate,
	// 		@Size(min = 0, max = 50) String description) {
	// 	this.id = id;
	// 	this.client = client;
	// 	this.beautifySkinItem = beautifySkinItem;
	// 	this.employee = employee;
	// 	this.operations = operations;
	// 	this.itemNumber = itemNumber;
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// 	this.receivedAmount = receivedAmount;
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// 	this.employeePremium = employeePremium;
	// 	this.shopPremium = shopPremium;
	// 	this.createCardDate = createCardDate;
	// 	this.description = description;
	// }

	@Override
	public String toString() {
		return "Sale [beautifySkinItemName=" + beautifySkinItem.getName() + ", clientName=" + client.getName() + ", createCardDate="
				+ createCardDate + ", createCardTotalAmount=" + createCardTotalAmount + ", description=" + description
				+ ", sellerName=" + employee.getName() + ", employeePremium=" + employeePremium + ", id=" + id + ", itemNumber="
				+ itemNumber + ", operationNumber=" + Optional.ofNullable(operations).orElse(new HashSet<Operation>()).size() + ", receivedAmount=" + receivedAmount
				+ ", receivedEarnedAmount=" + receivedEarnedAmount + ", shopPremium=" + shopPremium + "]";
	}

	



}
