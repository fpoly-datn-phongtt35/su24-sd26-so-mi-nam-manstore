package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NhanVien")
public class NhanVien implements UserDetails {
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

    @Size(max = 10)
    @Column(name = "SDT", length = 10)
    private String sdt;

    @Size(max = 300)
    @Column(name = "DiaChi", length = 300)
    private String diaChi;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Size(max = 50)
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPhanQuyen", nullable = false, referencedColumnName = "id")
    private PhanQuyen idPhanQuyen;

    @Size(max = 250)
    @Column(name = "MatKhau", length = 250)
    private String matKhau;

    @Size(max = 250)
    @Column(name = "MaHoaMatKhau", length = 250)
    private String maHoaMatKhau;

    @Column(name = "TrangThai")
    private Integer trangThai;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "idNhanVien" , fetch = FetchType.LAZY)
//    private List<HoaDon> hoaDons;
//
//    @OneToMany(mappedBy = "idNhanVien", fetch = FetchType.LAZY)
//    private List<ThongBao> thongBaos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(idPhanQuyen.getTen()));
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