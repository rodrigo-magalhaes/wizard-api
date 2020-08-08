package com.magalhaes.wizardapi.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(String message) {
        this.message = message;
    }

}
