/*
    Copyright (C) 2018  Shazin Sadakath

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.yuehe.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuehe.app.dto.SaleClientItemSellerForDBDto;
import com.yuehe.app.entity.Sale;

/**
 * @author Soveran Zhong
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {

    //List<Sale> findByName(String name);
//	(String saleId, String clientName, String beautifySkinItemName,
//			String cosmeticShopName, int itemNumber, long createCardTotalAmount, float discount, long receivedAmount,
//			long unpaidAmount, long earnedAmount, long receivedEarnedAmount, long unpaidEarnedAmount, float employeePremium,
//			float shopPremium, Date createCardDate, String sellerName, String description)
	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDto(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
//	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerDto(s.id,c.name, b.name, p.name, "
//			+ "s.itemNumber,s.createCardTotalAmount,(s.createCardTotalAmount / (b.price * s.itemNumber)) as discount,"
//			+ "s.receivedAmount,(s.createCardTotalAmount - s.receivedAmount) as unpaidAmount,"
//			+ "(s.createCardTotalAmount * p.discount) as earnedAmount,(s.receivedAmount * p.discount) as receivedEarnedAmount,"
//			+ "(earnedAmount - receivedEarnedAmount) as unpaidEarnedAmount ,s.employeePremium,s.shopPremium,"
//			+ "s.createCardDate, e.name,s.description) "
//			+ "FROM Sale s INNER JOIN s.client c "
//			+ "INNER JOIN s.beautifySkinItem b "
//			+ "INNER JOIN s.employee e "
//			+ "INNER JOIN c.cosmeticShop p")
    List<SaleClientItemSellerForDBDto> fetchSaleClientItemSellerData();
    Sale findById(String id);
}
