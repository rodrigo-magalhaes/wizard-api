package com.magalhaes.wizardapi.controller;

import com.magalhaes.wizardapi.dto.CharacterDTO;
import com.magalhaes.wizardapi.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO getCharacter(@PathVariable("id") Long id) {
        return new CharacterDTO(characterService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CharacterDTO> getCharacterWithFilter(String house) {
        return characterService.findByHouse(house).stream()
                .map(CharacterDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacter(@RequestBody CharacterDTO dto) {
        return new CharacterDTO(characterService.save(dto.toCharacter()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody CharacterDTO dto) {
        return new CharacterDTO(characterService.save(dto.toCharacter()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable("id") Long id) {
        characterService.delete(id);
    }
}
