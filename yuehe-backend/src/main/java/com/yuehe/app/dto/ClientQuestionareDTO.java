package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClientQuestionare DTO for client questionare edit page. @author Soveran Zhong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode 
public class ClientQuestionareDTO {
	// Fields

	/**
	 * 
	 */
	private String clientId;
	private String clientGender;
	private String clientName;
	private int clientAge;
	private String usualBeautifyItem;
	private String ifAlergicBody;
	private String ifAlergicSkin;
	private String[] alergicSources;
	private String medicineName;
	private String ifHealthy;
	private String ifHadMedicine;
	private String ifPregnantOrBreastFeeding;
	private String ifUsedWhiteningProduct;
	private String ifSunExposure;
	private String ifSunProtection;
	private String ifSunBurnRecently;
	private String ifScabBody;
	private String[] eatingSituations;
	private String[] sleepSituations;
	private String[] digestSituations;
	private String[] incretionSituations;
	private String practiseSituation;
	private String practiseMethods;
	private String[] workingEnvs;
	private String commonUsedSkinCareProducts;
}
