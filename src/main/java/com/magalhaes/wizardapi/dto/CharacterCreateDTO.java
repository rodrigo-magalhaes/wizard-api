package com.magalhaes.wizardapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CharacterCreateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String role;
    private String school;
    @NotBlank(message = "House cannot be blank")
    private String house;
    private String patronus;

    public CharacterCreateDTO() {
    }
}
