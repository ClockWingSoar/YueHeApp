package com.yuehe.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
 * ClientQuestionare entity. @author Soveran Zhong
 */
@Entity
@Table(name = "client_questionare")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class ClientQuestionare implements Serializable {
	// Fields

	private static final long serialVersionUID = -6357133516380999542L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// @NotBlank(message = "请输入正确的客户ID")
	// @Column(name="client_id")
	// private String clientId;
	// @JsonBackReference
	// @OneToOne(mappedBy = "clientQuestionare")
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", nullable = false)//need to insert data to shop_id column
	@Fetch(FetchMode.JOIN)
	private Client client;

	@Column(name="usual_beautify_item")
	String usualBeautifyItem;
	@Column(name="if_alergic_body")
	String ifAlergicBody;
	@Column(name="if_alergic_skin")
	String ifAlergicSkin;
	@Column(name="alergic_source")
	String alergicSource;
	@Column(name="medicine_name")
	String medicineName;
	@Column(name="if_healthy")
	String ifHealthy;
	@Column(name="if_had_medicine")
	String ifHadMedicine;
	@Column(name="if_pregnant_or_breast_feeding")
	String ifPregnantOrBreastFeeding;
	@Column(name="if_used_whitening_product")
	String ifUsedWhiteningProduct;
	@Column(name="if_sun_exposure")
	String ifSunExposure;
	@Column(name="if_sun_protection")
	String ifSunProtection;
	@Column(name="if_sun_burn_recently")
	String ifSunBurnRecently;
	@Column(name="if_scab_body")
	String ifScabBody;
	@Column(name="eating_situation")
	String eatingSituation;
	@Column(name="sleep_situation")
	String sleepSituation;
	@Column(name="digest_situation")
	String digestSituation;
	@Column(name="incretion_situation")
	String incretionSituation;
	@Column(name="practise_situation")
	String practiseSituation;
	@Column(name="practise_methods")
	String practiseMethods;
	@Column(name="working_env")
	String workingEnv;
	@Column(name="common_used_skin_care_products")
	String commonUsedSkinCareProducts;
}
