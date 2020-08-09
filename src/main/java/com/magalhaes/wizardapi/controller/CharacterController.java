package com.magalhaes.wizardapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalhaes.wizardapi.domain.Character;
import com.magalhaes.wizardapi.dto.CharacterCreateDTO;
import com.magalhaes.wizardapi.dto.CharacterDTO;
import com.magalhaes.wizardapi.dto.CharacterUpdateDTO;
import com.magalhaes.wizardapi.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private CharacterService characterService;
    private ObjectMapper mapper;

    public CharacterController(CharacterService characterService, ObjectMapper mapper) {
        this.characterService = characterService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO getCharacter(@PathVariable("id") Long id) {
        return mapper.convertValue(characterService.findById(id), CharacterDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CharacterDTO> getCharacters(String house) {
        return characterService.find(house).stream()
                .map(character -> mapper.convertValue(character, CharacterDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacter(@RequestBody CharacterUpdateDTO dto) {
        Character character = mapper.convertValue(dto, Character.class);
        return mapper.convertValue(characterService.save(character), CharacterDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody CharacterCreateDTO dto) {
        Character character = mapper.convertValue(dto, Character.class);
        return mapper.convertValue(characterService.save(character), CharacterDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable("id") Long id) {
        characterService.delete(id);
    }
}
