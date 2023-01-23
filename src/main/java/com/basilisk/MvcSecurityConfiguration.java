package com.basilisk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//adalah setingan dan konfigurasi untuk middleware security untuk chack sebelum request ke action/controller apapun
@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        urutan chainmethod/ rantai method  tidak boleh terbalik logikanya alias harus ada yg diperbolehkan acces dullu
//        http.csrf().disable().authorizeRequests();
        http.authorizeRequests()
                .antMatchers("/resources/**","/account/**").permitAll()//html yg boleh diakses atau diperbolehkan tanpa login
                .antMatchers(
                        "/supplier/**",
                        "/category/upsert-form",
                        "/category/delete",
                        "/category/save"
                        ).hasAuthority("Administrator")
                .antMatchers("/product/**").hasAnyAuthority("Administrator", "Salesman")
                .anyRequest().authenticated()   //request yg harus login
                .and().formLogin().loginPage("/account/loginForm") //rantai selanjutnya akan mendeteksi apakah auth atau belum, maka akan diteruskan ke login page
                .loginProcessingUrl("/authenticating")//pada accnt controller diberikan url nya karena method sudah bisediakan(custom urlnya)
                .and().logout()//untuk mengaktifkan request untuk logout
                .and().exceptionHandling().accessDeniedPage("/account/accessDenied");//setiing acces denied untuk non authorize
        return http.build();
    }
}
