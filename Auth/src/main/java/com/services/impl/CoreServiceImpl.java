package com.services.impl;

import com.dtos.*;
import com.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service Core qui agrège les informations des API Student et AcademicYear.
 */
@Service
public class CoreServiceImpl {

    private final StudentServiceImpl studentService;
    private final AcademicYearServiceImpl academicYearService;

    private final AuthServiceImpl authService;
    private final UserRepository userRepository;
    public CoreServiceImpl(StudentServiceImpl studentService, AcademicYearServiceImpl academicYearService, AuthServiceImpl authService, UserRepository userRepository) {
        this.studentService = studentService;
        this.academicYearService = academicYearService;
        this.authService = authService;
        this.userRepository = userRepository;
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


        return coreInfo;
    }

    /**
     * Enregistre un nouvel étudiant en créant à la fois un compte utilisateur et un profil étudiant.
     */
    @Transactional
    public StudentDTO registerStudent(StudentUserDTO dto) {
        // 1. Vérifier si l'email existe déjà

        if (userRepository.existsByEmail(dto.getMail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // 2. Créer le compte utilisateur
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(dto.getMail());
        userDTO.setPassword(dto.getPassword());

        UserDTO createdUser = authService.register(userDTO);

        // 3. Créer l'étudiant
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(dto.getFirstName());
        studentDTO.setLastName(dto.getLastName());
        studentDTO.setMail(dto.getMail());
        studentDTO.setBirthDate(dto.getBirthDate());
        studentDTO.setAcademicYearId(dto.getAcademicYearId());

        // 4. Associer l'étudiant au compte utilisateur
        return studentService.createStudent(studentDTO);
    }
}
