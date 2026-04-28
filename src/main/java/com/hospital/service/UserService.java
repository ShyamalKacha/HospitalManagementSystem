package com.hospital.service;

import com.hospital.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User saveUser(User user);
    boolean existsByUsername(String username);
}