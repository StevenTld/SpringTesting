package com.services.impl;

import com.dtos.AcademicYearDto;
import com.dtos.CoreStudentInfoDTO;
import com.dtos.StudentDTO;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service Core qui agrège les informations des API Student et AcademicYear.
 */
@Service
public class CoreServiceImpl {

    private final StudentServiceImpl studentService;
    private final AcademicYearServiceImpl academicYearService;

    public CoreServiceImpl(StudentServiceImpl studentService, AcademicYearServiceImpl academicYearService) {
        this.studentService = studentService;
        this.academicYearService = academicYearService;
    }

    /**
     * Récupère les informations agrégées d'un étudiant.
     * L'agrégation combine les données de l'étudiant et de sa formation.
     *
     * @param studentId L'identifiant de l'étudiant.
     * @return Un CoreStudentInfoDTO contenant toutes les informations agrégées.
     */
    public CoreStudentInfoDTO getStudentInfo(Long studentId) {
        CoreStudentInfoDTO coreInfo = new CoreStudentInfoDTO();

        // Récupérer les informations de l'étudiant
        StudentDTO studentDTO = studentService.getStudentById(studentId);
        coreInfo.setStudent(studentDTO);

        // Si l'étudiant est associé à une année universitaire, récupérer les détails de la formation
        if (studentDTO != null && studentDTO.getAcademicYearId() != null) {
            AcademicYearDto academicYearDTO = academicYearService.getAcademicYearById(studentDTO.getAcademicYearId());
            coreInfo.setAcademicYear(academicYearDTO);
        } else {
            coreInfo.setAcademicYear(null);
        }

        // Pour l'instant, les listes pour groupes, formations et modules sont des stubs
        coreInfo.setGroups(List.of());
        coreInfo.setFormations(List.of());
        coreInfo.setModules(List.of());

        return coreInfo;
    }
}
