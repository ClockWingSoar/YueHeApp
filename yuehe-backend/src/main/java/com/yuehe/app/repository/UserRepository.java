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
package com.yuehe.app.repository;

import java.util.List;

import com.yuehe.app.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yi xiang zhong
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
     /**
	 * get all the ids from table user 
	 * @return a list with all the user ids
	 */
	@Query("select new User(u.id) from User u")
    List<User> findAllIds();
}
