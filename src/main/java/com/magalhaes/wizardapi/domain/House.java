package com.magalhaes.wizardapi.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"apiId"})})
public class House {
    @Id
    @GeneratedValue
    private long id;
    private String apiId;
    private String name;

    public House() {
    }

    @Builder
    public House(String apiId, String name) {
        this.apiId = apiId;
        this.name = name;
    }
}
