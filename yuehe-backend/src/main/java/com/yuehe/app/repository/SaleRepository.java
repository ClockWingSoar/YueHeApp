/*
    Copyright (C) 2019 Yi Xiang Zhong

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

import com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO;
import com.yuehe.app.dto.SaleClientItemSellerForDBDTO;
import com.yuehe.app.dto.SaleDetailForDBDTO;
import com.yuehe.app.dto.SalePerformanceDetailForDBDTO;
import com.yuehe.app.entity.Sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Soveran Zhong
 */
public interface SaleRepository extends JpaRepository<Sale, String>,JpaSpecificationExecutor<Sale> {

	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDTO(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,s.receivedEarnedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
	Page<SaleClientItemSellerForDBDTO> fetchSaleClientItemSellerData(Pageable pageable);

	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDTO(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,s.receivedEarnedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
	Page<SaleClientItemSellerForDBDTO> fetchSaleClientItemSellerDataWithFiltering(Specification<Sale> spec,Pageable pageable);
	
	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDTO(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,s.receivedEarnedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
    List<SaleClientItemSellerForDBDTO> fetchSaleClientItemSellerDataForDownloadWithSort(Sort sort);
	
	//add below method to avoid sorting the table when using frontend view table column not in DB to sort,like unpaidAmount
	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerForDBDTO(s.id,c.name, b.name, p.name, "
			+ "s.itemNumber,s.createCardTotalAmount,b.price,"
			+ "s.receivedAmount,s.receivedEarnedAmount,p.discount ,s.employeePremium,s.shopPremium,"
			+ "s.createCardDate, e.name,s.description) "
			+ "FROM Sale s INNER JOIN s.client c "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p")
    List<SaleClientItemSellerForDBDTO> fetchSaleClientItemSellerDataForDownload();
	/**
	 * To calculate the overall sale consuming situation-including consumed amount, consumed earned amount, advanced earned amount
	  * 用于计算耗卡情况，如该销售卡的实际消耗，实际回款消耗，还有剩余的预付款等
	 * @param id
	 * @return
	 */
	@Query("SELECT new com.yuehe.app.dto.SaleDetailForDBDTO(s.id,s.createCardDate, b.name,  "
			+ "s.createCardTotalAmount,s.itemNumber,"
			+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
			+ "FROM Sale s INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.client c "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p "
			+ "where s.id = ?1")
	SaleDetailForDBDTO fetchSaleBasicDetailById(String id);
	/**
	 * To calculate the overall sale performance situation-including create card amount, received amount, debt amount etc.
	 * 用于计算销售业绩情况，如该销售卡的开卡金额，实际收款，实际回款，还有欠款等
	 * @param id
	 * @return
	 */
	@Query("SELECT new com.yuehe.app.dto.SalePerformanceDetailForDBDTO(s.id,s.createCardDate, b.name,  "
			+ "s.createCardTotalAmount,s.receivedAmount,s.receivedEarnedAmount,s.itemNumber,"
			+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
			+ "FROM Sale s INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN s.client c "
			+ "INNER JOIN s.employee e "
			+ "INNER JOIN c.cosmeticShop p "
			+ "where s.id = ?1")
	SalePerformanceDetailForDBDTO fetchSalePerformanceDetailById(String id);
	// @Query("SELECT new com.yuehe.app.dto.SalePerformanceDetailForDBDTO(s.id,s.createCardDate, b.name,  "
	// 		+ "s.createCardTotalAmount,s.receivedAmount,s.receivedEarnedAmount,s.itemNumber,"
	// 		+ "p.discount ,s.employeePremium,s.shopPremium,s.description) "
	// 		+ "FROM Sale s INNER JOIN s.beautifySkinItem b "
	// 		+ "INNER JOIN s.client c "
	// 		+ "INNER JOIN s.employee e "
	// 		+ "INNER JOIN c.cosmeticShop p "
	// 		+ "where s.id = ?1 AND s.createCardDate >= ?2 AND s.createCardDate <= ?3")
	// SalePerformanceDetailForDBDTO fetchSalePerformanceDetailByIdAndCreateCardDate(String id, String startDate, String endDate);
	
	// @Modifying
	// @Query("delete Sale s where s.id = ?1")
    // void deleteById(String id);
    
    // @Query("select s from Sale s where s.clientId = ?1 AND s.beautifySkinItemId = ?2 AND s.createCardDate = ?3")
    // Sale findByClientIdAndItemIdAndCreateCardDate(String clientId, String beautifySkinItemId, String createCardDate);
    
    @Query("select new com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO(s.id, b.name, s.createCardDate) from Sale s "
    		+ "INNER JOIN s.beautifySkinItem b where s.client.id = ?1")
	List<SaleBeautifySkinItemForFilterDTO> findByClientId(String clientId);

    @Query("select new com.yuehe.app.dto.SaleBeautifySkinItemForFilterDTO(s.id, b.name, s.createCardDate) from Sale s "
    		+ "INNER JOIN s.beautifySkinItem b where s.client.id = ?1 AND s.createCardDate >= ?2 AND s.createCardDate <= ?3")
	List<SaleBeautifySkinItemForFilterDTO> findByClientIdAndCreateCardDate(String clientId, String startDate, String endDate);

	/**
	 * get all the ids from table sale 
	 * @return a list with all the sale ids
	 */
	@Query("select new Sale(s.id) from Sale s")
    List<Sale> findAllIds();
}

