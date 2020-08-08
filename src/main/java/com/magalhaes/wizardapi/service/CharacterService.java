package com.magalhaes.wizardapi.service;

import com.magalhaes.wizardapi.domain.Character;
import com.magalhaes.wizardapi.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Character findById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Character> findByHouse(String house) {
        return characterRepository.findAllByHouse(house);
    }

    public Character update(Character character) {
        return characterRepository.save(character);
    }

    public Character create(Character character) {
        return characterRepository.save(character);
    }

    public void delete(Long id) {
        characterRepository.deleteById(id);
    }
}
