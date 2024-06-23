package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class KhachHang {
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

    @Size(max = 50)
    @Column(name = "UserName", length = 50)
    private String userName;

    @Size(max = 100)
    @Column(name = "PassWord", length = 100)
    private String passWord;

    @Size(max = 10)
    @Column(name = "SDT", length = 10)
    private String sdt;

    @Size(max = 50)
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @OneToMany(mappedBy = "idKhachHang")
    private Set<DiaChi> diaChis = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<GioHang> gioHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<KhahhangPhieugiam> khahhangPhieugiam = new LinkedHashSet<>();

}