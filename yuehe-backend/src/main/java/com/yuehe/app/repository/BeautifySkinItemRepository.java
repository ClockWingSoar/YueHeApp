package com.yuehe.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.Client;

import java.util.List;

@Repository
public interface BeautifySkinItemRepository extends JpaRepository<BeautifySkinItem, Integer> {
	// custom query to search to BeautifySkinItem by id or name
	List<BeautifySkinItem> findAll();
	BeautifySkinItem findById(String id);
//	BeautifySkinItem findByName(String name);
	List<BeautifySkinItem> findByName(String name);
	//BeautifySkinItem save(BeautifySkinItem beautifySkinItem);
//	void delete(String id);

}
