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

import java.util.Collections;
import java.util.List;

import com.yuehe.app.entity.Employee;
import com.yuehe.app.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = false)
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getById(String id) {
    	return employeeRepository.findById(id);
    }
    public Employee getEmployeeByName(String name) {
    	return employeeRepository.findByName(name);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<Employee> saveAll(List<Employee> employee) {
        LOGGER.info("Saving {}", employee);
        return employeeRepository.saveAll(employee);
    }
	 public long getEntityNumber() {
	    	return employeeRepository.count();
        }
    /**
	 * To get the biggest number of the current string id 
	 */ 
    public int getBiggestIdNumber() {
		List<Employee> employeeList = employeeRepository.findAllIds();
		Collections.sort(employeeList,Employee.idComparator.reversed());
		String biggestId = employeeList.get(0).getId();
		int biggestIdNum = new Integer(biggestId.substring(biggestId.lastIndexOf("0")));
		LOGGER.info("biggest Id Number-{}",biggestIdNum);
	    return biggestIdNum;
	}
}
