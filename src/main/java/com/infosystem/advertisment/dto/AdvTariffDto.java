package com.infosystem.advertisment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvTariffDto {

    private String name;
    private String description;

    public AdvTariffDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
