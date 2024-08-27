package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DiaChi")
public class DiaChi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private KhachHang idKhachHang;

    @Column(name = "Tinh_ThanhPho", length = 50)
    private String tinhTp;

    @Column(name = "Quan_Huyen", length = 50)
    private String quanHuyen;

    @Column(name = "Xa_Phuong_ThiTran", length = 50)
    private String xaPhuongThitran;

    @Column(name = "SDT", length = 10)
    private String sdt;

    @Column(name = "DiaChiCuThe", length = 500)
    private String diaChiCuThe;

    @Column(name = "TenNguoiNhan", length = 250)
    private String tenNguoiNhan;

    @Column(name = "IsDefault")
    private boolean isDefault;

}