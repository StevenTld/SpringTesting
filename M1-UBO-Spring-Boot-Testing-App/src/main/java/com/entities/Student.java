package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDate;

/**
 * Représente un étudiant dans le système.
 */
@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNumber; // Identifiant unique de l'étudiant

    private String lastName;    // Nom de famille
    private String firstName;   // Prénom
    private String mail;        // Adresse mail
    private LocalDate birthDate; // Date de naissance (format yyyy-MM-dd)

    /**
     * Association Many-to-One avec AcademicYear.
     * Un étudiant est inscrit dans une formation.
     */
    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;
}
