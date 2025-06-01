package com.example.UserDept.services;


import com.example.UserDept.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return empRepository.findByEmail(email);
    }
}
