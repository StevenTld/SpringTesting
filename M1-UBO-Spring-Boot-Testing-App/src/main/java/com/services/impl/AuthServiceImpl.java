package com.services.impl;

import com.dtos.LoginRequestDTO;
import com.dtos.LoginResponseDTO;
import com.entities.Student;
import com.entities.User;
import com.repositories.StudentRepository;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service d'authentification ultra simplifié
 */
@Service
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Authentification d'un utilisateur
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Chercher l'utilisateur par email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        // Vérifier le mot de passe (en clair)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Créer la réponse
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());



            Student student = user.getStudent();
            response.setFirstName(student.getFirstName());
            response.setLastName(student.getLastName());
            response.setStudentNumber(student.getStudentNumber());

            if (student.getAcademicYear() != null) {
                response.setAcademicYearId(student.getAcademicYear().getId());
                response.setFormationName(student.getAcademicYear().getFormationName());
            }


        return response;
    }
}