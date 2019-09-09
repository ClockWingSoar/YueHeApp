package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to get all the sale records regardless create or adjust(add amount or return amount)
 * for sale performance summary.html
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateOrAdjustDTO {
	private Long saleAdjustId;
	private String saleId;
	

}
