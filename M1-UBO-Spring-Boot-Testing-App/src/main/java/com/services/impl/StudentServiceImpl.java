package com.services.impl;

import com.dtos.StudentDTO;
import com.dtos.UserDTO;
import com.dtos.StudentUserDTO;
import com.entities.AcademicYear;
import com.entities.User;
import com.entities.Student;
import com.mappers.StudentMapper;
import com.mappers.UserMapper;
import com.repositories.AcademicYearRepository;
import com.repositories.StudentRepository;
import com.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des étudiants.
 */
@Service
public class StudentServiceImpl {

    private final StudentRepository studentRepository;
    private final AcademicYearRepository academicYearRepository;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;



    public StudentServiceImpl(StudentRepository studentRepository, AcademicYearRepository academicYearRepository, StudentMapper studentMapper, UserRepository userRepository, UserMapper userMapper) {
        this.studentRepository = studentRepository;
        this.academicYearRepository = academicYearRepository;
        this.studentMapper = studentMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Récupère tous les étudiants.
     */
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un étudiant par son numéro.
     */
    public StudentDTO getStudentById(Long studentNumber) {
        return studentRepository.findById(studentNumber)
                .map(studentMapper::toDTO)
                .orElse(null);
    }

    /**
     * Crée un nouvel étudiant avec un compte utilisateur associé.
     */
    @Transactional
    public StudentDTO createStudentWithUser(StudentUserDTO dto) {
        // Vérifier si l'email est déjà utilisé
        if (userRepository.existsByEmail(dto.getMail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Créer le UserDTO à partir du StudentUserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(dto.getMail());
        userDTO.setPassword(dto.getPassword());

        // Utiliser le mapper pour convertir en entité User
        User user = userMapper.toEntity(userDTO);

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Créer le StudentDTO à partir du StudentUserDTO
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(dto.getFirstName());
        studentDTO.setLastName(dto.getLastName());
        studentDTO.setMail(dto.getMail());
        studentDTO.setBirthDate(dto.getBirthDate());
        studentDTO.setAcademicYearId(dto.getAcademicYearId());

        // Utiliser le mapper pour convertir en entité Student
        Student student = studentMapper.toEntity(studentDTO);

        // Associer à l'utilisateur et sauvegarder
        student.setUser(savedUser);
        Student savedStudent = studentRepository.save(student);

        // Établir la relation bidirectionnelle
        savedUser.setStudent(savedStudent);
        userRepository.save(savedUser);

        // Utiliser le mapper pour convertir en DTO pour le retour
        return studentMapper.toDTO(savedStudent);
    }

    /**
     * Met à jour un étudiant existant.
     */
    public StudentDTO updateStudent(Long studentNumber, StudentDTO dto) {
        return studentRepository.findById(studentNumber)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setMail(dto.getMail());
                    if (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) {
                        existing.setBirthDate(java.time.LocalDate.parse(dto.getBirthDate(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                    if (dto.getAcademicYearId() != null) {
                        Optional<AcademicYear> formation = academicYearRepository.findById(dto.getAcademicYearId());
                        formation.ifPresent(existing::setAcademicYear);
                    }
                    Student updated = studentRepository.save(existing);
                    return studentMapper.toDTO(updated);
                })
                .orElse(null);
    }

    /**
     * Supprime un étudiant.
     */
    public boolean deleteStudent(Long studentNumber) {
        if (studentRepository.existsById(studentNumber)) {
            studentRepository.deleteById(studentNumber);
            return true;
        }
        return false;
    }

    // Méthodes pour les inscriptions (stubs ou implémentation réelle pour formation)

    /**
     * Récupère la liste des formations auxquelles l'étudiant est inscrit.
     * Ici, nous renvoyons la formation liée (sous forme d'une liste contenant un ID) s'il existe.
     */
    public List<Long> getStudentFormations(Long studentNumber) {
        Optional<Student> opt = studentRepository.findById(studentNumber);
        if (opt.isPresent() && opt.get().getAcademicYear() != null) {
            return List.of(opt.get().getAcademicYear().getId());
        }
        return List.of();
    }

    /**
     * Demande l'inscription d'un étudiant à une formation.
     * Ici, on met à jour l'association academicYear dans l'étudiant.
     */
    public boolean addStudentToFormation(Long studentNumber, Long formationId) {
        Optional<Student> optStudent = studentRepository.findById(studentNumber);
        Optional<AcademicYear> optFormation = academicYearRepository.findById(formationId);
        if (optStudent.isPresent() && optFormation.isPresent()) {
            Student student = optStudent.get();
            student.setAcademicYear(optFormation.get());
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    /**
     * Supprime un étudiant d'une formation.
     * Ici, on supprime l'association en mettant academicYear à null.
     */
    public boolean removeStudentFromFormation(Long studentNumber, Long formationId) {
        Optional<Student> optStudent = studentRepository.findById(studentNumber);
        if (optStudent.isPresent()) {
            Student student = optStudent.get();
            if (student.getAcademicYear() != null && student.getAcademicYear().getId().equals(formationId)) {
                student.setAcademicYear(null);
                studentRepository.save(student);
                return true;
            }
        }
        return false;
    }

    // Stub methods pour les autres inscriptions (groupes, UEs)

    public List<Long> getStudentGroups(Long studentNumber) {
        return List.of(); // Stub
    }

    public List<Long> getStudentModules(Long studentNumber) {
        return List.of(); // Stub
    }

    public boolean addStudentToGroup(Long studentNumber, Long groupId) {
        return true; // Stub
    }

    public boolean removeStudentFromGroup(Long studentNumber, Long groupId) {
        return true; // Stub
    }

    public boolean validateStudentGroup(Long studentNumber, Long groupId, boolean accept) {
        return true; // Stub
    }

    public boolean validateStudentFormation(Long studentNumber, Long formationId, boolean accept) {
        return true; // Stub
    }

    public boolean addStudentToUE(Long studentNumber, Long ueId) {
        return true; // Stub
    }

    public boolean removeStudentFromUE(Long studentNumber, Long ueId) {
        return true; // Stub
    }

    public boolean validateStudentUE(Long studentNumber, Long ueId, boolean accept) {
        return true; // Stub
    }
}
