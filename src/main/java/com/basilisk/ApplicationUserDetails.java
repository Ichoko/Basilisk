package com.basilisk;


import com.basilisk.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//digunakan untuk semacam DTO yg digunkanan untuk login
public class ApplicationUserDetails implements UserDetails {

    private String username;
    private String password;

    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();//roles

    public ApplicationUserDetails(Account account){
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.authorities.add(new SimpleGrantedAuthority(account.getRole()));//convert String jadi SimpleGrantedAuthority
//        getAuthorities adalah tipe data yg spring security gunakan untuk membaca role
//        notes : Kenapa authorities dibuat tipe data colection adalah karena satu waktu user bisa memiliki banyak role
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
