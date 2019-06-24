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

import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.repository.CosmeticShopRepository;

/**
 * @author Shazin Sadakath
 */
@Service
@Transactional(readOnly = true)
public class CosmeticShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CosmeticShopService.class);
    private final CosmeticShopRepository cosmeticShopRepository;

    public CosmeticShopService(CosmeticShopRepository cosmeticShopRepository) {
        this.cosmeticShopRepository = cosmeticShopRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public CosmeticShop create(CosmeticShop cosmeticShop) {
        return cosmeticShopRepository.save(cosmeticShop);
    }
    public List<CosmeticShop> getAllCosmeticShop() {
        return cosmeticShopRepository.findAll();
    }
    public CosmeticShop getCosmeticShopById(String id) {
    	return cosmeticShopRepository.findById(id);
    }
    public List<CosmeticShop> getCosmeticShopByName(String name) {
    	return cosmeticShopRepository.findByName(name);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<CosmeticShop> saveAll(List<CosmeticShop> cosmeticShop) {
        LOGGER.info("Saving {}", cosmeticShop);
        return cosmeticShopRepository.saveAll(cosmeticShop);
    }
}
