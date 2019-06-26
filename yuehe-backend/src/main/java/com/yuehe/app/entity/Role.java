package com.yuehe.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Role entity. @author Soveran Zhong
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7280910034468399593L;

	public Role(String id, String name, String responsibility, String description) {
		super();
		this.id = id;
		this.name = name;
		this.responsibility = responsibility;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */
	@Id
	private String id;
	private String name;
	private String responsibility;
	private String description;

	// Constructors

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", responsibility=" + responsibility + ", description="
				+ description + "]";
	}

	/** default constructor */
	public Role() {

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

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
