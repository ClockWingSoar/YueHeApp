package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to get the cosmeticshop id and anme to be as a
 * filter for a cosmeticshop list use it in view like operationSummary.html
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleBeautifySkinItemForFilterDTO {
	private String saleId;
	private String beautifySkinItemName;
	private String createCardDate;
	

}
