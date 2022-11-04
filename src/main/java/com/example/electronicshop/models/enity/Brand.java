package com.example.electronicshop.models.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    private String id;
    @Indexed(unique = true)
    @TextIndexed(weight = 9)
    private String name;

    private String state;

    public Brand(String name, String state) {
        this.name = name;
        this.state = state;
    }
}
