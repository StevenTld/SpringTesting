package com.controllers;

import com.dtos.StudentDTO;
import com.dtos.StudentUserDTO;
import com.dtos.ValidationDTO;
import com.services.impl.StudentServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des étudiants.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    /**
     * GET /students
     * Récupère tous les étudiants.
     */
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * POST /students
     * Crée un nouvel étudiant.
     */
    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentUserDTO dto) {
        return studentService.createStudentWithUser(dto);
    }

    /**
     * GET /students/{id}
     * Récupère un étudiant par son numéro.
     */
    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable("id") Long studentNumber) {
        return studentService.getStudentById(studentNumber);
    }

    /**
     * DELETE /students/{id}
     * Supprime un étudiant par son numéro.
     */
    @DeleteMapping("/{id}")
    public Boolean deleteStudent(@PathVariable("id") Long studentNumber) {
        return studentService.deleteStudent(studentNumber);
    }

    /**
     * PUT /students/{id}
     * Met à jour un étudiant existant.
     */
    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable("id") Long studentNumber, @RequestBody StudentDTO dto) {
        return studentService.updateStudent(studentNumber, dto);
    }

    // Endpoints pour la gestion des inscriptions

    @GetMapping("/{id}/groupes")
    public List<Long> getStudentGroups(@PathVariable("id") Long studentNumber) {
        return studentService.getStudentGroups(studentNumber);
    }

    @GetMapping("/{id}/formations")
    public List<Long> getStudentFormations(@PathVariable("id") Long studentNumber) {
        return studentService.getStudentFormations(studentNumber);
    }

    @GetMapping("/{id}/ues")
    public List<Long> getStudentModules(@PathVariable("id") Long studentNumber) {
        return studentService.getStudentModules(studentNumber);
    }

    @PostMapping("/{id}/groupes/{gid}")
    public Boolean addStudentToGroup(@PathVariable("id") Long studentNumber,
                                     @PathVariable("gid") Long groupId) {
        return studentService.addStudentToGroup(studentNumber, groupId);
    }

    @DeleteMapping("/{id}/groupes/{gid}")
    public Boolean removeStudentFromGroup(@PathVariable("id") Long studentNumber,
                                          @PathVariable("gid") Long groupId) {
        return studentService.removeStudentFromGroup(studentNumber, groupId);
    }

    @PutMapping("/{id}/groupes/{gid}")
    public Boolean validateInscriptionGroup(@PathVariable("id") Long studentNumber,
                                            @PathVariable("gid") Long groupId,
                                            @RequestBody ValidationDTO validation) {
        return studentService.validateStudentGroup(studentNumber, groupId, validation.isAccept());
    }

    @PostMapping("/{id}/formations/{fid}")
    public Boolean addStudentToFormation(@PathVariable("id") Long studentNumber,
                                         @PathVariable("fid") Long formationId) {
        return studentService.addStudentToFormation(studentNumber, formationId);
    }

    @DeleteMapping("/{id}/formations/{fid}")
    public Boolean removeStudentFromFormation(@PathVariable("id") Long studentNumber,
                                              @PathVariable("fid") Long formationId) {
        return studentService.removeStudentFromFormation(studentNumber, formationId);
    }

    @PutMapping("/{id}/formations/{fid}")
    public Boolean validateInscriptionFormation(@PathVariable("id") Long studentNumber,
                                                @PathVariable("fid") Long formationId,
                                                @RequestBody ValidationDTO validation) {
        return studentService.validateStudentFormation(studentNumber, formationId, validation.isAccept());
    }

    @PostMapping("/{id}/ues/{ueid}")
    public Boolean addStudentToUE(@PathVariable("id") Long studentNumber,
                                  @PathVariable("ueid") Long ueId) {
        return studentService.addStudentToUE(studentNumber, ueId);
    }

    @DeleteMapping("/{id}/ues/{ueid}")
    public Boolean removeStudentFromUE(@PathVariable("id") Long studentNumber,
                                       @PathVariable("ueid") Long ueId) {
        return studentService.removeStudentFromUE(studentNumber, ueId);
    }

    @PutMapping("/{id}/ues/{ueid}")
    public Boolean validateInscriptionUE(@PathVariable("id") Long studentNumber,
                                         @PathVariable("ueid") Long ueId,
                                         @RequestBody ValidationDTO validation) {
        return studentService.validateStudentUE(studentNumber, ueId, validation.isAccept());
    }
}
