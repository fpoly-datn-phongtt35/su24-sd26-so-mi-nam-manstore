package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
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
    private Integer gioiTinh;

    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
    private List<DiaChi> diaChis;

    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
    private List<GioHang> gioHangs;

    @OneToMany(mappedBy = "idKhachHang" , fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

}