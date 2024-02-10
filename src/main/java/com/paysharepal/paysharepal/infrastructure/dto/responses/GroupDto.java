package com.paysharepal.paysharepal.infrastructure.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.paysharepal.paysharepal.model.Group;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto extends EntityModel<GroupDto> {
    private UUID id;
    private String name;
    private List<UUID> userIds;
    public void AddSelfLink() {
        add(Link.of("/groups/"+getId()).withSelfRel());
    }

    public void AddAllGroupsLink() {
        String groupsLink = "/groups";
        add(Link.of(groupsLink, "all-groups"));
    }

    public static GroupDto of(Group group) {
        return new GroupDto(group.getId(), group.getName(), group.getUserIds());
    }
}
