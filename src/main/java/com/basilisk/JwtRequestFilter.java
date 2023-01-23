package com.basilisk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
//        ambil data dari http header dengan key authorization
        String token = null, username = null;

//        jika null akan exception
        if (authorizationHeader!=null){
          token = authorizationHeader.replace("Bearer ","");//buang kata2 bearer dari header
          username = jwtToken.getUsername(token);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(username != null && authentication == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            apakah token valid atau tidak
            Boolean isValid = jwtToken.validateToken(token, userDetails);
            if(isValid){
//                langkah untuk menandakan kalau kita authenticated di dalam spring security, tanpa menggunakan pw
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);//untuk bisa dilanjutkkan ke step selanjutnya
    }
}
