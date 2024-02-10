package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.GroupNotExistsException;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import com.paysharepal.paysharepal.infrastructure.utils.ImageUtils;
import com.paysharepal.paysharepal.model.Group;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.repositories.IGroupRepository;
import com.paysharepal.paysharepal.repositories.IUserRepository;
import com.paysharepal.paysharepal.services.interfaces.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;

    public List<GroupDto> getAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(
                GroupDto::of
        ).toList();
    }


    public Optional<GroupDto> getById(UUID id) {
        Optional<Group> grouOptional = groupRepository.findById(id);
        return grouOptional.map(GroupDto::of);
    }

    public GroupDto addGroup(GroupContract newGroup) {
        Group group = new Group(newGroup.name());
        groupRepository.save(group);
        return GroupDto.of(group);
    }

    @Override
    public GroupDto deleteGroup(UUID groupId) throws GroupNotExistsException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty())
            throw new GroupNotExistsException(String.format("There is no group with id %s", groupId));
        Group group = groupOptional.get();
        groupRepository.deleteById(groupId);
        return GroupDto.of(group);
    }

    public GroupDto addImage(UUID groupId, MultipartFile file) throws GroupNotExistsException, IOException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty())
            throw new GroupNotExistsException(String.format("There is no group with id %s", groupId));
        Group group = groupOptional.get();
        group.setImageData(ImageUtils.CompressImage(file.getBytes()));

        groupRepository.save(group);
        return GroupDto.of(group);
    }

    public byte[] getImage(UUID groupId) throws GroupNotExistsException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty())
            throw new GroupNotExistsException(String.format("There is no group with id %s", groupId));

        Group group = groupOptional.get();
        byte[] data = group.getImageData();
        return ImageUtils.decompressImage(data);
    }

    public GroupDto addUserToGroup(UUID groupId, UUID userId) throws UserNotExistsException, GroupNotExistsException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new UserNotExistsException(String.format("There is no user with id %s", userId));
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty())
            throw new GroupNotExistsException(String.format("There is no group with id %s", groupId));
        Group group = groupOptional.get();
        group.AddUser(userId);
        groupRepository.save(group);

        // Add group to user
        User user = userOptional.get();
        user.addToGroup(groupId);
        userRepository.save(user);

        return GroupDto.of(group);
    }
}
