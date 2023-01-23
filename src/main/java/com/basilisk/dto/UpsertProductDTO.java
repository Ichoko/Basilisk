package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertProductDTO {
    private Long id;

    @NotBlank(message = "nama tidak boleh Kosong")
    @Size(max=200, message = "Tidak Boleh Lebih dari 200 Karakter")
    private String name;

    private Long supplierId;

    @NotNull(message = "Harap Pilih Kategori Product")
    private Long categoryId;

    @Size(max=1000, message ="maksimal deskripsi 1000 karakter" )
    private String description;

    @NotNull(message = "harus Ada Harga")
    @Min(value = 0,message = "harga harus diinputkan")
    @Digits(integer = 12,fraction = 2, message = "desimal dengan 2 Fraction ")
    private Double price;

    @NotNull(message = "harus Ada Stock")
    @Min(value = 0,message = "Minimal 0")
    @Max(value = 999, message = "max karakter 999")
    private Integer stock;

    @NotNull(message = "harus Ada Order")
    @Min(value = 0,message = "Minimal 0")
    @Max(value = 999, message = "max karakter 999")
    private Integer onOrder;

    private Boolean discontinue;
}
