package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KhachHang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "Ten", length = 100)
    private String ten;

    @Column(name = "SDT")
    private Integer sdt;


    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @OneToMany(mappedBy = "idKhachHang", fetch = FetchType.LAZY)
    private List<DiaChi> diaChis;

    @OneToMany(mappedBy = "idKhachHang", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

    @OneToMany(mappedBy = "idKhachHang", fetch = FetchType.LAZY)
    private List<KhahhangPhieugiam> khahhangPhieugiam;

}