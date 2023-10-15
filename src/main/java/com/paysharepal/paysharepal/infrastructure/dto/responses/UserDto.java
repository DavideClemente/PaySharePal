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
public class UserDto extends EntityModel<UserDto> {
    private UUID id;
    private String name;
    private String email;

    public void AddSelfLink() {
        add(Link.of("/users/"+getId()).withSelfRel());
    }

    public void AddAllUsersLink() {
        String usersLink = "/users";
        add(Link.of(usersLink, "all-users"));
    }

    public static UserDto of(@NotNull User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
