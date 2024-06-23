package com.example.manstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TaiKhoan_PhanQuyen")
public class TaikhoanPhanquyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTaiKhoan")
    private TaiKhoan idTaiKhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPhanQuyen")
    private PhanQuyen idPhanQuyen;

    @Column(name = "TrangThai")
    private Integer trangThai;

}