package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.repositories.IUserRepository;
import com.paysharepal.paysharepal.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDto> GetAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                UserDto::of).toList();
    }

    public UserDto Get(UUID id) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new UserNotExistsException(String.format("There is no user with id %s", id));
        return UserDto.of(optionalUser.get());
    }

    public UserDto Add(UserContract u) {
        String passwordHash = bCryptPasswordEncoder.encode(u.password());
        User user = new User(u.name(), u.email(), passwordHash);
        userRepository.save(user);
        return UserDto.of(user);
    }

    public UserDto Delete(UUID userId) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty())
            throw new UserNotExistsException(String.format("There is no user with id %s", userId));
        User user = optionalUser.get();
        userRepository.deleteById(userId);
        return UserDto.of(user);
    }
}
