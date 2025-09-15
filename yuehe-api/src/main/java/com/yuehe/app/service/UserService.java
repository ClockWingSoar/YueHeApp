package com.yuehe.app.service;

import com.yuehe.app.entity.User;
import com.yuehe.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public User create(User user) {
        return userRepository.save(user);
    }
    
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
