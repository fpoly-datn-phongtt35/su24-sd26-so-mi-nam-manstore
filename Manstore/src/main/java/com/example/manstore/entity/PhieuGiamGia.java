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
@Table(name = "PhieuGiamGia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "TieuDe", length = 100)
    private String tieuDe;

    @Column(name = "NgayMo")
    private LocalDate ngayMo;

    @Column(name = "NgayDong")
    private LocalDate ngayDong;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "LoaiGiamGia")
    private Boolean loaiGiamGia;

    @Column(name = "GiaTriGiam")
    private Integer giaTriGiam;


    @Column(name = "PhuongThucThanhToan", length = 100)
    private String phuongThucThanhToan;


    @Column(name = "DieuKien", length = 300)
    private String dieuKien;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idPhieuGiamGia", fetch = FetchType.LAZY)
    private List<HoadonPhieugiam> hoadonPhieugiam;

    @OneToMany(mappedBy = "idPhieuGiamGia", fetch = FetchType.LAZY)
    private List<KhahhangPhieugiam> khahhangPhieugiam;

}