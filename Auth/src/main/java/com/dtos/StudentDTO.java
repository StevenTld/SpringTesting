package com.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO pour le transfert des données d'un étudiant.
 */
@Data
public class StudentDTO {

    private Long studentNumber;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom de famille est obligatoire")
    private String lastName;

    @NotBlank(message = "Le mail est obligatoire")
    private String mail;

    // Date de naissance sous forme de chaîne (format yyyy-MM-dd)
    private String birthDate;

    // Identifiant de l'année universitaire à laquelle l'étudiant est inscrit (optionnel)
    private Long academicYearId;
}
