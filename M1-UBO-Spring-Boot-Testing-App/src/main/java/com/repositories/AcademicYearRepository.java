package com.repositories;

import com.entities.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Couche d'accès à la base de données pour AcademicYear.
 */
@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    // Les méthodes CRUD de base sont automatiquement fournies.
}
