package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of duty.html,using when it needs to join other tables
 * to get employee name by it's employee id, it's different with the entity
 * class {@link com.yuehe.app.entity.Duty} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class DutyEmployeeRoleDTO {
	public DutyEmployeeRoleDTO() {
		//super();
	}
	private String id;
	private String employeeId;
	
	private String employeeName;
	private String roleName;
	private int welfare;
	private String description;
	public String getId() {
		return id;
	}
	
	public int getWelfare() {
		return welfare;
	}
	public void setWelfare(int welfare) {
		this.welfare = welfare;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public DutyEmployeeRoleDTO(String id, String employeeId, String employeeName, String roleName, int welfare,
			String description) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.roleName = roleName;
		this.welfare = welfare;
		this.description = description;
	}

	@Override
	public String toString() {
		return "DutyEmployeeRoleDTO [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", roleName=" + roleName + ", welfare=" + welfare + ", description=" + description + "]";
	}
}
