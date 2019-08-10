package com.yuehe.app.repository;

import java.util.List;

import com.yuehe.app.entity.BeautifySkinItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautifySkinItemRepository extends JpaRepository<BeautifySkinItem, String> {
	// custom query to search to BeautifySkinItem by id or name
	List<BeautifySkinItem> findAll();
	BeautifySkinItem findByName(String name);

    /**
    * get all the ids from table beautifySkinItem 
    * @return a list with all the beautifySkinItem ids
    */
	@Query("select new BeautifySkinItem(b.id) from BeautifySkinItem b")
	List<BeautifySkinItem> findAllIds();

}
