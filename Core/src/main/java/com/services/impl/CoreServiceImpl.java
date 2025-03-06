package com.example.services;

import com.clients.AuthClient;
import com.clients.StudentClient;
import com.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoreService {

    private final AuthClient authClient;
    private final StudentClient studentClient;

    public CoreService(AuthClient authClient, StudentClient studentClient) {
        this.authClient = authClient;
        this.studentClient = studentClient;
    }

    /**
     * Enregistre un nouvel étudiant avec un compte utilisateur associé.
     */
    @Transactional
    public StudentDTO registerStudent(StudentUserDTO dto) {
        // 1. Créer l'utilisateur via Auth service
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(dto.getMail());
        userDTO.setPassword(dto.getPassword());
        userDTO.setRole("STUDENT");

        UserDTO createdUser = authClient.register(userDTO);

        // 2. Créer l'étudiant via Student service
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(dto.getFirstName());
        studentDTO.setLastName(dto.getLastName());
        studentDTO.setMail(dto.getMail());
        studentDTO.setBirthDate(dto.getBirthDate());
        studentDTO.setAcademicYearId(dto.getAcademicYearId());
        studentDTO.setUserId(createdUser.getId());

        return studentClient.createStudent(studentDTO);
    }

    /**
     * Récupère les informations complètes d'un étudiant, y compris sa formation académique.
     */
    public CoreStudentInfoDTO getStudentInfo(Long studentId) {
        StudentDTO student = studentClient.getStudentById(studentId);

        CoreStudentInfoDTO infoDTO = new CoreStudentInfoDTO();
        infoDTO.setStudent(student);

        // Si l'étudiant a une formation académique, récupérez ses détails
        if (student.getAcademicYearId() != null) {
            // Supposons que vous ayez une méthode pour récupérer les détails de la formation
            // AcademicYearDto academicYear = academicYearClient.getAcademicYearById(student.getAcademicYearId());
            // infoDTO.setAcademicYear(academicYear);
        }

        return infoDTO;
    }
}