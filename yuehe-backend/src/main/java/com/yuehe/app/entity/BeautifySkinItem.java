package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * BeautifySkinItem entity. @author Soveran Zhong
 */
@Entity
@Table(name = "beautifyskinitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class BeautifySkinItem implements Serializable {

	// Fields
	private static final long serialVersionUID = -6638046999127630014L;
	@Id
	@NotBlank(message = "请输入正确的美肤套餐ID")
	private String id;
	@JsonManagedReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "beautifySkinItem", orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<Sale> sales;
	private String name;

	private int price;
	private String description;
    /**
	 * use it to get the biggest id column of table beautifySkinItem 
	 * @param id
	 */
	public BeautifySkinItem(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "BeautifySkinItem [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ "]";
	}
	public static Comparator<BeautifySkinItem> idComparator = Comparator.comparing(BeautifySkinItem::getId);
}
