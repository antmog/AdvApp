package com.infosystem.advertisment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
    private String itemName;
    private String description;

    public Item(String name, String description) {
        itemName = name;
        this.description = description;
    }
}
