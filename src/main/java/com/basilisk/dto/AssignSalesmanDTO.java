package com.basilisk.dto;
import javax.validation.constraints.NotBlank;

import com.basilisk.validator.UniqueAssignRegion;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@UniqueAssignRegion(message="This salesman already work in this region.")

    public class AssignSalesmanDTO {

    private Long regionId;

    @NotBlank(message="Salesman is required")
    private String salesmanEmployeeNumber;
}
