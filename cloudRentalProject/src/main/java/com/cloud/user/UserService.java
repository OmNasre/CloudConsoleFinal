package com.cloud.user;

//package com.example.cloudconsole.service;
//
//import com.example.cloudconsole.entity.User;
//import com.example.cloudconsole.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.entity.User;
import com.cloud.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        
        return null; // Return null if credentials are invalid
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

