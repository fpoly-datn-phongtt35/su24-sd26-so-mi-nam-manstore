package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DiaChi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    private KhachHang idKhachHang;

    @Column(name = "Ma", length = 50)
    private String ma;


    @Column(name = "Tinh_TP", length = 50)
    private String tinhTp;

    @Column(name = "Quan_Huyen", length = 50)
    private String quanHuyen;

    @Column(name = "Xa_Phuong_ThiTran", length = 50)
    private String xaPhuongThitran;

    @Column(name = "MoTa", length = 500)
    private String moTa;

}