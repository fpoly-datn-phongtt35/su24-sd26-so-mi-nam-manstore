package com.example.manstore.model.gioHang;

import com.example.manstore.model.product.ChiTietSanPham;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 18)
    private BigDecimal donGia;

    @Column(name = "TongTien", precision = 18)
    private BigDecimal tongTien;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPhamChiTiet", nullable = false)
    private ChiTietSanPham idSanPhamChiTiet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGioHang", nullable = false)
    private GioHang idGioHang;

}