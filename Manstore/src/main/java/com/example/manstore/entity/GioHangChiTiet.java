package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GioHangChiTiet")
public class GioHangChiTiet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "NgaySua")
    private LocalDate ngaySua;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPhamChiTiet", nullable = false, referencedColumnName = "id")
    private ChiTietSanPham idSanPhamChiTiet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGioHang", nullable = false, referencedColumnName = "id")
    private GioHang idGioHang;

}