package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to get the cosmeticshop id and anme to be as a filter for a cosmeticshop list
 * use it in view like operationSummary.html
 * @author YIXIANGZhong
 * @since 1.0
 */
public class SaleBeautifySkinItemForFilterDTO {
	public SaleBeautifySkinItemForFilterDTO() {
		//super();
	}
	private String saleId;
	private String beautifySkinItemName;
	private String createCardDate;
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getBeautifySkinItemName() {
		return beautifySkinItemName;
	}
	public void setBeautifySkinItemName(String beautifySkinItemName) {
		this.beautifySkinItemName = beautifySkinItemName;
	}
	public String getCreateCardDate() {
		return createCardDate;
	}
	public void setCreateCardDate(String createCardDate) {
		this.createCardDate = createCardDate;
	}
	public SaleBeautifySkinItemForFilterDTO(String saleId, String beautifySkinItemName, String createCardDate) {
		super();
		this.saleId = saleId;
		this.beautifySkinItemName = beautifySkinItemName;
		this.createCardDate = createCardDate;
	}
	@Override
	public String toString() {
		return "SaleBeautifySkinItemForFilterDTO [saleId=" + saleId + ", beautifySkinItemName=" + beautifySkinItemName
				+ ", createCardDate=" + createCardDate + "]";
	}
	

}
