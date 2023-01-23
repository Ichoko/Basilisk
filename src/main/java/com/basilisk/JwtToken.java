package com.basilisk;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//se-level service
@Service
public class JwtToken {

    //Ini merupakan fungsi password dari aplikasi 3rd untuk akses API
    private final String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";

    //Ini merupakan fungsi username dari aplikasi 3rd untuk akses API
    private final String AUDIENCE = "BasiliskWebUI";

    //Mendapatkan isi payload(claim) dari token yg terbagi menjadi 3.
    private Claims getClaims(String token){
//        konvert secret key jadi JWTParser dan
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
//        mem-verified String token, dan ekstrak signature dan calims
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);
//        Ambil claims/payloadnya saja
        Claims claims =signatureAndClaims.getBody();
        return claims;
    }

    public String getUsername(String token){
        try {
            Claims claims = getClaims(token);
            return claims.get("username", String.class);
        }catch ( Exception exception){
            return null;
        }
    }
//  Menvalidasi apakah token ini benar atau tidak
    public Boolean validateToken (String token, UserDetails userDetails){
        Claims claims = getClaims(token);
//        mendapatkan username
        String username = getUsername(token);
//        dapatkan audience
        String audience = claims.getAudience();
//        jika username dan audience match, return true
        return (username.equals(userDetails.getUsername())) && audience.equals(AUDIENCE);
    }

//    Method yg digunakan untuk membuat token yg diterima oleh request 3rd party appication and user
    public String generateToken(String username, String secretKey, String role, String audience, String subject){
        JwtBuilder builder = Jwts.builder();
        builder = builder.setSubject(subject)
                .claim("username", username)
                .claim("role",role)
                .setIssuer("http://localhost:8070")
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256,secretKey);
        return builder.compact();
    }
}
