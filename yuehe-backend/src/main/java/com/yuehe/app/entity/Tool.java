package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tool entity. @author Soveran Zhong
 */
@Entity
@Table(name = "tool")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tool implements Serializable {

	// Fields
	private static final long serialVersionUID = -6197995435047062728L;
	@Id
	private String id;
	@JsonManagedReference
	@OneToMany(targetEntity = Operation.class, mappedBy = "tool", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;
	private String name;
	private String major;
	private int price;
	private Date buyDate;
	private String buyFrom;
	private int operateExpense;
	private String description;
    /**
	 * use it to get the biggest id column of table tool 
	 * @param id
	 */
	public Tool(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "Tool [id=" + id + ", name=" + name + ", operationNumber="
				+ Optional.ofNullable(operations).orElse(new HashSet<Operation>()).size() + ", major=" + major
				+ ", price=" + price + ", buyDate=" + buyDate + ", buyFrom=" + buyFrom + ", operateExpense="
				+ operateExpense + ", description=" + description + "]";
	}
	public static Comparator<Tool> idComparator = Comparator.comparing(Tool::getId);
}
