package com.magalhaes.wizardapi.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Character {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;

    public Character() {
    }

    @Builder
    public Character(long id, String name, String role, String school, String house, String patronus) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.school = school;
        this.house = house;
        this.patronus = patronus;
    }
}
