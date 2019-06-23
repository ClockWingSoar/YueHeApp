package com.yuehe.app.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Client entity. @author Soveran Zhong
 */
@Entity
@Table(name = "cosmeticshop")
public class CosmeticShop implements Serializable{
	
	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 807865871214235889L;
	@Id
	private String id;
	private String name;
	private String owner;
	@Column(name="contact_method")
	private String contactMethod;
	private String location;
	private int size;
	private float discount;
	private String description;
	
	// Constructors

	/** default constructor */
	public CosmeticShop() {
		
	}
	/** full constructor */
	public CosmeticShop(String id, String name, String owner, String contactMethod, String location, int size,
			float discount, String description) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.contactMethod = contactMethod;
		this.location = location;
		this.size = size;
		this.discount = discount;
		this.description = description;
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
