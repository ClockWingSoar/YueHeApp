package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client entity. @author Soveran Zhong
 */
@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6357133516380999542L;
	@Id
	@NotBlank(message = "请输入正确的客户ID")
	private String id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id", nullable = false)//need to insert data to shop_id column
	@Fetch(FetchMode.JOIN)
	private CosmeticShop cosmeticShop;

	private String name;
	private int age;
	private String gender;
	private String symptom;
	@JsonManagedReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "client", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Sale> sales;

	@Override
	public String toString() {
		return "Client [id=" + id + ", cosmeticShopName=" + Optional.ofNullable(cosmeticShop).orElse(new CosmeticShop()).getName() + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", symptom=" + symptom + "]";
	}
    /**
	 * use it to get the biggest id column of table client 
	 * @param id
	 */
	public Client(String id){
		this.id = id;
	}
	/** use it for filtering to filter client for operationSummary.html */
	public Client(String id, String name) {
		this.id = id;
		this.name = name;

	}
	public static Comparator<Client> idComparator = Comparator.comparing(Client::getId);
}
