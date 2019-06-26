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

import com.yuehe.app.entity.Role;
import com.yuehe.app.repository.RoleRepository;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = true)
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Role create(Role role) {
        return roleRepository.save(role);
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Role getRoleById(String id) {
    	return roleRepository.findById(id);
    }
    public List<Role> getRoleByName(String name) {
    	return roleRepository.findByName(name);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<Role> saveAll(List<Role> role) {
        LOGGER.info("Saving {}", role);
        return roleRepository.saveAll(role);
    }
	 public long getEntityNumber() {
	    	return roleRepository.count();
	    }
}
