package com.magalhaes.wizardapi;

import com.magalhaes.wizardapi.domain.Character;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CharacterTestHelper {

    public Character create() {
        return Character.builder()
                .name("name")
                .role("role")
                .school("school")
                .house("house")
                .patronus("patronus")
                .build();
    }
}
