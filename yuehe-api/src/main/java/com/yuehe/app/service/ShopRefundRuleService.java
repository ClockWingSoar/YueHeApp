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

import com.yuehe.app.entity.ShopRefundRule;
import com.yuehe.app.repository.ShopRefundRuleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = false)
public class ShopRefundRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopRefundRuleService.class);
    private final ShopRefundRuleRepository shopRefundRuleRepository;

    public ShopRefundRuleService(ShopRefundRuleRepository shopRefundRuleRepository) {
        this.shopRefundRuleRepository = shopRefundRuleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ShopRefundRule create(ShopRefundRule shopRefundRule) {
        return shopRefundRuleRepository.save(shopRefundRule);
    }

    public List<ShopRefundRule> getAllShopRefundRules() {
        return shopRefundRuleRepository.findAll();
    }

    public ShopRefundRule getById(long id) {
        return shopRefundRuleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shopRefundRule Id:" + id));
    }
    public List<ShopRefundRule> getByShopId(String shopId) {
        return shopRefundRuleRepository.findByShopId(shopId);
    }
    public List<ShopRefundRule> getByShopIdAndAdjustDate(String shopId, String startDate, String endDate) {
        return shopRefundRuleRepository.findByShopIdAndAdjustDate(shopId, startDate, endDate);
    }

    
    public void deleteById(long id) {
		shopRefundRuleRepository.deleteById(id);
   }

    @Transactional(rollbackFor = Exception.class)
    public List<ShopRefundRule> saveAll(List<ShopRefundRule> shopRefundRule) {
        LOGGER.info("Saving {}", shopRefundRule);
        return shopRefundRuleRepository.saveAll(shopRefundRule);
    }

}
