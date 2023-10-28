package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupContract;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupUserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.GroupNotExistsException;
import com.paysharepal.paysharepal.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<GroupDto>>> getAll() {
        List<GroupDto> allGroups = groupService.getAll();

        List<EntityModel<GroupDto>> groupResources = allGroups.stream().map(
                groupDto -> {
                    groupDto.AddSelfLink();
                    return EntityModel.of(groupDto);
                }
        ).toList();

        Link allGroupsLink = WebMvcLinkBuilder.linkTo(GroupController.class).withSelfRel();

        CollectionModel<EntityModel<GroupDto>> response = CollectionModel.of(groupResources, allGroupsLink);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<GroupDto>> getById(@PathVariable UUID id) {
        Optional<GroupDto> groupDtoOptional = groupService.getById(id);

        if(groupDtoOptional.isPresent()) {
            GroupDto dto = groupDtoOptional.get();
            dto.AddSelfLink();
            dto.AddAllGroupsLink();
            return ResponseEntity.ok(EntityModel.of(dto));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<GroupDto>> add(@RequestBody GroupContract body) {
        GroupDto groupDto = groupService.addGroup(body);
        groupDto.AddSelfLink();
        groupDto.AddAllGroupsLink();

        URI uri = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash("users").slash(groupDto.getId()).toUri();
        return ResponseEntity.created(uri).body(groupDto);
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<EntityModel<GroupDto>> delete(@PathVariable UUID groupId) {
        try {
            GroupDto groupDto = groupService.deleteGroup(groupId);
            return ResponseEntity.ok(EntityModel.of(groupDto));
        } catch (GroupNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{groupId}/users")
    public ResponseEntity<EntityModel<GroupDto>> addUser(@PathVariable UUID groupId, @RequestBody GroupUserContract user) {
        try {
            GroupDto groupDto = groupService.addUserToGroup(groupId, user.userId());
            groupDto.AddSelfLink();
            groupDto.AddAllGroupsLink();

            return ResponseEntity.ok(EntityModel.of(groupDto));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{groupId}/image")
    public ResponseEntity<?> getImage(@PathVariable UUID groupId) {
        try {
            byte[] image = groupService.getImage(groupId);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
        } catch (GroupNotExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{groupId}/image")
    public ResponseEntity<?> uploadImage(@PathVariable UUID groupId, @RequestParam("image") MultipartFile file) throws GroupNotExistsException, IOException {
        GroupDto response = groupService.addImage(groupId, file);

        return ResponseEntity.ok(EntityModel.of(response));
    }

    @GetMapping("{groupId}/users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getUsers(@PathVariable String groupId) {
        throw new UnsupportedOperationException();
    }
}
