package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDto> GetAll();

    Optional<UserDto> Get(UUID id);
    UserDto Add(UserContract userContract);
}
