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

import com.yuehe.app.entity.ClientQuestionare;
import com.yuehe.app.repository.ClientQuestionareRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author soveran zhong
 */
@Service
@Transactional(readOnly = false)
public class ClientQuestionareService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientQuestionareService.class);
    private final ClientQuestionareRepository clientQuestionareRepository;

    public ClientQuestionareService(ClientQuestionareRepository clientQuestionareRepository) {
        this.clientQuestionareRepository = clientQuestionareRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ClientQuestionare create(ClientQuestionare clientQuestionare) {
        return clientQuestionareRepository.save(clientQuestionare);
    }

    public List<ClientQuestionare> getAllClientQuestionares() {
        return clientQuestionareRepository.findAll();
    }

    public ClientQuestionare getById(String id) {
        return clientQuestionareRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid clientQuestionare Id:" + id));
    }
    public ClientQuestionare getByClientId(String clientId) {
        return clientQuestionareRepository.findByClientId(clientId);
    }

    
    public void deleteById(String id) {
		clientQuestionareRepository.deleteById(id);
   }

    @Transactional(rollbackFor = Exception.class)
    public List<ClientQuestionare> saveAll(List<ClientQuestionare> clientQuestionare) {
        LOGGER.info("Saving {}", clientQuestionare);
        return clientQuestionareRepository.saveAll(clientQuestionare);
    }

}
