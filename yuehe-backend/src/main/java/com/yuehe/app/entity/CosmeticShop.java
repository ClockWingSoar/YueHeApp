package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * Client entity. @author Soveran Zhong
 */
@Entity
@Table(name = "cosmeticshop")
public class CosmeticShop implements Serializable{
	
	//Fields
	
	@Override
	public String toString() {
		return "CosmeticShop [id=" + id + ", name=" + name + ", owner=" + owner + ", contactMethod=" + contactMethod
				+ ", location=" + location + ", size=" + size + ", discount=" + discount + ", shopPremium=" + shopPremium +", description="
				+ description+ "]"; //+ ", clients=" + clients + "]";
	}
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
	@Column(name="shop_premium")
	private float shopPremium;//例如肤语港，总业绩超过50万时，需要返还5万给店家
	private String description;
	@JsonManagedReference
	@OneToMany(targetEntity = Client.class, mappedBy = "shopId", orphanRemoval = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Client> clients;
	
	// Constructors

	/** default constructor */
	public CosmeticShop() {
		
	}
	/** use it for filtering to filter client for operationSummary.html*/
	public CosmeticShop(String id,String name) {
		this.id = id;
		this.name = name;
		
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
	public Set<Client> getClients() {
		return clients;
	}
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	public float getShopPremium() {
		return shopPremium;
	}
	public void setShopPremium(float shopPremium) {
		this.shopPremium = shopPremium;
	}

	
}
