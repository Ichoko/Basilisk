package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesmanGridDTO {
    private String employeeNumber;
    private String fullName;
    private String level;
    private String superiorFullName;

}
