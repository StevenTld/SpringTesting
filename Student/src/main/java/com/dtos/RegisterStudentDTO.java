package com.dtos;

import lombok.Data;

/**
 * DTO pour l'inscription d'un étudiant avec création d'un compte utilisateur.
 */
@Data
public class RegisterStudentDTO {
    // Données user
    private String email;
    private String password;

    // Données studeznt
    private String firstName;
    private String lastName;
    private String birthDate;
    private Long academicYearId;
}