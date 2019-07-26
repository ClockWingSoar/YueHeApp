package com.yuehe.app.dto;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of sale.html,using when it
 * needs to join other tables to get seller name by it's employee id, it's
 * different with the entity class {@link com.yuehe.app.entity.Client} which is
 * aim to symplify the data store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleClientItemSellerDTO implements Comparable<SaleClientItemSellerDTO>{
	// public SaleClientItemSellerDTO() {
	// 	//super();
	// }
	private String saleId;
	private String clientName;
	private String beautifySkinItemName;
	private String cosmeticShopName;
	private int itemNumber;
	private long createCardTotalAmount;
	private float discount;
	private long receivedAmount;
	private long unpaidAmount;
	private float earnedAmount;
	
	private long receivedEarnedAmount;
	private float unpaidEarnedAmount;
	private float employeePremium;
	private float shopPremium;
	private String createCardDate;
	private String sellerName;
	private String description;
	// public String getSaleId() {
	// 	return saleId;
	// }
	// public void setSaleId(String saleId) {
	// 	this.saleId = saleId;
	// }
	// public String getClientName() {
	// 	return clientName;
	// }
	// public void setClientName(String clientName) {
	// 	this.clientName = clientName;
	// }
	// public String getBeautifySkinItemName() {
	// 	return beautifySkinItemName;
	// }
	// public void setBeautifySkinItemName(String beautifySkinItemName) {
	// 	this.beautifySkinItemName = beautifySkinItemName;
	// }
	// public String getCosmeticShopName() {
	// 	return cosmeticShopName;
	// }
	// public void setCosmeticShopName(String cosmeticShopName) {
	// 	this.cosmeticShopName = cosmeticShopName;
	// }
	// public String getSellerName() {
	// 	return sellerName;
	// }
	// public void setSellerName(String sellerName) {
	// 	this.sellerName = sellerName;
	// }
	// public int getItemNumber() {
	// 	return itemNumber;
	// }
	// public void setItemNumber(int itemNumber) {
	// 	this.itemNumber = itemNumber;
	// }
	// public float getDiscount() {
	// 	return discount;
	// }
	// public void setDiscount(float discount) {
	// 	this.discount = discount;
	// }
	// public float getEmployeePremium() {
	// 	return employeePremium;
	// }
	// public void setEmployeePremium(float employeePremium) {
	// 	this.employeePremium = employeePremium;
	// }
	// public float getShopPremium() {
	// 	return shopPremium;
	// }
	// public void setShopPremium(float shopPremium) {
	// 	this.shopPremium = shopPremium;
	// }
	// public long getReceivedAmount() {
	// 	return receivedAmount;
	// }
	// public void setReceivedAmount(long receivedAmount) {
	// 	this.receivedAmount = receivedAmount;
	// }
	// public String getCreateCardDate() {
	// 	return createCardDate;
	// }
	// public void setCreateCardDate(String createCardDate) {
	// 	this.createCardDate = createCardDate;
	// }
	// public String getDescription() {
	// 	return description;
	// }
	// public void setDescription(String description) {
	// 	this.description = description;
	// }
	// public long getCreateCardTotalAmount() {
	// 	return createCardTotalAmount;
	// }
	// public void setCreateCardTotalAmount(long createCardTotalAmount) {
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// }
	// public long getUnpaidAmount() {
	// 	return unpaidAmount;
	// }
	// public void setUnpaidAmount(long unpaidAmount) {
	// 	this.unpaidAmount = unpaidAmount;
	// }
	// public long getEarnedAmount() {
	// 	return earnedAmount;
	// }
	// public void setEarnedAmount(long earnedAmount) {
	// 	this.earnedAmount = earnedAmount;
	// }
	// public long getReceivedEarnedAmount() {
	// 	return receivedEarnedAmount;
	// }
	// public void setReceivedEarnedAmount(long receivedEarnedAmount) {
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// }
	// public long getUnpaidEarnedAmount() {
	// 	return unpaidEarnedAmount;
	// }
	// public void setUnpaidEarnedAmount(long unpaidEarnedAmount) {
	// 	this.unpaidEarnedAmount = unpaidEarnedAmount;
	// }
	// public SaleClientItemSellerDTO(String saleId, String clientName, String beautifySkinItemName,
	// 		String cosmeticShopName, int itemNumber, long createCardTotalAmount, float discount, long receivedAmount,
	// 		long unpaidAmount, long earnedAmount, long receivedEarnedAmount, long unpaidEarnedAmount, float employeePremium,
	// 		float shopPremium, String createCardDate, String sellerName, String description) {
	// 	super();
	// 	this.saleId = saleId;
	// 	this.clientName = clientName;
	// 	this.beautifySkinItemName = beautifySkinItemName;
	// 	this.cosmeticShopName = cosmeticShopName;
	// 	this.itemNumber = itemNumber;
	// 	this.createCardTotalAmount = createCardTotalAmount;
	// 	this.discount = discount;
	// 	this.receivedAmount = receivedAmount;
	// 	this.unpaidAmount = unpaidAmount;
	// 	this.earnedAmount = earnedAmount;
	// 	this.receivedEarnedAmount = receivedEarnedAmount;
	// 	this.unpaidEarnedAmount = unpaidEarnedAmount;
	// 	this.employeePremium = employeePremium;
	// 	this.shopPremium = shopPremium;
	// 	this.createCardDate = createCardDate;
	// 	this.sellerName = sellerName;
	// 	this.description = description;
	// }
	@Override
	public String toString() {
		return "SaleClientItemSellerDTO [saleId=" + saleId + ", clientName=" + clientName + ", beautifySkinItemName="
				+ beautifySkinItemName + ", cosmeticShopName=" + cosmeticShopName + ", itemNumber=" + itemNumber
				+ ", createCardTotalAmount=" + createCardTotalAmount + ", discount=" + discount + ", receivedAmount="
				+ receivedAmount + ", unpaidAmount=" + unpaidAmount + ", earnedAmount=" + earnedAmount
				+ ", receivedEarnedAmount=" + receivedEarnedAmount + ", unpaidEarnedAmount=" + unpaidEarnedAmount
				+ ", employeePremium=" + employeePremium + ", shopPremium=" + shopPremium + ", createCardDate="
				+ createCardDate + ", sellerName=" + sellerName + ", description=" + description + "]";
	}
	public int compareTo(SaleClientItemSellerDTO saleClientItemSellerDTO) {
		
		//ascending order
		return this.saleId.compareTo(saleClientItemSellerDTO.getSaleId());
		
	}

	public static Comparator<SaleClientItemSellerDTO> discountComparator 
							 =Comparator.comparing(SaleClientItemSellerDTO::getDiscount);
	public static Comparator<SaleClientItemSellerDTO> unpaidAmountComparator 
							 =Comparator.comparing(SaleClientItemSellerDTO::getUnpaidAmount);
	public static Comparator<SaleClientItemSellerDTO> earnedAmountComparator 
							 =Comparator.comparing(SaleClientItemSellerDTO::getEarnedAmount);
	public static Comparator<SaleClientItemSellerDTO> unpaidEarnedAmountComparator 
							 =Comparator.comparing(SaleClientItemSellerDTO::getUnpaidEarnedAmount);
						//   = (s1, s2) -> s1.getDiscount()-s2.getDiscount();
	// 					  new Comparator<SaleClientItemSellerDTO>() {

	//     public int compare(SaleClientItemSellerDTO saleClientItemSellerDTO1, SaleClientItemSellerDTO saleClientItemSellerDTO2) {
	    	
	//       Float discount1 = saleClientItemSellerDTO1.getDiscount();
	//       Float discount2 = saleClientItemSellerDTO2.getDiscount();
	      
	//       //ascending order
	//       return discount1.compareTo(discount2);
	      
	//       //descending order
	//       //return fruitName2.compareTo(fruitName1);
	//     }

	// };
	
	
}
