package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTokenDTO {

    private String username;
    private String password;
    private String subject;
    private String audience;
    private String secretKey;
}
