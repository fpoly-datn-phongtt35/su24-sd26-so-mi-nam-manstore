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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DotGiamGia")
public class DotGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Ma", length = 50)
    private String ma;


    @Column(name = "Ten", length = 100)
    private String ten;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "LoaiGiamGia")
    private Boolean loaiGiamGia;

    @Column(name = "GiaTriGiam")
    private Float giaTriGiam;

    @Column(name = "GiaTriDonHang")
    private Double giaTriDonHang;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @OneToMany(mappedBy = "idDotGiamGia", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

}