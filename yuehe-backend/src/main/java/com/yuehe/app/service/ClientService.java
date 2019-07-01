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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.ClientShopDto;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.repository.ClientRepository;

/**
 * @author Shazin Sadakath
 */
@Service
@Transactional(readOnly = true)
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
    private final ClientRepository clientRepository;
	@Autowired
	private final CosmeticShopService cosmeticShopService;
	private CosmeticShop cosmeticShop;

    public ClientService(ClientRepository clientRepository, CosmeticShopService cosmeticShopService) {
        this.clientRepository = clientRepository;
        this.cosmeticShopService = cosmeticShopService;
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
    public List<Client> getClientsByShopId(String cosmeticShopId) {
    	return clientRepository.findByShopId(cosmeticShopId);
    }
    public Client getClientByClientNameAndShopName(String clientName, String cosmeticShopName) {
    	cosmeticShop = cosmeticShopService.getCosmeticShopByName(cosmeticShopName);
    	return clientRepository.findByClientNameAndShopId(clientName,cosmeticShop.getId());
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
