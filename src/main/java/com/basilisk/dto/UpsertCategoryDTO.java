package com.basilisk.dto;

import com.basilisk.validator.UniqueCategoryName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "ini akan menjadi descripsi pada schema")
@UniqueCategoryName(message ="Nama Kategori Sudah Ada!")
public class UpsertCategoryDTO {
    @Schema(description = "ini akan menjadi descripsi pada field")
    private long id;

    @NotBlank(message = "Nama Kategori tidak Boleh kosong!")
    @Size(max = 50, message = "Nama Tidak Boleh Lebih Dari 50 Karakter")
    private String name ;
    @Size(max = 500, message = "Nama Tidak Boleh Lebih Dari 500 Karakter")
    private String description;
}
