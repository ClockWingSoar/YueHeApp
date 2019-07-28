package com.yuehe.app.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sale entity. @author Soveran Zhong
 */
@Entity
@Table(name = "sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Serializable {

	// Fields

	private static final long serialVersionUID = -496619121626313549L;
	@Id
	private String id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "client_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Client client;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "beautify_skin_item_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private BeautifySkinItem beautifySkinItem;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "seller_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Employee employee;

	@OneToMany(targetEntity = Operation.class, mappedBy = "sale", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Operation> operations;

	@Column(name = "item_number")
	@NotNull(message = "{sale.itemNumber.notNull}")
	@Range(min = 1, max = 36, message = "{sale.itemNumber.range}")
	private Integer itemNumber;

	@Column(name = "create_card_total_amount")
	@NotNull(message = "请输入开卡总金额")
	private Long createCardTotalAmount;

	@Column(name = "received_amount")
	@PositiveOrZero
	@NotNull(message = "请输入实际回款数额")
	private Long receivedAmount;

	@Column(name = "received_earned_amount")
	@PositiveOrZero
	@NotNull(message = "请输入公司收到回款数额")
	private Long receivedEarnedAmount;

	@Column(name = "employee_premium")
	@PositiveOrZero
	private Float employeePremium;

	@Column(name = "shop_premium")
	@PositiveOrZero
	private Float shopPremium;

	@Column(name = "create_card_date")
	@NotBlank(message = "请选择开卡日期")
	private String createCardDate;
	@Size(min = 0, max = 250)
	private String description;
	/**
	 * use it to get the biggest id column of table sale 
	 * @param id
	 */
	public Sale(String id){
		this.id = id;
	}

	@Override
	public String toString() {
		return "Sale [beautifySkinItemName=" + beautifySkinItem.getName() + ", clientName=" + client.getName()
				+ ", createCardDate=" + createCardDate + ", createCardTotalAmount=" + createCardTotalAmount
				+ ", description=" + description + ", sellerName=" + employee.getName() + ", employeePremium="
				+ employeePremium + ", id=" + id + ", itemNumber=" + itemNumber + ", operationNumber="
				+ Optional.ofNullable(operations).orElse(new HashSet<Operation>()).size() + ", receivedAmount="
				+ receivedAmount + ", receivedEarnedAmount=" + receivedEarnedAmount + ", shopPremium=" + shopPremium
				+ "]";
	}
	public static Comparator<Sale> idComparator = Comparator.comparing(Sale::getId);

}
