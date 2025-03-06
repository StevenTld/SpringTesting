package com.clients;

import com.dtos.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "student-service", url = "${student.service.url}")
public interface StudentClient {

    @PostMapping("/api/students")
    StudentDTO createStudent(@RequestBody StudentDTO studentDTO);

    @GetMapping("/api/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);

    @GetMapping("/api/students")
    java.util.List<StudentDTO> getAllStudents();
}