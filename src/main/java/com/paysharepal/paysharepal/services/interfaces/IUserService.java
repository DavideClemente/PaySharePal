package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDto> GetAll();

    UserDto Get(UUID id) throws UserNotExistsException;
    UserDto Add(UserContract userContract);

    UserDto Delete(UUID userId) throws UserNotExistsException;
}
