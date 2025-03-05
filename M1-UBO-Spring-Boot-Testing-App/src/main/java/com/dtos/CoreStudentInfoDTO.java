package com.dtos;

import lombok.Data;
import java.util.List;

/**
 * DTO d'agrégation des informations d'un étudiant.
 * Contient les informations de base de l'étudiant,
 * celles de l'année universitaire à laquelle il est inscrit,
 * et des listes (stubs) pour groupes, formations et modules.
 */
@Data
public class CoreStudentInfoDTO {
    private StudentDTO student;         // Informations de l'étudiant
    private AcademicYearDto academicYear; // Informations de la formation associée
    private List<Long> groups;          // Stub : liste des IDs des groupes
    private List<Long> formations;      // Stub : liste des IDs des formations
    private List<Long> modules;         // Stub : liste des IDs des modules (UE)
}
