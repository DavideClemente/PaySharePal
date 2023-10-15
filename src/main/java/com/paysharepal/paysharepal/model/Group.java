package com.paysharepal.paysharepal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Groups")
@Table(name = "Groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "group_user_ids", joinColumns = @JoinColumn(name = "group_id"))
    private List<UUID> userIds;

    public Group(String name) {
        this.name = name;
        this.userIds = new ArrayList<>();
    }

    public boolean AddUser(UUID userId) {
        return this.getUserIds().add(userId);
}
}
