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
    List<GroupDto> GetAll();
    Optional<GroupDto> GetById(UUID id);

    GroupDto AddGroup(GroupContract newGroup);

    GroupDto DeleteGroup(UUID groupId) throws GroupNotExistsException;
    GroupDto AddImage(UUID groupId, MultipartFile file) throws GroupNotExistsException, IOException;
    byte[] GetImage(UUID groupId) throws GroupNotExistsException;
    GroupDto AddUserToGroup(UUID groupId, UUID userId) throws UserNotExistsException, GroupNotExistsException;
}
