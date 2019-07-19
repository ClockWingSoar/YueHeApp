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

import com.yuehe.app.dto.DutyEmployeeRoleDTO;
import com.yuehe.app.entity.Duty;

/**
 * @author Soveran Zhong
 */
public interface DutyRepository extends JpaRepository<Duty, Long> {
	
	@Query("SELECT new com.yuehe.app.dto.DutyEmployeeRoleDTO(d.id,e.id,e.name, r.name,d.welfare, d.description) "
			+ "FROM Duty d INNER JOIN d.employee e  INNER JOIN d.role r WHERE r.name=?1")
    List<DutyEmployeeRoleDTO> findByRoleName(String name);
	
	@Query("SELECT new com.yuehe.app.dto.DutyEmployeeRoleDTO(d.id,e.id,e.name, r.name,d.welfare, d.description) "
			+ "FROM Duty d INNER JOIN d.employee e  INNER JOIN d.role r")
    List<DutyEmployeeRoleDTO> fetchDutyData();
    Duty findById(String id);
}
