package com.cloud.repo;

//package com.example.cloudconsole.repository;

//import com.example.cloudconsole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

