package com.controllers;

import com.dtos.CoreStudentInfoDTO;
import com.services.impl.CoreServiceImpl;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour l'API Core qui agrège les informations
 * provenant des API Student et AcademicYear.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/core")
public class CoreController {

    private final CoreServiceImpl coreService;

    public CoreController(CoreServiceImpl coreService) {
        this.coreService = coreService;
    }

    /**
     * GET /core/student-info/{id}
     * Récupère les informations agrégées d'un étudiant,
     * incluant ses informations personnelles et celles de sa formation.
     *
     * @param id L'identifiant de l'étudiant.
     * @return Un CoreStudentInfoDTO contenant l'agrégation.
     */
    @GetMapping("/student-info/{id}")
    public CoreStudentInfoDTO getStudentInfo(@PathVariable Long id) {
        return coreService.getStudentInfo(id);
    }
}
