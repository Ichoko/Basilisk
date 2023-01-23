package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@UniqueCustomerName(message = "Nama Customer Company Sudah Ada")
public class UpsertCustomerDTO {
    private long id;

    @NotBlank(message = "Nama company tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String companyName;

    @NotBlank(message = "Nama Contact Person tidak Boleh kosong!")
    @Size(max = 200, message = "Tidak Boleh Lebih Dari 200 Karakter")
    private String contactPerson;

    @Size(max = 500, message = "Tidak Boleh Lebih Dari 500 Karakter")
    private String address;

    @NotBlank(message = "tidak Boleh kosong!")
    @Size(max = 100, message = "Tidak Boleh Lebih Dari 100 Karakter")
    private String city;


    @Size(max = 20, message = "Tidak Boleh Lebih Dari 20 Karakter")
    private String phone;

    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String email;

}
