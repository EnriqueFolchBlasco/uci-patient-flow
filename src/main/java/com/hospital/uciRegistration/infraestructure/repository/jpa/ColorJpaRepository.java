package com.hospital.uciRegistration.infraestructure.repository.jpa;

import com.hospital.uciRegistration.infraestructure.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {

    @Query(value = "SELECT * FROM colors ORDER BY RAND() LIMIT 1", nativeQuery = true)
    ColorEntity findRandom();

}
