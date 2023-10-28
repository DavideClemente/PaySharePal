package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import com.paysharepal.paysharepal.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserDto> getAll();

    UserDto getById(UUID id) throws UserNotExistsException;

    User getByEmail(String email);

    UserDto add(UserContract userContract);

    UserDto delete(UUID userId) throws UserNotExistsException;
}
