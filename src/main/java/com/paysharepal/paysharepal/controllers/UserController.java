package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserDto;
import com.paysharepal.paysharepal.infrastructure.dto.responses.UserResource;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResource>>> GetAll() {
        List<User> allUsers = userService.GetAll();

        List<EntityModel<UserResource>> userResources = allUsers.stream().map(
                user -> {
                    UserResource userResource = UserResource.of(user);
                    userResource.addSelfLink(user.getId());
                    userResource.addAllUsersLink();
                    return EntityModel.of(userResource);
                }
        ).toList();

        Link allUsersLink = WebMvcLinkBuilder.linkTo(UserController.class).withSelfRel();

        CollectionModel<EntityModel<UserResource>> response = CollectionModel.of(userResources, allUsersLink);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<UserResource>> GetById(@PathVariable UUID id) {
        Optional<User> userOpt = userService.Get(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserResource userResource = UserResource.of(user);
            userResource.addSelfLink(id);
            userResource.addAllUsersLink();
            return ResponseEntity.ok(EntityModel.of(userResource));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserResource>> Add(@RequestBody UserDto user) {
        User newUser = userService.Add(user);

        UserResource userResource = UserResource.of(newUser);
        userResource.addSelfLink(newUser.getId());
        userResource.addAllUsersLink();

        URI uri = WebMvcLinkBuilder.linkTo(UserController.class).slash("users").slash(newUser.getId()).toUri();

        return ResponseEntity.created(uri).body(userResource);
    }
}
