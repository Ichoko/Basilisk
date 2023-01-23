package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class errorDTO {
    private String jenisExeption;
    private String messege;
    private LocalDateTime waktuError;

}
