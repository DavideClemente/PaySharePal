package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserDto;
import com.paysharepal.paysharepal.services.interfaces.IUserService;
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
@RequestMapping("api/v1/Users")
public class UserController {

    private final IUserService userService;
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> GetAll() {
        List<UserDto> allUsers = userService.GetAll();

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
    public ResponseEntity<EntityModel<UserDto>> GetById(@PathVariable UUID id) {
        Optional<UserDto> userResourceOptional = userService.Get(id);

        if (userResourceOptional.isPresent()) {
            UserDto userDto = userResourceOptional.get();
            userDto.AddSelfLink();
            userDto.AddAllUsersLink();
            return ResponseEntity.ok(EntityModel.of(userDto));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserDto>> Add(@RequestBody UserContract user) {
        UserDto userDto = userService.Add(user);

        userDto.AddSelfLink();
        userDto.AddAllUsersLink();

        URI uri = WebMvcLinkBuilder.linkTo(UserController.class).slash("users").slash(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }
}
