package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Duty entity. @author Soveran Zhong
 */
@Entity
@Table(name = "duty")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class Duty implements Serializable {
	private static final long serialVersionUID = 8370937162321937450L;

	// Fields
	@Id
	private String id;
	private int welfare;
	private String description;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Role role;
    /**
	 * use it to get the biggest id column of table duty 
	 * @param id
	 */
	public Duty(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "Duty [id=" + id + ", employeeName=" + employee.getName() + ", roleName=" + role.getName() + ", welfare="
				+ welfare + ", description=" + description + "]";
	}
	public static Comparator<Duty> idComparator = Comparator.comparing(Duty::getId);
}
