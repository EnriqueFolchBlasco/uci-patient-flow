package com.hospital.uciRegistration.infraestructure.repository.jpa;

import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, String> {
    @Query("SELECT p.id FROM PatientEntity p WHERE p.id = :id")
    Optional<String> findIdById(@Param("id") String id);
}
