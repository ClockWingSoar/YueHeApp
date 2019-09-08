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
package com.yuehe.app.service;

import java.util.List;

import com.yuehe.app.entity.SaleCardAmountAdjust;
import com.yuehe.app.repository.SaleCardAmountAdjustRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = false)
public class SaleCardAmountAdjustService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleCardAmountAdjustService.class);
    private final SaleCardAmountAdjustRepository saleCardAmountAdjustRepository;

    public SaleCardAmountAdjustService(SaleCardAmountAdjustRepository saleCardAmountAdjustRepository) {
        this.saleCardAmountAdjustRepository = saleCardAmountAdjustRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public SaleCardAmountAdjust create(SaleCardAmountAdjust saleCardAmountAdjust) {
        return saleCardAmountAdjustRepository.save(saleCardAmountAdjust);
    }

    public List<SaleCardAmountAdjust> getAllSaleCardAmountAdjusts() {
        return saleCardAmountAdjustRepository.findAll();
    }

    public SaleCardAmountAdjust getById(long id) {
        return saleCardAmountAdjustRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid saleCardAmountAdjust Id:" + id));
    }
    public List<SaleCardAmountAdjust> getBySaleId(String saleId) {
        return saleCardAmountAdjustRepository.findBySaleId(saleId);
    }
    public List<SaleCardAmountAdjust> getBySaleIdAndAdjustDate(String saleId, String startDate, String endDate) {
        return saleCardAmountAdjustRepository.findBySaleIdAndAdjustDate(saleId, startDate, endDate);
    }

    
    public void deleteById(long id) {
		saleCardAmountAdjustRepository.deleteById(id);
   }

    @Transactional(rollbackFor = Exception.class)
    public List<SaleCardAmountAdjust> saveAll(List<SaleCardAmountAdjust> saleCardAmountAdjust) {
        LOGGER.info("Saving {}", saleCardAmountAdjust);
        return saleCardAmountAdjustRepository.saveAll(saleCardAmountAdjust);
    }

}
