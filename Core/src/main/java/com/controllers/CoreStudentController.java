package com.controllers;


import com.dtos.StudentDTO;
import com.dtos.StudentUserDTO;
import com.dtos.LoginResponseDTO;

import com.services.impl.CoreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des étudiants via l'API Core.
 * Ce contrôleur sert d'intermédiaire entre le client et les services Student et Auth.
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class CoreStudentController {

    private final CoreServiceImpl coreService;

    public CoreStudentController(CoreServiceImpl coreService) {
        this.coreService = coreService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody StudentUserDTO dto) {
        try {
            StudentDTO createdStudent = coreService.registerStudent(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}