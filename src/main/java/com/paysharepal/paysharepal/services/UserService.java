package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.repositories.IUserRepository;
import com.paysharepal.paysharepal.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> GetAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                UserDto::of).toList();
    }

    public Optional<UserDto> Get(UUID id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(UserDto::of);
    }

    public UserDto Add(UserContract u) {
        User user = new User(u.name(), u.email());
        userRepository.save(user);
        return UserDto.of(user);
    }
}
