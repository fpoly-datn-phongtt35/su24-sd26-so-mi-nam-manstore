package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GioHang")
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idTaiKhoan", nullable = false, referencedColumnName = "id")
    private TaiKhoan idTaiKhoan;

    @OneToMany(mappedBy = "idGioHang", fetch = FetchType.LAZY)
    private List<GioHangChiTiet> gioHangChiTiets;

}