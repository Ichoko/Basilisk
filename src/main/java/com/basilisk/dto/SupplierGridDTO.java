package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierGridDTO {
    private long id;
    private String companyName;
    private String contactPerson;
    private String jobTitle;
}
