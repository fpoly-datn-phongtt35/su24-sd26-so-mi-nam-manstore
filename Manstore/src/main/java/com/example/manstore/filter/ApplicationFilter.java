package com.example.manstore.filter;

import com.example.manstore.entity.KhachHang;
import com.example.manstore.entity.NhanVien;
import com.example.manstore.repository.KhachHangRepository;
import com.example.manstore.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@Component
public class ApplicationFilter {

    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<NhanVien> nhanVien = nhanVienRepository.getByEmail(username);
            System.out.println(nhanVien.toString());
            if (nhanVien.isPresent()) {
                return nhanVien.get();
            }

            Optional<KhachHang> khachHang = khachHangRepository.getByEmail(username);
            if (khachHang.isPresent()) {
                return khachHang.get();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
