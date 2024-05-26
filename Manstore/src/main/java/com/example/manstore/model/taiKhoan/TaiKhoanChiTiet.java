package com.example.manstore.model.taiKhoan;

import com.example.manstore.model.khachHang.KhachHang;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TaiKhoanChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idTaiKhoan", nullable = false)
    private TaiKhoan idTaiKhoan;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false)
    private KhachHang idKhachHang;

    @Column(name = "TrangThai")
    private Integer trangThai;

}