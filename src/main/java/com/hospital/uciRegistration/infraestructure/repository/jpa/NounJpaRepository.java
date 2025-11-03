package com.hospital.uciRegistration.infraestructure.repository.jpa;

import com.hospital.uciRegistration.infraestructure.entity.NounEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NounJpaRepository extends JpaRepository<NounEntity, Long> {

    @Query(value = "SELECT * FROM nouns ORDER BY RAND() LIMIT 1", nativeQuery = true)
    NounEntity findRandom();

}
