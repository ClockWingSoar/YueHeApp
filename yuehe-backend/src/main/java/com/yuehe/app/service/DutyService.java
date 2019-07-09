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

import com.yuehe.app.dto.DutyEmployeeRoleDto;
import com.yuehe.app.entity.Duty;
import com.yuehe.app.repository.DutyRepository;

/**
 * @author yi xiang zhong
 */
@Service
@Transactional(readOnly = true)
public class DutyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
    private final DutyRepository dutyRepository;

    public DutyService(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Duty create(Duty duty) {
        return dutyRepository.save(duty);
    }
    public List<Duty> getAllDuties() {
        return dutyRepository.findAll();
    }
    public Duty getDutyById(String id) {
    	return dutyRepository.findById(id);
    }
    public List<DutyEmployeeRoleDto> getAllPersonByRoleName(String name) {
    	return dutyRepository.findByRoleName(name);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public List<Duty> saveAll(List<Duty> duty) {
        LOGGER.info("Saving {}", duty);
        return dutyRepository.saveAll(duty);
    }
	public List<DutyEmployeeRoleDto> getDutyDetailList() {
		List<DutyEmployeeRoleDto> list = dutyRepository.fetchDutyData();
		list.forEach(l -> System.out.println(l));
		return list;
	}
	 public long getEntityNumber() {
	    	return dutyRepository.count();
	    }
}
