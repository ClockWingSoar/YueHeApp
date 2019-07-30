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
package com.yuehe.app.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.yuehe.app.entity.User;
import com.yuehe.app.repository.UserRepository;
import com.yuehe.app.util.YueHeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Soveran Zhong
 */
@Service
@Transactional(readOnly = false)
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

    @Transactional(rollbackFor = Exception.class)
    public User create(User user) {
        return userRepository.save(user);
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    @Transactional(rollbackFor = Exception.class)
    public List<User> saveAll(List<User> user) {
        LOGGER.info("Saving {}", user);
        return userRepository.saveAll(user);
    }
    /**
	 * To get the biggest number of the current string id 
	 */ 
	 public int getBiggestIdNumber() {
		List<User> userList = userRepository.findAllIds();
		Collections.sort(userList,User.idComparator.reversed());
		String biggestId = userList.get(0).getId();
	    int biggestIdNum = YueHeUtil.extractIdDigitalNumber(biggestId);
		LOGGER.info("biggest Id Number-{}",biggestIdNum);
	    return biggestIdNum;
	}
}
