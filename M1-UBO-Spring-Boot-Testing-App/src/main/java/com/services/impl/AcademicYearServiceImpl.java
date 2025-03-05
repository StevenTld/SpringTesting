package com.services.impl;

import com.dtos.AcademicYearDto;
import com.entities.AcademicYear;
import com.mappers.AcademicYearMapper;
import com.repositories.AcademicYearRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémente la logique métier pour AcademicYear (années universitaires).
 */
@Service
public class AcademicYearServiceImpl {

    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
    }

    /**
     * Récupère la liste de toutes les formations.
     */
    public List<AcademicYearDto> getAllAcademicYears() {
        return academicYearRepository.findAll().stream()
                .map(academicYearMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une formation par son ID.
     */
    public AcademicYearDto getAcademicYearById(Long id) {
        return academicYearRepository.findById(id)
                .map(academicYearMapper::toDto)
                .orElse(null);
    }

    /**
     * Crée une nouvelle formation.
     */
    public AcademicYearDto createAcademicYear(AcademicYearDto dto) {
        AcademicYear entity = academicYearMapper.toEntity(dto);
        AcademicYear saved = academicYearRepository.save(entity);
        return academicYearMapper.toDto(saved);
    }

    /**
     * Met à jour une formation existante.
     */
    public AcademicYearDto updateAcademicYear(Long id, AcademicYearDto dto) {
        return academicYearRepository.findById(id)
                .map(existing -> {
                    existing.setFormationName(dto.getFormationName());
                    existing.setTpGroupSize(dto.getTpGroupSize());
                    existing.setTdGroupSize(dto.getTdGroupSize());
                    existing.setOptionalTeachingUnitsCount(dto.getOptionalTeachingUnitsCount());
                    existing.setResponsibleEmail(dto.getResponsibleEmail());
                    AcademicYear updated = academicYearRepository.save(existing);
                    return academicYearMapper.toDto(updated);
                })
                .orElse(null);
    }

    /**
     * Supprime une formation par son ID.
     */
    public boolean deleteAcademicYear(Long id) {
        if (academicYearRepository.existsById(id)) {
            academicYearRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
