package com.basilisk.rest;

import com.basilisk.JwtToken;
import com.basilisk.dto.RequestTokenDTO;
import com.basilisk.dto.ResponseTokenDTO;
import com.basilisk.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/authenticate")
public class AccountRestController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtToken jwtToken;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody RequestTokenDTO dto){
        try {

//        tokenisasi gaya Spring security(token pada cookies)
            var token = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
//        menggunakan spring security untuk check apakah username dan pw valid
            authenticationManager.authenticate(token);


        }catch (Exception exception){
            return ResponseEntity.status(403).body("authentication gagal, username & pw not found");
        }

//            dapatkan role hanya untuk extra informasi di payload
        String role = accountService.getAccountRole(dto.getUsername());
//            Dapatkan jwt token dari method generate di class jwt token
        String jwt = jwtToken.generateToken(dto.getUsername(),
                dto.getSecretKey(),
                role,
                dto.getAudience(),
                dto.getSubject());
//            DTO yg diberikan ke reques dalam http respon bodinya
        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(
                dto.getUsername(),role,jwt
        );
        return ResponseEntity.status(200).body(responseTokenDTO);
    }
}
