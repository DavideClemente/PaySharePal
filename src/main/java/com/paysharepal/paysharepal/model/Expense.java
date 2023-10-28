package com.paysharepal.paysharepal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "Payments")
@Table(name = "Payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID payer;
    @ElementCollection
    @CollectionTable(name = "payment_participant_ids", joinColumns = @JoinColumn(name = "payment_id"))
    private List<UUID> participants;

    private float totalPrice;

    private float eachPrice;
}
