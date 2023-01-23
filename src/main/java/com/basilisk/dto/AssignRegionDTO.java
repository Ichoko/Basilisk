package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignRegionDTO {
    @Getter @Setter private String salesmanEmployeeNumber;

    @NotNull(message="Region is required")
    @Getter @Setter private Long regionId;
}
