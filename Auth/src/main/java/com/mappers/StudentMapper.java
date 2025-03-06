package com.mappers;

import com.dtos.StudentDTO;
import com.entities.AcademicYear;
import com.entities.Student;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Mapper pour convertir entre Student et StudentDTO.
 */
@Component
public class StudentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Convertit une entité Student en DTO
    public StudentDTO toDTO(Student student) {
        if (student == null) return null;
        StudentDTO dto = new StudentDTO();
        dto.setStudentNumber(student.getStudentNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setMail(student.getMail());
        if (student.getBirthDate() != null) {
            dto.setBirthDate(student.getBirthDate().format(FORMATTER));
        }
        if (student.getAcademicYear() != null) {
            dto.setAcademicYearId(student.getAcademicYear().getId());
        }
        return dto;
    }

    // Convertit un DTO en entité Student
    public Student toEntity(StudentDTO dto) {
        if (dto == null) return null;
        Student student = new Student();
        if (dto.getStudentNumber() != null) {
            student.setStudentNumber(dto.getStudentNumber());
        }
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setMail(dto.getMail());
        if (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) {
            student.setBirthDate(LocalDate.parse(dto.getBirthDate(), FORMATTER));
        }
        if (dto.getAcademicYearId() != null) {
            AcademicYear academicYear = new AcademicYear();
            academicYear.setId(dto.getAcademicYearId());
            student.setAcademicYear(academicYear);
        }
        return student;
    }
}
