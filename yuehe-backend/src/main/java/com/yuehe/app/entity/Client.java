package com.yuehe.app.entity;
import java.io.Serializable;

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
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "shop_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private CosmeticShop cosmeticShop;
	@Column(name="shop_id")
	private String shopId;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	private String name;
	private int age;
	private String gender;
	private String symptom;
	
	// Constructors

	@Override
	public String toString() {
		return "Client [id=" + id + ", cosmeticShop=" + cosmeticShop + ", shopId=" + shopId + ", name=" + name
				+ ", age=" + age + ", gender=" + gender + ", symptom=" + symptom + "]";
	}
	/** default constructor */
	public Client() {
		
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
	
	
	
}
