package com.example.shop.service;

import com.example.shop.user.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);

    Optional<User> findById(Long id);

    List<User> findAll();
}
