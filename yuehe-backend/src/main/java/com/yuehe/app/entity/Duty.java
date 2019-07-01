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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Organization entity. @author Soveran Zhong
 */
@Entity
@Table(name="duty")
public class Duty implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8370937162321937450L;

	//Fields
	@Id
	private String id;
	@Column(name="employee_id")
	private String employeeId;
	@Column(name="role_id")
	private String roleId;
	private int welfare;
	private String description;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "employee_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "role_id",insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Role role;
	
	// Constructors

	/** default constructor */
	public Duty() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getWelfare() {
		return welfare;
	}

	public void setWelfare(int welfare) {
		this.welfare = welfare;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Duty(String id, String employeeId, String roleId, int welfare, String description) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.roleId = roleId;
		this.welfare = welfare;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Duty [id=" + id + ", employeeId=" + employeeId + ", roleId=" + roleId + ", welfare=" + welfare
				+ ", description=" + description + "]";
	}


}
