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
package com.yuehe.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.entity.Client;

/**
 * @author Soveran Zhong
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByName(String name);
	@Query("SELECT new com.yuehe.app.dto.ClientShopDTO(c.id,c.name, s.name, c.age, c.gender,c.symptom) "
			+ "FROM Client c INNER JOIN c.cosmeticShop s")
    List<ClientShopDTO> fetchClientShopDataList();
	
	@Query("SELECT new com.yuehe.app.dto.ClientShopDTO(c.id,c.name, s.name, c.age, c.gender,c.symptom) "
			+ "FROM Client c INNER JOIN c.cosmeticShop s where c.id = ?1")
	ClientShopDTO fetchClientDetailById(String id);
	
    Client findById(String id);
    @Query("select c from Client c where c.name = ?1 AND c.cosmeticShop.id =?2")
    Client findByClientNameAndShopId(String clientName, String cosmeticShopId);
    
    @Query("select new Client(c.id,c.name) from Client c where c.cosmeticShop.id = ?1")
    List<Client> findByShopId(String cosmeticShopId);

    /**
    * get all the ids from table client 
    * @return a list with all the client ids
    */
   @Query("select new Client(c.id) from Client c")
   List<Client> findAllIds();
}
