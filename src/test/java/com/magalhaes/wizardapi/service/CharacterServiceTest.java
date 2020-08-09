package com.magalhaes.wizardapi.service;

import com.magalhaes.wizardapi.CharacterTestHelper;
import com.magalhaes.wizardapi.domain.Character;
import com.magalhaes.wizardapi.domain.House;
import com.magalhaes.wizardapi.repository.CharacterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CharacterServiceTest {

    @Autowired
    private CharacterRepository characterRepository;
    @Mock
    private HouseService houseService;

    private CharacterService characterService;
    private Character characterTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        characterTest = characterRepository.saveAndFlush(CharacterTestHelper.create());
        characterService = new CharacterService(characterRepository, houseService);
        doReturn(House.builder()
                .name(characterTest.getName())
                .apiId(characterTest.getHouse())
                .build())
                .when(houseService).getHouseByApiId(anyString());
    }

    @AfterEach
    void tearDown() {
        characterRepository.deleteAll();
    }

    @Test
    void findById() {
        Character character = characterService.findById(characterTest.getId());
        assertTrue(character != null);
        assertTrue(character.equals(characterTest));
    }

    @Test
    void findByIdShouldThrowException() {
        assertThrows(EntityNotFoundException.class,
                () -> characterService.findById(-1L));
    }

    @Test
    void findByHouse() {
        assertFalse(characterService.find(characterTest.getHouse()).isEmpty());
    }

    @Test
    void update() {
        characterTest.setHouse("new house");
        characterTest.setName("new name");
        characterTest.setPatronus("new patronus");
        characterTest.setRole("new role");
        characterTest.setSchool("new school");

        Character characterUpdated = characterService.save(characterTest);
        assertEquals(characterTest, characterUpdated);
    }

    @Test
    void create() {
        Character newCharacter = characterService.save(CharacterTestHelper.create());
        assertNotNull(newCharacter.getId());
    }

    @Test
    void delete() {
        characterService.delete(characterTest.getId());
        assertTrue(characterRepository.findAll().isEmpty());
    }
}