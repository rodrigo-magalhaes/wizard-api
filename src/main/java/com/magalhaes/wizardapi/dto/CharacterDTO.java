package com.magalhaes.wizardapi.dto;

import com.magalhaes.wizardapi.domain.Character;
import lombok.Data;

@Data
public class CharacterDTO {
    private long id;
    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;

    public CharacterDTO() {
    }

    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.role = character.getRole();
        this.school = character.getSchool();
        this.house = character.getHouse();
        this.patronus = character.getPatronus();
    }

    public Character toCharacter() {
        return Character.builder()
                .id(this.getId())
                .name(this.getName())
                .role(this.getRole())
                .school(this.getSchool())
                .house(this.getHouse())
                .patronus(this.getPatronus())
                .build();
    }
}
