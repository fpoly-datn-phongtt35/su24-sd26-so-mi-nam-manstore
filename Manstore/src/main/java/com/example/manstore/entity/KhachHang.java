package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KhachHang")
public class KhachHang implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "Ma", length = 50)
    private String ma;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Ten", length = 100)
    private String ten;

    @Size(max = 250)
    @Column(name = "MatKhau", length = 250)
    private String matKhau;

    @Size(max = 250)
    @Nationalized
    @Column(name = "MaHoaMatKhau", length = 250)
    private String maHoaMatKhau;

    @Size(max = 10)
    @Column(name = "SDT", length = 10)
    private String sdt;

    @Size(max = 50)
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private boolean gioiTinh;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<DiaChi> diaChis;
//
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<GioHang> gioHangs;
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<HoaDon> hoaDons;
//
//    @OneToMany(mappedBy = "idKhachHang", fetch = FetchType.LAZY)
//    private List<ThongBao> thongBaos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("CUSTOMER"));
    }

    @Override
    public String getPassword() {
        return maHoaMatKhau;
    }

    @Override
    public String getUsername() {
        return email;
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