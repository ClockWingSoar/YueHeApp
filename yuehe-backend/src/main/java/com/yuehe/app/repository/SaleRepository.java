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

import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.Sale;

/**
 * @author Soveran Zhong
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {

    //List<Sale> findByName(String name);
	@Query("SELECT new com.yuehe.app.dto.SaleClientItemSellerDto(s.id,c.name, b.name,p.name, e.name, s.itemNumber,s.discount,s.employeePremium,s.shopPremium,s.receivedAmount,s.createCardDate,s.description) "
			+ "FROM Sale s INNER JOIN s.client c INNER JOIN s.beautifySkinItem b INNER JOIN s.employee e INNER JOIN c.cosmeticShop p")
    List<SaleClientItemSellerDto> fetchSaleClientItemSellerDataInnerJoin();
    Sale findById(String id);
}
