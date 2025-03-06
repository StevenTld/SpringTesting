
package com.dtos;

import lombok.Data;

/**
 * DTO pour la réponse après connexion
 */

@Data
public class StudentUserDTO {
    // Données étudiant
    private String firstName;
    private String lastName;
    private String mail;
    private String birthDate;
    private Long academicYearId;

    // Données utilisateur
    private String password; // Mot de passe pour le nouvel utilisateur
}