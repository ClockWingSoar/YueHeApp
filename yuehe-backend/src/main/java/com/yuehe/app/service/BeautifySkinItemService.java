package com.yuehe.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;
import com.yuehe.app.repository.BeautifySkinItemRepository;

import java.util.List;

/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = true)
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
    public long getEntityNumber() {
    	return beautifySkinItemRepository.count();
    }
}

