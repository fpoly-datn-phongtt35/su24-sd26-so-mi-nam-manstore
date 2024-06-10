package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDotGiamGia", nullable = false, referencedColumnName = "id")
    private DotGiamGia idDotGiamGia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idNhanVien", nullable = false, referencedColumnName = "id")
    private NhanVien idNhanVien;

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

    @OneToMany(mappedBy = "idHoaDon", fetch = FetchType.LAZY)
    private List<ChiTietHoaDon> chiTietHoaDons;

    @OneToMany(mappedBy = "idHoaDon", fetch = FetchType.LAZY)
    private List<HoadonPhieugiam> hoadonPhieugiam;

}