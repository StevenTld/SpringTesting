package com.dtos;

import lombok.Data;
import java.util.List;

/**
 * DTO d'agrégation des informations d'un étudiant.
 * Contient les informations de base de l'étudiant,
 */
@Data
public class CoreStudentInfoDTO {
    private StudentDTO student;         // Informations de l'étudiant
    private AcademicYearDto academicYear; // Informations de la formation associée
}
