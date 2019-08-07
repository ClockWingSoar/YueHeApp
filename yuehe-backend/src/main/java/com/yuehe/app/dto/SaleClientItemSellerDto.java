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
