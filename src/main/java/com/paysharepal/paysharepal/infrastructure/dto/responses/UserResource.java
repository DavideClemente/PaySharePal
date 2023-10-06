package com.paysharepal.paysharepal.infrastructure.dto.responses;

import com.paysharepal.paysharepal.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResource extends EntityModel<UserResource> {
    private UUID id;
    private String name;

    public void addSelfLink(UUID userId) {
        add(Link.of("/users/"+userId).withSelfRel());
    }

    public void addAllUsersLink() {
        String usersLink = "/users";
        add(Link.of(usersLink, "all-users"));
    }

    public static UserResource of(@NotNull User user) {
        UserResource resource = new UserResource();
        resource.id = user.getId();
        resource.name = user.getName();
        return resource;
    }
}
