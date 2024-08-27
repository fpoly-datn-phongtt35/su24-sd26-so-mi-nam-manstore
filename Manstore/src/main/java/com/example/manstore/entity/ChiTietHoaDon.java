package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ChiTietHoaDon")
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idChiTietSanPham", nullable = false, referencedColumnName = "id")
    private ChiTietSanPham idChiTietSanPham;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idHoaDon", nullable = false, referencedColumnName = "id")
    private HoaDon idHoaDon;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "DonGia", precision = 18)
    private BigDecimal donGia;

    @Column(name = "GiaThoiDiemMua", precision = 18)
    private BigDecimal giaThoiDiemMua;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "TongTien", precision = 18)
    private BigDecimal tongTien;

}