package com.dtos;

import lombok.Data;
import java.util.List;

/**
 * DTO pour le transfert de données d'une année universitaire.
 */
@Data
public class AcademicYearDto {

    private Long id;

    // Nom de la formation
    private String formationName;

    // Taille maximale des groupes de TP
    private Integer tpGroupSize;

    // Taille maximale des groupes de TD
    private Integer tdGroupSize;

    // Nombre d'UE optionnelles à choisir
    private Integer optionalTeachingUnitsCount;

    // Email du responsable de la formation
    private String responsibleEmail;

    // Listes (à implémenter ultérieurement)
    private List<Object> groups;
    private List<Object> teachingUnits;
}
