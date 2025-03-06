package com.controllers;

import com.dtos.CoreStudentInfoDTO;
import com.dtos.StudentDTO;
import com.dtos.StudentUserDTO;
import com.dtos.LoginResponseDTO;
import com.services.impl.AuthServiceImpl;
import com.services.impl.StudentServiceImpl;
import com.services.impl.CoreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des étudiants via l'API Core.
 * Ce contrôleur sert d'intermédiaire entre le client et les services Student et Auth.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/students")
public class CoreStudentController {

    private final StudentServiceImpl studentService;
    private final AuthServiceImpl authService;
    private final CoreServiceImpl coreService;

    public CoreStudentController(StudentServiceImpl studentService, AuthServiceImpl authService, CoreServiceImpl coreService) {
        this.studentService = studentService;
        this.authService = authService;
        this.coreService = coreService;
    }

    /**
     * POST /api/students/register
     * Enregistre un nouvel étudiant en créant à la fois un compte utilisateur et un profil étudiant.
     */
    @PostMapping("/register")
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody StudentUserDTO dto) {
        try {
            // Utilise le service Core pour gérer cette opération complexe
            StudentDTO createdStudent = coreService.registerStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }


}