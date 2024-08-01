package com.example.manstore.service;

import com.example.manstore.CustomModel.AuthRequest;
import com.example.manstore.CustomModel.AuthResponse;
import com.example.manstore.entity.KhachHang;
import com.example.manstore.entity.NhanVien;
import com.example.manstore.repository.KhachHangRepository;
import com.example.manstore.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        NhanVien nv = nhanVienRepository.getByEmail(authenticationRequest.getUsername()).orElse(null);
        if (nv == null) {
            KhachHang kh = khachHangRepository.getByEmail(authenticationRequest.getUsername()).orElseThrow();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
            var jwtToken = jwtService.generateToken(kh, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(kh);
            return AuthResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(nv.getIdPhanQuyen().getTen()));
        var jwtToken = jwtService.generateToken(nv, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(nv);
        return AuthResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
    }
}