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

import com.yuehe.app.entity.Tool;
import com.yuehe.app.repository.ToolRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = true)
public class ToolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolService.class);
    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Tool create(Tool tool) {
        return toolRepository.save(tool);
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool getById(String id) {
        return toolRepository.findById(id);
    }

    public Tool getToolByName(String name) {
        return toolRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Tool> saveAll(List<Tool> tool) {
        LOGGER.info("Saving {}", tool);
        return toolRepository.saveAll(tool);
    }

    public long getEntityNumber() {
        return toolRepository.count();
    }
}
