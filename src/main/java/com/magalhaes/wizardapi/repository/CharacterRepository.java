package com.magalhaes.wizardapi.repository;

import com.magalhaes.wizardapi.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findAllByHouse(String house);
}
