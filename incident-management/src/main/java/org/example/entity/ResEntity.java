package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResEntity {
    private String code;
    private String message;
    public ResEntity(){
    }
}
