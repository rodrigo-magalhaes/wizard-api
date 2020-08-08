package com.magalhaes.wizardapi.repository;

import com.magalhaes.wizardapi.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {

    House findByApiId(String house);
}
