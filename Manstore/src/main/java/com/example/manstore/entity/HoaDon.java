package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HoaDon")
public class HoaDon implements Serializable {
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
    private LocalDateTime ngayTao;

    @Column(name = "PhiVanChuyen")
    private BigDecimal phiVanChuyen;


    @Column(name = "GhiChu", length = 250)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idThongTinVanChuyen", nullable = false, referencedColumnName = "id")
    private ThongTinVanChuyen idThongTinVanChuyen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPhuongThucThanhToan", nullable = false, referencedColumnName = "id")
    private PhuongThucThanhToan idPhuongThucThanhToan;


    @Column(name = "TongTien", precision = 18)
    private BigDecimal tongTien;

    @Column(name = "TrangThai")
    private Integer trangThai;

//    @OneToMany(mappedBy = "idHoaDon", fetch = FetchType.LAZY)
//    private List<ChiTietHoaDon> chiTietHoaDons;
//
//    @OneToMany(mappedBy = "idHoaDon", fetch = FetchType.LAZY)
//    private List<ThongBao> thongBaos;


}