package com.yuehe.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yuehe.app.common.ClientColumnsEnum;
import com.yuehe.app.common.PaginationAndSortModel;
import com.yuehe.app.common.YueHeEntitiesEnum;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.dto.ClientShopDiscountDTO;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.repository.ClientRepository;
import com.yuehe.app.specification.SpecificationsBuilder;
import com.yuehe.app.util.ServiceUtil;
import com.yuehe.app.util.YueHeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    Sort sort = null;
	boolean sortByJPA = true;
	Comparator<Client> comparator = null;
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
        return clientRepository.findAll();
    }
    public Client getById(String id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
	}
    public List<Client> getClientByName(String name) {
    	return clientRepository.findByName(name);
    }
    public List<ClientShopDiscountDTO> getClientsByShopId(String cosmeticShopId) {
    	return clientRepository.findByShopId(cosmeticShopId);
    }
    public Client getClientForQuestionareById(String id) {
    	return clientRepository.findClientById(id);
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
	public ClientShopDTO getClientDetailById(String id) {
		return clientRepository.fetchClientDetailById(id);
	}
	 public long getEntityNumber() {
	    	return clientRepository.count();
        }
    /**
	 * To get the biggest number of the current string id 
	 */ 
    public int getBiggestIdNumber() {
		List<Client> clientList = clientRepository.findAllIds();
		Collections.sort(clientList,Client.idComparator.reversed());
		String biggestId = clientList.get(0).getId();
		int biggestIdNum = YueHeUtil.extractIdDigitalNumber(biggestId);
		LOGGER.info("biggest Id Number-{}",biggestIdNum);
	    return biggestIdNum;
    }
    public void deleteById(String id) {
		clientRepository.deleteById(id);
   }
    public Page<Client> getClientsDetailListWithPaginationAndSort(PaginationAndSortModel paginationAndSortModel) {
		Pageable pageable = buildPaginationAndSort(paginationAndSortModel);
		Page<Client> clientPage = clientRepository.findAll(pageable);

		return clientPage;
	}

	public Pageable buildPaginationAndSort(PaginationAndSortModel paginationAndSortModel) {
		buildSort(paginationAndSortModel.getSortDirection(), paginationAndSortModel.getSortProperty());
		return ServiceUtil.buildPageableObj(sortByJPA, sort, paginationAndSortModel);
	}
	/**
	 * to get all the clients detail with pagination and sort and filtering,  can't use @query to do the dynamic query
	 * can't use a DTO class to store the dynamic querired result, so use findAll method
	 */
	public Page<Client> getClientsDetailListWithPaginationAndSortAndFiltering(PaginationAndSortModel paginationAndSortModel, String searchParameters) {
		Pageable pageable = buildPaginationAndSort(paginationAndSortModel);
		SpecificationsBuilder<Client> specificationsBuilder = new SpecificationsBuilder<>();
		Specification<Client> spec = specificationsBuilder.resolveSpecification(searchParameters,YueHeEntitiesEnum.CLIENT);
		LOGGER.info("spec {}",spec);
		Page<Client> clientPage = clientRepository.findAll(spec,pageable);
		return clientPage;
	}
	
	/**
	 * Get the clients detail list for downloading as csv, excel or pdf
	 * @param sortDirection
	 * @param sortProperty
	 * @return
	 */
	public Map<String, Object>  getClientsDetailListForDownload(Direction sortDirection,String sortProperty){
		sort = Sort.by(sortDirection, sortProperty);
		List<ClientShopDTO> clientShopDTOList = new ArrayList<ClientShopDTO>();
		List<Sort.Order> sortOrders = null;
		Map<String, Object> clientMap = new HashMap<String, Object>();
		buildSort(sortDirection, sortProperty);
		if(sortByJPA){
			sortOrders = sort.get().collect(Collectors.toList());
			clientShopDTOList = clientRepository.fetchClientShopDataListForDownloadWithSort(sort);
		}else{
			clientShopDTOList = clientRepository.fetchClientShopDataListForDownload();

		}
		for (ClientShopDTO clientShopDTO : clientShopDTOList) {
			String gender = clientShopDTO.getGender();
			if(gender.equals("f"))
				clientShopDTO.setGender("女性");
			else
				clientShopDTO.setGender("男性");

		}
		clientMap.put("csvObjList",clientShopDTOList);
		clientMap.put("sortOrders", sortOrders);
		return clientMap;

	}
	

	private void buildSort(Direction sortDirection, String sortProperty) {

		// Below code is using the table field to do the sorting, when it comes to the
		// foreign table, you need to use the foreign
		// relationship to refer to it, like through entity "sale" to "client" to
		// "cosmeticShop", you got "client.cosmeticshop.name"
		// but you can't have any "." inside a enum class, so you have to remove all the
		// ".", then do the comparing
		ClientColumnsEnum clientSortBy = ClientColumnsEnum
				.valueOf(StringUtils.remove(sortProperty.toUpperCase(), '.'));
		switch (clientSortBy) {
		case ID:
		case NAME:
		case AGE:
		case GENDER:
		case SYMPTOM:
			sort = Sort.by(new Order(sortDirection, sortProperty));
			sortByJPA = true;
			break;
		case COSMETICSHOPNAME:
			sort = Sort.by(new Order(sortDirection, "cosmeticShop.name"));
			sortByJPA = true;
			break;
		default:
			break;

		}
	}
}
