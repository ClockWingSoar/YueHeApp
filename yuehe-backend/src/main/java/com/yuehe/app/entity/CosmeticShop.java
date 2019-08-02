package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "cosmeticshop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CosmeticShop implements Serializable {

	// Fields
	private static final long serialVersionUID = 807865871214235889L;
	@Id
	private String id;
	private String name;
	private String owner;
	@Column(name = "contact_method")
	private String contactMethod;
	private String location;
	private int size;
	private float discount;
	@Column(name = "shop_premium")
	private float shopPremium;// 例如肤语港，总业绩超过50万时，需要返还5万给店家
	private String description;
	@JsonManagedReference
	@OneToMany(targetEntity = Client.class, mappedBy = "cosmeticShop", orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)//to fix the Spring Boot Fail-safe cleanup (collections) issue,this can happen if it trys to retreive many data
	// see https://stackoverflow.com/questions/53540056/what-casuses-spring-boot-fail-safe-cleanup-collections-to-occur
	private Set<Client> clients;

	/** use it for filtering to filter client for operationSummary.html */
	public CosmeticShop(String id, String name) {
		this.id = id;
		this.name = name;

	}
    /**
	 * use it to get the biggest id column of table cosmeticShop 
	 * @param id
	 */
	public CosmeticShop(String id){
		this.id = id;
	}
	@Override
	public String toString() {
		return "CosmeticShop [id=" + id + ", name=" + name + ", owner=" + owner + ", contactMethod=" + contactMethod
				+ ", location=" + location + ", size=" + size + ", discount=" + discount + ", shopPremium="
				+ shopPremium + ", description=" + description + "]";//+ ", clientNumber="
				// + Optional.ofNullable(clients).orElse(new HashSet<Client>()).size() + "]";
	}
	public static Comparator<CosmeticShop> idComparator = Comparator.comparing(CosmeticShop::getId);
}
