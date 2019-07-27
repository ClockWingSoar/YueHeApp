package com.yuehe.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.repository.ClientRepository;

/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
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
    public Client getById(String id) {
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
	public List<ClientShopDTO> getClientsDetailList() {
		List<ClientShopDTO> list = clientRepository.fetchClientShopDataList();
		list.forEach(l -> System.out.println(l));
		return list;
	}
	public ClientShopDTO getClientDetailById(String id) {
		return clientRepository.fetchClientDetailById(id);
	}
	 public long getEntityNumber() {
	    	return clientRepository.count();
	    }
}
