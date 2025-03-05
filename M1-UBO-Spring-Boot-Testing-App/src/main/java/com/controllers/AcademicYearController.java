package com.controllers;

import com.dtos.AcademicYearDto;
import com.services.impl.AcademicYearServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des années universitaires (formations).
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/academicyears")
public class AcademicYearController {

    private final AcademicYearServiceImpl academicYearService;

    public AcademicYearController(AcademicYearServiceImpl academicYearService) {
        this.academicYearService = academicYearService;
    }

    /**
     * GET /academicyears
     * Récupère toutes les formations.
     */
    @GetMapping
    public List<AcademicYearDto> getAllAcademicYears() {
        return academicYearService.getAllAcademicYears();
    }

    /**
     * GET /academicyears/{id}
     * Récupère une formation par son ID.
     */
    @GetMapping("/{id}")
    public AcademicYearDto getAcademicYearById(@PathVariable Long id) {
        return academicYearService.getAcademicYearById(id);
    }

    /**
     * POST /academicyears
     * Crée une nouvelle formation.
     */
    @PostMapping
    public AcademicYearDto createAcademicYear(@RequestBody AcademicYearDto dto) {
        return academicYearService.createAcademicYear(dto);
    }

    /**
     * PUT /academicyears/{id}
     * Met à jour une formation existante.
     */
    @PutMapping("/{id}")
    public AcademicYearDto updateAcademicYear(@PathVariable Long id, @RequestBody AcademicYearDto dto) {
        return academicYearService.updateAcademicYear(id, dto);
    }

    /**
     * DELETE /academicyears/{id}
     * Supprime une formation par son ID.
     */
    @DeleteMapping("/{id}")
    public Boolean deleteAcademicYear(@PathVariable Long id) {
        return academicYearService.deleteAcademicYear(id);
    }
}
