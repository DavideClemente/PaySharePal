package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.GroupNotExistsException;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import com.paysharepal.paysharepal.model.Group;
import com.paysharepal.paysharepal.repositories.IGroupRepository;
import com.paysharepal.paysharepal.services.interfaces.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;
    private final UserService userService;
    @Autowired
    public GroupService(IGroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public List<GroupDto> GetAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(
                GroupDto::of
        ).toList();
    }


    public Optional<GroupDto> GetById(UUID id) {
        Optional<Group> grouOptional = groupRepository.findById(id);
        return grouOptional.map(GroupDto::of);
    }

    public GroupDto AddGroup(GroupContract newGroup) {
        Group group = new Group(newGroup.name());
        groupRepository.save(group);
        return GroupDto.of(group);
    }

    public GroupDto AddUserToGroup(UUID groupId, UUID userId) throws UserNotExistsException, GroupNotExistsException {
        Optional<UserDto> userOptional = userService.Get(userId);
        if (userOptional.isEmpty())
            throw new UserNotExistsException("There is no User with id " + userId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty())
            throw new GroupNotExistsException("Group does not exist");
        Group group = groupOptional.get();
        group.AddUser(userId);
        groupRepository.save(group);


        // Add groupId to user

        return GroupDto.of(group);
    }
}
