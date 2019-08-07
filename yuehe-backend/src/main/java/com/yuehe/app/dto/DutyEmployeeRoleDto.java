package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of duty.html,using when it
 * needs to join other tables to get employee name by it's employee id, it's
 * different with the entity class {@link com.yuehe.app.entity.Duty} which is
 * aim to symplify the data store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DutyEmployeeRoleDTO {
	private String id;
	private String employeeId;
	
	private String employeeName;
	private String roleName;
	private int welfare;
	private String description;
}
