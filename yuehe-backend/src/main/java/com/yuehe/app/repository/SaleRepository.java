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

import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto;
import com.yuehe.app.dto.SaleClientItemSellerForDBDto;
import com.yuehe.app.dto.SaleDetailForDBDto;
import com.yuehe.app.dto.SalePerformanceDetailForDBDto;
import com.yuehe.app.entity.Sale;

/**
 * @author Soveran Zhong
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDto(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,s.receivedEarnedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
    List<SaleClientItemSellerForDBDto> fetchSaleClientItemSellerData();
	/**
	 * To calculate the overall sale consuming situation-including consumed amount, consumed earned amount, advanced earned amount
	  * 用于计算耗卡情况，如该销售卡的实际消耗，实际回款消耗，还有剩余的预付款等
	 * @param id
	 * @return
	 */
	@Query("SELECT new com.yuehe.app.dto.SaleDetailForDBDto(s.id,s.createCardDate, b.name,  "
			+ "s.createCardTotalAmount,s.itemNumber,"
			+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
			+ "FROM Sale s INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.client c "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p "
			+ "where s.id = ?1")
	SaleDetailForDBDto fetchSaleBasicDetailById(String id);
	/**
	 * To calculate the overall sale performance situation-including create card amount, received amount, debt amount etc.
	 * 用于计算销售业绩情况，如该销售卡的开卡金额，实际收款，实际回款，还有欠款等
	 * @param id
	 * @return
	 */
	@Query("SELECT new com.yuehe.app.dto.SalePerformanceDetailForDBDto(s.id,s.createCardDate, b.name,  "
			+ "s.createCardTotalAmount,s.receivedAmount,s.receivedEarnedAmount,s.itemNumber,"
			+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
			+ "FROM Sale s INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.client c "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p "
			+ "where s.id = ?1")
	SalePerformanceDetailForDBDto fetchSalePerformanceDetailById(String id);
	
	
    Sale findById(String id);
    
    @Query("select s from Sale s where s.clientId = ?1 AND s.beautifySkinItemId = ?2 AND s.createCardDate = ?3")
    Sale findByClientIdAndItemIdAndCreateCardDate(String clientId, String beautifySkinItemId, String createCardDate);
    
    @Query("select new com.yuehe.app.dto.SaleBeautifySkinItemForFilterDto(s.id, b.name, s.createCardDate) from Sale s "
    		+ "INNER JOIN s.beautifySkinItem b where s.clientId = ?1")
    List<SaleBeautifySkinItemForFilterDto> findByClientId(String clientId);
}
