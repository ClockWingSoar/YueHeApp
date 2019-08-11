package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Role entity. @author Soveran Zhong
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class Role implements Serializable {

	// Fields
	private static final long serialVersionUID = 7280910034468399593L;
	@Id
	private String id;
	@JsonManagedReference
	@OneToMany(targetEntity = Duty.class, mappedBy = "role", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Duty> duties;

	private String name;
	private String responsibility;
	private String description;

    /**
	 * use it to get the biggest id column of table role 
	 * @param id
	 */
	public Role(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", responsibility=" + responsibility + ", description="
				+ description + "]";
	}
	public static Comparator<Role> idComparator = Comparator.comparing(Role::getId);

}
