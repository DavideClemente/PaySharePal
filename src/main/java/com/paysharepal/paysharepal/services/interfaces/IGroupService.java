package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.GroupNotExistsException;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGroupService {
    List<GroupDto> getAll();
    Optional<GroupDto> getById(UUID id);

    GroupDto addGroup(GroupContract newGroup);

    GroupDto deleteGroup(UUID groupId) throws GroupNotExistsException;
    GroupDto addImage(UUID groupId, MultipartFile file) throws GroupNotExistsException, IOException;
    byte[] getImage(UUID groupId) throws GroupNotExistsException;
    GroupDto addUserToGroup(UUID groupId, UUID userId) throws UserNotExistsException, GroupNotExistsException;
}
