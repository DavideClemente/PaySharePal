package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserDto;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.repositories.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public List<User> GetAll() {
        return userRepository.findAll();
    }

    public Optional<User> Get(UUID id) {
        return userRepository.findById(id);
    }

    public User Add(UserDto u) {
        User user = new User(u.name());
        return userRepository.save(user);
    }
}
