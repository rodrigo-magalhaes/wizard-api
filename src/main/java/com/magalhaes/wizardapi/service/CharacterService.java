package com.magalhaes.wizardapi.service;

import com.magalhaes.wizardapi.domain.Character;
import com.magalhaes.wizardapi.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;
    private HouseService houseService;

    public CharacterService(CharacterRepository characterRepository, HouseService houseService) {
        this.characterRepository = characterRepository;
        this.houseService = houseService;
    }

    public Character findById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Character> find(String house) {
        if (house != null) {
            return characterRepository.findAllByHouse(house);
        }
        return characterRepository.findAll();
    }

    public Character save(Character character) {
        validate(character);
        return characterRepository.save(character);
    }

    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    private boolean validate(Character character) {
        if (character.getId() > 0) {
            characterRepository.findById(character.getId()).orElseThrow(EntityNotFoundException::new);
        }
        return Optional.ofNullable(houseService.getHouseByApiId(character.getHouse()))
                .map(newHouse -> houseService.save(newHouse))
                .isPresent();
    }
}
