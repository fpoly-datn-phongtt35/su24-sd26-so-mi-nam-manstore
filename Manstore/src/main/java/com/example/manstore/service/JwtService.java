package com.example.manstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.manstore.entity.KhachHang;
import com.example.manstore.entity.NhanVien;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String secret_key = "123";

    public String generateToken(Object obj, Collection<SimpleGrantedAuthority> authorities) {
        if (obj instanceof NhanVien) {
            NhanVien nv = (NhanVien) obj;
            Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
            return JWT.create().withSubject(nv.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() + 1440 * 60 * 1000))
                    .withClaim("roles",
                            authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                    )
                    .sign(algorithm);
        } else if (obj instanceof KhachHang) {
            KhachHang kh = (KhachHang) obj;
            Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
            return JWT.create().withSubject(kh.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() + 1440 * 60 * 1000))
                    .withClaim("roles",
                            authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                    )
                    .sign(algorithm);
        }
        return null;
    }

    public String generateRefreshToken(Object obj) {
        if (obj instanceof NhanVien) {
            NhanVien nv = (NhanVien) obj;
            Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
            return JWT.create().withSubject(nv.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() + 1440 * 60 * 1000))
                    .sign(algorithm);
        } else if (obj instanceof KhachHang) {
            KhachHang kh = (KhachHang) obj;
            Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
            return JWT.create().withSubject(kh.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() + 1440 * 60 * 1000))
                    .sign(algorithm);
        }
        return null;
    }
}
