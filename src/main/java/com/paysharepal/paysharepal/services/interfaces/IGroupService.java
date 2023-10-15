package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.GroupNotExistsException;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGroupService {
    List<GroupDto> GetAll();
    Optional<GroupDto> GetById(UUID id);
    GroupDto AddUserToGroup(UUID groupId, UUID userId) throws UserNotExistsException, GroupNotExistsException;
}
