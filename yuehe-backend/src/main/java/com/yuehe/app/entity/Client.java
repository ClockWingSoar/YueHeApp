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
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Client entity. @author Soveran Zhong
 */
@Entity
@Table(name = "client")
public class Client implements Serializable{
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6357133516380999542L;
	@Id
	private String id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "shop_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private CosmeticShop cosmeticShop;

	@Column(name="shop_id")
	private String shopId;
	
	private String name;
	private int age;
	private String gender;
	private String symptom;
	 @JsonManagedReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "clientId", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Sale> sales;
	// Constructors

	@Override
	public String toString() {
		return "Client [id=" + id + ", cosmeticShop=" + cosmeticShop + ", shopId=" + shopId + ", name=" + name
				+ ", age=" + age + ", gender=" + gender + ", symptom=" + symptom + "]";
	}
	/** default constructor */
	public Client() {
		
	}
	/** use it for filtering to filter client for operationSummary.html*/
	public Client(String id,String name) {
		this.id = id;
		this.name = name;
		
	}
	/** full constructor */
	public Client(String id, CosmeticShop cosmeticShop, String name, int age, String gender, String symptom) {
		this.id = id;
		this.cosmeticShop = cosmeticShop;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.symptom = symptom;
	}
	
	// Property accessors
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CosmeticShop getCosmeticShop() {
		return cosmeticShop;
	}
	public void setCosmeticShop(CosmeticShop cosmeticShop) {
		this.cosmeticShop = cosmeticShop;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Set<Sale> getSales() {
		return sales;
	}
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
