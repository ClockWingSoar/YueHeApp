package com.yuehe.app.entity;
import java.io.Serializable;

/**
 * Organization entity. @author Soveran Zhong
 */
public class Organization implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8370937162321937450L;

	//Fields
	
	public Organization(String employeeId, String roleId, String description) {
		super();
		this.employeeId = employeeId;
		this.roleId = roleId;
		this.description = description;
	}

	/**
	 * 
	 */
	/**
	 * 
	 */

	private String employeeId;
	private String roleId;
	private String description;
	
	// Constructors

	/** default constructor */
	public Organization() {
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
