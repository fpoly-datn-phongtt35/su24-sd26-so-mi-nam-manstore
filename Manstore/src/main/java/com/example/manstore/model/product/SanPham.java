package com.example.manstore.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SanPham")
public class SanPham {
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

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "Gia", precision = 18)
    private BigDecimal gia;

    @Column(name = "GiaSale", precision = 18)
    private BigDecimal giaSale;

    @Size(max = 500)
    @Nationalized
    @Column(name = "MoTa", length = 500)
    private String moTa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDanhMuc", nullable = false)
    private DanhMuc idDanhMuc;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idHinhAnh", nullable = false)
    private HinhAnh idHinhAnh;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idThuongHieu", nullable = false)
    private ThuongHieu idThuongHieu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCoAo", nullable = false)
    private CoAo idCoAo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDuoiAo", nullable = false)
    private DuoiAo idDuoiAo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKieuDang", nullable = false)
    private KieuDang idKieuDang;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idChatLieu", nullable = false)
    private ChatLieu idChatLieu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}