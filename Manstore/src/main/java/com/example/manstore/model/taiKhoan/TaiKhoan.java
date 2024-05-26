package com.example.manstore.model.taiKhoan;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "TenTaiKhoan", length = 20)
    private String tenTaiKhoan;

    @Size(max = 20)
    @Column(name = "MatKhau", length = 20)
    private String matKhau;

    @Size(max = 100)
    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "PhanQuyen")
    private Integer phanQuyen;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

}