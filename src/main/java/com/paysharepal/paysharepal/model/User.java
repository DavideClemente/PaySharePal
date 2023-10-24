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

    private String passwordHash;

    @Lob
    private byte[] profileImageData;
    @ElementCollection
    @CollectionTable(name = "user_group_ids", joinColumns = @JoinColumn(name = "user_id"))
    private List<UUID> groupIds;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.passwordHash = password;
    }

    public boolean AddToGroup(UUID groupId) {
        return getGroupIds().add(groupId);
    }
}
