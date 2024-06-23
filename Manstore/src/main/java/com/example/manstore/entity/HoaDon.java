package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDotGiamGia")
    private DotGiamGia idDotGiamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNhanVien")
    private NhanVien idNhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTaiKhoan")
    private TaiKhoan idTaiKhoan;

    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "VAT", precision = 18)
    private BigDecimal vat;

    @Column(name = "PhuongThucThanhToan")
    private Integer phuongThucThanhToan;

    @Column(name = "TongTien", precision = 18)
    private BigDecimal tongTien;

    @Column(name = "TrangThai")
    private Integer trangThai;

}