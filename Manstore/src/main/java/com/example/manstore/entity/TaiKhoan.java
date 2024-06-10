package com.example.manstore.entity;

import jakarta.persistence.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "TenTaiKhoan", length = 20)
    private String tenTaiKhoan;

    @Column(name = "MatKhau", length = 20)
    private String matKhau;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "PhanQuyen")
    private Integer phanQuyen;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @OneToMany(mappedBy = "idTaiKhoan", fetch = FetchType.LAZY)
    private List<GioHang> gioHangs;

    @OneToMany(mappedBy = "idTaiKhoan", fetch = FetchType.LAZY)
    private List<NhanVien> nhanViens;

}