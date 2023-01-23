package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertDeliveryDTO {
    private Long id;

    @NotBlank(message="Company name is required.")
    @Size(max=50, message="Company name can't be no more than 50 characters.")
    private String companyName;
    @NotBlank(message = "Nama Contact Person tidak Boleh kosong!")
    @Size(max=20, message="Phone can't be no more than 20 characters.")
    private String phone;

    @NotNull(message="Cost in rupiah format")
    private Double cost;
}
