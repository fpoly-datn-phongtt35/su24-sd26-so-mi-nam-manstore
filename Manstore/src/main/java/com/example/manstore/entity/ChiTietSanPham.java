package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ChiTietSanPham")
public class ChiTietSanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "Soluong")
    private Integer soluong;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSize", nullable = false, referencedColumnName = "id")
    private Size idSize;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPham", nullable = false, referencedColumnName = "id")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idMauSac", nullable = false, referencedColumnName = "id")
    private MauSac idMauSac;

    @Column(name = "TrangThai")
    private Integer trangThai;

//    @OneToMany(mappedBy = "idChiTietSanPham", fetch = FetchType.LAZY)
//    private List<ChiTietHoaDon> chiTietHoaDons;
//
//    @OneToMany(mappedBy = "idSanPhamChiTiet", fetch = FetchType.LAZY)
////    @JsonIgnore
//    @JsonBackReference
//    private List<GioHangChiTiet> gioHangChiTiets;

    @Column(name = "duongDan")
    private String duongDan;

}