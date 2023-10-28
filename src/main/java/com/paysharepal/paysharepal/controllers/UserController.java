package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.infrastructure.exceptions.UserNotExistsException;
import com.paysharepal.paysharepal.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAll() {
        List<UserDto> allUsers = userService.getAll();

        List<EntityModel<UserDto>> userResources = allUsers.stream().map(
                user -> {
                    user.AddSelfLink();
                    return EntityModel.of(user);
                }
        ).toList();

        Link allUsersLink = WebMvcLinkBuilder.linkTo(UserController.class).withSelfRel();

        CollectionModel<EntityModel<UserDto>> response = CollectionModel.of(userResources, allUsersLink);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<UserDto>> getById(@PathVariable UUID id) {
        try {
            UserDto userDto = userService.getById(id);
            userDto.AddSelfLink();
            userDto.AddAllUsersLink();
            return ResponseEntity.ok(EntityModel.of(userDto));
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//    public ResponseEntity<EntityModel<UserDto>> Add(@RequestBody UserContract user) {
//        UserDto userDto = userService.Add(user);
//
//        userDto.AddSelfLink();
//        userDto.AddAllUsersLink();
//
//        URI uri = WebMvcLinkBuilder.linkTo(UserController.class).slash("users").slash(userDto.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(userDto);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<EntityModel<UserDto>> deleteUser(@PathVariable UUID id) {
        try {
            UserDto userDto = userService.delete(id);
            return ResponseEntity.ok(EntityModel.of(userDto));
        } catch (UserNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
