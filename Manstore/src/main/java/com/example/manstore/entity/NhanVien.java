package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class NhanVien {
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

    @OneToMany(mappedBy = "idNhanVien" , fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

}