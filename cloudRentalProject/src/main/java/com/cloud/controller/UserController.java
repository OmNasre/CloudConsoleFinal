package com.cloud.controller;

import java.util.HashMap;

import java.util.Map;

//package com.example.cloudconsole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entity.User;
import com.cloud.user.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }
    @GetMapping("/temp")
    public String getMethodName() {
        return "hello";
    }
    
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("userId", user.getId().toString());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping("/{userId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        User user = userService.findUserById(id);
        System.out.println("He came here1");
        if (user != null) {
        	System.out.println("He came here2");
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

