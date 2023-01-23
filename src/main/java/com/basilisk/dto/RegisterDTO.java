package com.basilisk.dto;

import com.basilisk.validator.PasswordComparer;
import com.basilisk.validator.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordComparer(message = "Konfirmasi Password harus Sama")
public class RegisterDTO {
    @NotBlank(message = "username harus diisi")
    @UniqueUsername(message = "Maaf, nama sudah Digunakan")
    @Size(max = 20,message = "user name berupa max 20 karakter")
    private String username ;

    @NotBlank(message = "password harus diisi")
    @Size(max = 10,message = "max 10 karakter")
    private String password;

    @NotBlank(message = "password harus dikonfirmasi")
    @Size(max = 10,message = " max 10 karakter")
    private String confirmPassword;

    @NotBlank(message = "pilih role terlebih dahulu")
    private String role;
}
