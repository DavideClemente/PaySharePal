package com.paysharepal.paysharepal.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "Payments")
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID destination;
    private UUID source;
    private float amount;
    private boolean paid;
}
