package com.yuehe.app.entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BeautifySkinItem entity. @author Soveran Zhong
 */
@Entity
@Table(name = "beautifyskinitem")
public class BeautifySkinItem implements Serializable{
	

	//Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6638046999127630014L;
	 @Id
//	 @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String name;
	private int price;
	private String description;
	
	// Constructors

	/** default constructor */
	public BeautifySkinItem() {
		
	}
	public BeautifySkinItem(String id, String name, int price, String description) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "BeautifySkinItem [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ "]";
	}


	
}
