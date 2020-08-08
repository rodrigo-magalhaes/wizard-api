package com.magalhaes.wizardapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalhaes.wizardapi.CharacterTestHelper;
import com.magalhaes.wizardapi.domain.Character;
import com.magalhaes.wizardapi.domain.House;
import com.magalhaes.wizardapi.dto.CharacterDTO;
import com.magalhaes.wizardapi.exception.HouseException;
import com.magalhaes.wizardapi.repository.CharacterRepository;
import com.magalhaes.wizardapi.service.CharacterService;
import com.magalhaes.wizardapi.service.HouseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CharacterControllerTest {

    @Autowired
    private CharacterRepository characterRepository;
    @Mock
    private HouseService houseService;

    private final String URL_API = "/api/characters/";
    private MockMvc mockMvc;
    private Character characterTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        characterTest = characterRepository.saveAndFlush(CharacterTestHelper.create());
        CharacterService characterService = new CharacterService(characterRepository, houseService);
        mockMvc = MockMvcBuilders.standaloneSetup(new CharacterController(characterService)).build();
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
    void getCharacter() throws Exception {
        mockMvc.perform(get(URL_API + "{id}", characterTest.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(characterTest.getId()))
                .andExpect(jsonPath("$.name").value(characterTest.getName()))
                .andExpect(jsonPath("$.role").value(characterTest.getRole()))
                .andExpect(jsonPath("$.school").value(characterTest.getSchool()))
                .andExpect(jsonPath("$.house").value(characterTest.getHouse()))
                .andExpect(jsonPath("$.patronus").value(characterTest.getPatronus()));
    }

    @Test
    void getCharacterWithFilter() throws Exception {
        mockMvc.perform(get(URL_API + "?house={house}", characterTest.getHouse()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(characterTest.getId()))
                .andExpect(jsonPath("$.[0].name").value(characterTest.getName()))
                .andExpect(jsonPath("$.[0].role").value(characterTest.getRole()))
                .andExpect(jsonPath("$.[0].school").value(characterTest.getSchool()))
                .andExpect(jsonPath("$.[0].house").value(characterTest.getHouse()))
                .andExpect(jsonPath("$.[0].patronus").value(characterTest.getPatronus()));
    }

    @Test
    void getCharacterShouldReturnErrorMessage() throws Exception {
        doThrow(new HouseException(characterTest.getHouse())).when(houseService).getHouseByApiId(anyString());
        CharacterDTO dto = new CharacterDTO(characterTest);
        Exception exception = assertThrows(Exception.class,
                () -> mockMvc.perform(put(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(dto))));
        assertTrue(exception.getMessage().contains(characterTest.getHouse()));
    }

    @Test
    void updateCharacter() throws Exception {
        characterTest.setHouse("new house");
        characterTest.setName("new name");
        characterTest.setPatronus("new patronus");
        characterTest.setRole("new role");
        characterTest.setSchool("new school");
        CharacterDTO dto = new CharacterDTO(characterTest);
        mockMvc.perform(put(URL_API).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(characterTest.getId()))
                .andExpect(jsonPath("$.name").value(characterTest.getName()))
                .andExpect(jsonPath("$.role").value(characterTest.getRole()))
                .andExpect(jsonPath("$.school").value(characterTest.getSchool()))
                .andExpect(jsonPath("$.house").value(characterTest.getHouse()))
                .andExpect(jsonPath("$.patronus").value(characterTest.getPatronus()));
    }

    @Test
    void createCharacter() throws Exception {
        CharacterDTO dto = new CharacterDTO(CharacterTestHelper.create());
        mockMvc.perform(post(URL_API).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(characterTest.getName()))
                .andExpect(jsonPath("$.role").value(characterTest.getRole()))
                .andExpect(jsonPath("$.school").value(characterTest.getSchool()))
                .andExpect(jsonPath("$.house").value(characterTest.getHouse()))
                .andExpect(jsonPath("$.patronus").value(characterTest.getPatronus()));
    }

    @Test
    void deleteCharacter() throws Exception {
        mockMvc.perform(delete(URL_API + "{id}", characterTest.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertTrue(characterRepository.findAll().isEmpty());
    }
}