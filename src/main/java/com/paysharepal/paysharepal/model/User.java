package com.paysharepal.paysharepal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "Users")
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    @ElementCollection
    @CollectionTable(name = "user_group_ids", joinColumns = @JoinColumn(name = "user_id"))
    private List<UUID> groupIds;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public boolean AddToGroup(UUID groupId) {
        return getGroupIds().add(groupId);
    }
}
