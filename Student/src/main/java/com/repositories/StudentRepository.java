package com.repositories;

import com.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Student.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Les méthodes CRUD de base sont fournies automatiquement.
}
