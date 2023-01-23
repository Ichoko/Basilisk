package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTokenDTO {


    private String username;
    private String role;
//    dengan token pada prakteknya sudah mengandung user and pw, untuk mempermudah frond end
    private String token;
}
