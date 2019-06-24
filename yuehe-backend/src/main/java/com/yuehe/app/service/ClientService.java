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

import com.yuehe.app.dto.ClientShopDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.repository.ClientRepository;

/**
 * @author Shazin Sadakath
 */
@Service
@Transactional(readOnly = true)
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Client create(Client client) {
        return clientRepository.save(client);
    }
    public List<Client> getAllClient() {
//    	List<Client> clientList = new ArrayList<Client>();
//    	clientList = clientRepository.findAll();
        return clientRepository.findAll();
    }
    public Client getClientById(String id) {
    	return clientRepository.findById(id);
    }
    public List<Client> getClientByName(String name) {
    	return clientRepository.findByName(name);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<Client> saveAll(List<Client> client) {
        LOGGER.info("Saving {}", client);
        return clientRepository.saveAll(client);
    }
	public List<ClientShopDto> getClientsDetailList() {
		List<ClientShopDto> list = clientRepository.fetchClientShopDataInnerJoin();
		list.forEach(l -> System.out.println(l));
		return list;
	}
	 public long getEntityNumber() {
	    	return clientRepository.count();
	    }
}
