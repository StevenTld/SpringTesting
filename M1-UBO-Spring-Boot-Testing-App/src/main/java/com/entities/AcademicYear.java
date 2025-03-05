package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Représente une année universitaire (formation) en base de données.
 * Champs basés sur les exigences du projet.
 */
@Entity
@Data
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom de la formation (ex: "Master 1 TIIL-A")
    private String formationName;

    // Taille maximale des groupes de TP
    private Integer tpGroupSize;

    // Taille maximale des groupes de TD
    private Integer tdGroupSize;

    // Nombre d'UE optionnelles que doit choisir un étudiant
    private Integer optionalTeachingUnitsCount;

    // Email du responsable de la formation
    private String responsibleEmail;
}
