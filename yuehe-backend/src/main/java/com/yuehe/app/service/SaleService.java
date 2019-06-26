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
package com.yuehe.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.SaleClientItemSellerDto;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.repository.SaleRepository;

/**
 * @author Shazin Sadakath
 */
@Service
@Transactional(readOnly = true)
public class SaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Sale create(Sale sale) {
        return saleRepository.save(sale);
    }
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }
    public Sale getSaleById(String id) {
    	return saleRepository.findById(id);
    }
//    public List<Sale> getSaleByName(String name) {
//    	return saleRepository.findByName(name);
//    }
    @Transactional(rollbackFor = Exception.class)
    public List<Sale> saveAll(List<Sale> sale) {
        LOGGER.info("Saving {}", sale);
        return saleRepository.saveAll(sale);
    }
	public List<SaleClientItemSellerDto> getSalesDetailList() {
		List<SaleClientItemSellerDto> list = saleRepository.fetchSaleClientItemSellerDataInnerJoin();
		list.forEach(l -> System.out.println(l));
		return list;
	}
	 public long getEntityNumber() {
	    	return saleRepository.count();
	    }
}
