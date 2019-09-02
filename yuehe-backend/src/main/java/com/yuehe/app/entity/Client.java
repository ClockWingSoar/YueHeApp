package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Client entity. @author Soveran Zhong
 */
@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
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

	// @JsonBackReference
	// @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
    // @JoinColumn(name = "questionare_client")
	// private ClientQuestionare clientQuestionare;
	@JsonManagedReference
	@OneToOne(targetEntity = ClientQuestionare.class, mappedBy = "client", orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)//to fix the Spring Boot Fail-safe cleanup (collections) issue,this can happen if it trys to retreive many data
	private ClientQuestionare clientQuestionare;

	private String name;
	private int age;
	private String gender;
	private String symptom;
	@JsonManagedReference
	@OneToMany(targetEntity = Sale.class, mappedBy = "client", orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<Sale> sales;//this will cause multi query to DB if you search for client with this full arguments Client entity

	
    /**
	 * use it to get the biggest id column of table client 
	 * @param id
	 */
	public Client(String id){
		this.id = id;
	}
	/** use it for filtering to filter client for operationSummary.html */
	public Client(String id, String name,CosmeticShop cosmeticShop) {
		this.id = id;
		this.name = name;
		this.cosmeticShop = cosmeticShop;//get cosmeticshop discount to do the auto-caculation for received earned amount

	}
	/** use it to intialize client basic info for clientQuestionareNewItem.html */
	public Client(String id, String name, String gender, int age) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;

	}
	/** use it to test client basic info for clientQuestionareNewItem.html */
	public Client(String id, String name, CosmeticShop cosmeticShop, String gender, int age) {
		this.id = id;
		this.name = name;
		this.cosmeticShop = cosmeticShop;
		this.gender = gender;
		this.age = age;

	}
	@Override
	public String toString() {
		return "Client [id=" + id + ", cosmeticShopName=" + Optional.ofNullable(cosmeticShop).orElse(new CosmeticShop()).getName() + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", symptom=" + symptom + "]";
	}
	public static Comparator<Client> idComparator = Comparator.comparing(Client::getId);
}
