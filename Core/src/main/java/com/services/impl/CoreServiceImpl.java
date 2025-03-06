package com.services.impl;

import com.clients.AuthClient;
import com.clients.StudentClient;
import com.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CoreServiceImpl {

    private final AuthClient authClient;
    private final StudentClient studentClient;

    public CoreServiceImpl(AuthClient authClient, StudentClient studentClient) {
        this.authClient = authClient;
        this.studentClient = studentClient;
    }

    /**
     * Enregistre un nouvel étudiant avec un compte utilisateur associé.
     */
    @Transactional
    public StudentDTO registerStudent(StudentUserDTO dto) {

        Long commonId = generateUniqueId();
        // 1. Créer l'utilisateur via Auth service
        UserDTO userDTO = new UserDTO();
        userDTO.setId(commonId);
        userDTO.setEmail(dto.getMail());
        userDTO.setPassword(dto.getPassword());

        UserDTO createdUser = authClient.register(userDTO);

        // 2. Créer l'étudiant via Student service
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentNumber(commonId);
        studentDTO.setFirstName(dto.getFirstName());
        studentDTO.setLastName(dto.getLastName());
        studentDTO.setMail(dto.getMail());
        studentDTO.setBirthDate(dto.getBirthDate());
        studentDTO.setAcademicYearId(dto.getAcademicYearId());

        return studentClient.createStudent(studentDTO);
    }

    // Méthode pour générer un ID unique
    private Long generateUniqueId() {
        // Option 1: Utilisez un UUID converti en Long
        return Math.abs(UUID.randomUUID().getMostSignificantBits());

        // Option 2: Utilisez un service de séquence centralisé
        // return sequenceService.getNextId();
    }
}