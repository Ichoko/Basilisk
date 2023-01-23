package com.basilisk.service;

import com.basilisk.ApplicationUserDetails;
import com.basilisk.dao.AccountRepository;
import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.RegisterDTO;
import com.basilisk.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // disini dijadikan acak2


    public void registerAccount(RegisterDTO dto){
        String hasilEcript = passwordEncoder.encode(dto.getPassword());
        Account entity = new Account(
                dto.getUsername(),
                hasilEcript,
                dto.getRole()
        );
        accountRepository.save(entity);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account entity = accountRepository.findById(username).get();
        UserDetails userDetailsDTO = new ApplicationUserDetails(entity);
        return userDetailsDTO;
    }

    public Boolean checkExistingUsername(String username){
        Long totalUsername = accountRepository.countUsername(username);
        return (totalUsername > 0)? true:false;
    }

    public String getAccountRole (String username){
        Account entity = accountRepository.findById(username).get();
        return entity.getRole();
    }
    public List<DropDownDTO> getRoleDropdown() {
        return DropDownDTO.getRoleDropdown();
    }
}
