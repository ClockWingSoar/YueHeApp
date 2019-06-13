package com.yuehe.app.entity;
import java.io.Serializable;

/**
 * Client entity. @author Soveran Zhong
 */
public class Client implements Serializable{
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6357133516380999542L;
	private String id;
	private String shopId;
	private String name;
	private int age;
	private String gender;
	private String symptom;
	
	// Constructors

	/** default constructor */
	public Client() {
		
	}
	/** full constructor */
	public Client(String id, String shopId, String name, int age, String gender, String symptom) {
		this.id = id;
		this.shopId = shopId;
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
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
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
