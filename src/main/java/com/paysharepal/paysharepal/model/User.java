package com.paysharepal.paysharepal.model;

import jakarta.persistence.*;
import lombok.*;

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

    public User(String name) {
        this.name = name;
    }
}
