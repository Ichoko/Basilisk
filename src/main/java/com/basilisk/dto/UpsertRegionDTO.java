package com.basilisk.dto;

import com.basilisk.validator.UniqueCategoryName;
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
//@UniqueCategoryName(message ="Nama Kategori Sudah Ada!")
public class UpsertRegionDTO {
    private long id;

    @NotBlank(message = "Nama Kategori tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String city ;
    @Size(max = 500, message = "Tidak Boleh Lebih Dari 500 Karakter")
    private String remark;
}
