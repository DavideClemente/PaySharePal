package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupContract;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.GroupUserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.GroupDto;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("Groups")
public class GroupController {

    private final GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<GroupDto>>> GetAll() {
        List<GroupDto> allGroups = groupService.GetAll();

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
    public ResponseEntity<EntityModel<GroupDto>> GetById(@PathVariable UUID id) {
        Optional<GroupDto> groupDtoOptional = groupService.GetById(id);

        if(groupDtoOptional.isPresent()) {
            GroupDto dto = groupDtoOptional.get();
            dto.AddSelfLink();
            dto.AddAllGroupsLink();
            return ResponseEntity.ok(EntityModel.of(dto));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<GroupDto>> Add(@RequestBody GroupContract body) {
        GroupDto groupDto = groupService.AddGroup(body);
        groupDto.AddSelfLink();
        groupDto.AddAllGroupsLink();

        URI uri = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash("users").slash(groupDto.getId()).toUri();
        return ResponseEntity.created(uri).body(groupDto);
    }

    @PostMapping("{groupId}/Users")
    public ResponseEntity<EntityModel<GroupDto>> AddUser(@PathVariable UUID groupId, @RequestBody GroupUserContract user) {
        try {
            GroupDto groupDto = groupService.AddUserToGroup(groupId, user.userId());
            groupDto.AddSelfLink();
            groupDto.AddAllGroupsLink();

            return ResponseEntity.ok(EntityModel.of(groupDto));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}/Users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> GetUsers() {
        throw new UnsupportedOperationException();
    }
}
