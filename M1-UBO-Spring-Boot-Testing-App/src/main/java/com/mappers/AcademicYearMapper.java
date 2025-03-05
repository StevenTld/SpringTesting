package com.mappers;

import com.dtos.AcademicYearDto;
import com.entities.AcademicYear;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Mapper pour convertir entre AcademicYear et AcademicYearDto.
 */
@Component
public class AcademicYearMapper {

    // Convertit l'entité en DTO
    public AcademicYearDto toDto(AcademicYear academicYear) {
        if (academicYear == null) return null;
        AcademicYearDto dto = new AcademicYearDto();
        dto.setId(academicYear.getId());
        dto.setFormationName(academicYear.getFormationName());
        dto.setTpGroupSize(academicYear.getTpGroupSize());
        dto.setTdGroupSize(academicYear.getTdGroupSize());
        dto.setOptionalTeachingUnitsCount(academicYear.getOptionalTeachingUnitsCount());
        dto.setResponsibleEmail(academicYear.getResponsibleEmail());
        dto.setGroups(List.of());
        dto.setTeachingUnits(List.of());
        return dto;
    }

    // Convertit le DTO en entité
    public AcademicYear toEntity(AcademicYearDto dto) {
        if (dto == null) return null;
        AcademicYear academicYear = new AcademicYear();
        if (dto.getId() != null) {
            academicYear.setId(dto.getId());
        }
        academicYear.setFormationName(dto.getFormationName());
        academicYear.setTpGroupSize(dto.getTpGroupSize());
        academicYear.setTdGroupSize(dto.getTdGroupSize());
        academicYear.setOptionalTeachingUnitsCount(dto.getOptionalTeachingUnitsCount());
        academicYear.setResponsibleEmail(dto.getResponsibleEmail());
        return academicYear;
    }
}
