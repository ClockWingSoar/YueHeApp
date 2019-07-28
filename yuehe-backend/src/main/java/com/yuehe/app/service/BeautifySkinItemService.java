package com.yuehe.app.service;

import java.util.Collections;
import java.util.List;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.repository.BeautifySkinItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)
public class BeautifySkinItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeautifySkinItemService.class);

    private final BeautifySkinItemRepository beautifySkinItemRepository;

    public BeautifySkinItemService(BeautifySkinItemRepository beautifySkinItemRepository) {
        this.beautifySkinItemRepository = beautifySkinItemRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<BeautifySkinItem> saveAll(List<BeautifySkinItem> beautifySkinItems) {
        LOGGER.info("Saving {}", beautifySkinItems);
        return beautifySkinItemRepository.saveAll(beautifySkinItems);
    }

    public List<BeautifySkinItem> getAllBeautifySkinItem() {
        return beautifySkinItemRepository.findAll();
    }
    public BeautifySkinItem getBeautifySkinItemByName(String name) {
    	return beautifySkinItemRepository.findByName(name);
    }
    public BeautifySkinItem getById(String id) {
    	return beautifySkinItemRepository.findById(id);
    }
    public long getEntityNumber() {
    	return beautifySkinItemRepository.count();
    }
    /**
	 * To get the biggest number of the current string id 
	 */ 
    public int getBiggestIdNumber() {
		List<BeautifySkinItem> beautifySkinItemList = beautifySkinItemRepository.findAllIds();
		Collections.sort(beautifySkinItemList,BeautifySkinItem.idComparator.reversed());
		String biggestId = beautifySkinItemList.get(0).getId();
		int biggestIdNum = new Integer(biggestId.substring(biggestId.lastIndexOf("0")));
		LOGGER.info("biggest Id Number-{}",biggestIdNum);
	    return biggestIdNum;
	}    
}

