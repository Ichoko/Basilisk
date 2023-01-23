package com.basilisk.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationDTO {
    private String field;
    private String message;
}
