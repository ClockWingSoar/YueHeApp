package com.yuehe.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * SaleCardAmountAdjust entity. @author Soveran Zhong
 */
@Entity
@Table(name = "shop_refund_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class ShopRefundRule implements Serializable {
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
	@JoinColumn(name = "shop_id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private CosmeticShop cosmeticShop;

	@Column(name="adjust_action")//could be change discount, could be sale performance reach certain amount, need to give shop premium
	String adjustAction;
	@Column(name="trigger_point")
	String triggerPoint;
	@Column(name="adjust_amount")
	Double adjustAmount;
	@Column(name="adjust_date")
	String adjustDate;
	@Column(name="description")
	String description;
}
