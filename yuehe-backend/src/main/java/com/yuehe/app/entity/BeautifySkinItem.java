package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BeautifySkinItem entity. @author Soveran Zhong
 */
@Entity
@Table(name = "beautifyskinitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeautifySkinItem implements Serializable {

	// Fields
	private static final long serialVersionUID = -6638046999127630014L;
	@Id
	@NotBlank(message = "请输入正确的美肤套餐ID")
	private String id;
	@JsonBackReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "beautifySkinItem", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Sale> sales;
	private String name;

	private int price;
	private String description;

	@Override
	public String toString() {
		return "BeautifySkinItem [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", SaleNumber=" + Optional.ofNullable(sales).orElse(new HashSet<Sale>()).size() + "]";
	}

}
