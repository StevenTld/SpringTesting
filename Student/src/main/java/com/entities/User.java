package com.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_user") // Renommage de la table pour éviter le conflit avec le mot-clé MySQL
@Data
public class User {

    @Id

    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;




}