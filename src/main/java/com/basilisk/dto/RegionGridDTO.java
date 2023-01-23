package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionGridDTO {
    private Long id;
    private String city;
    private String remark;

}
