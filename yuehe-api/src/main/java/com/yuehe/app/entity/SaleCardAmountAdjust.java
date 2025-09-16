package com.yuehe.app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SaleCardAmountAdjust entity. @author Soveran Zhong
 */
@Entity
@Table(name = "sale_card_amount_adjust")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class SaleCardAmountAdjust implements Serializable {
	// Fields

	private static final long serialVersionUID = -6357133516380999542L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sale_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Sale sale;


	@Column(name="adjust_action")
	String adjustAction;
	@Column(name="adjust_amount")
	Long adjustAmount;
	@Column(name="employee_premium_adjust_amount")
	Long employeePremiumAdjustAmount;
	@Column(name="shop_premium_adjust_amount")
	Long shopPremiumAdjustAmount;
	@Column(name="adjust_date")
	String adjustDate;
	@Column(name="description")
	String description;
}
