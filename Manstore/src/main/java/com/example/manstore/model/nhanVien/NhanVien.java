package com.example.manstore.model.nhanVien;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "Ma", length = 50)
    private String ma;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Ten", length = 100)
    private String ten;

    @Column(name = "SDT")
    private Integer sdt;

    @Size(max = 300)
    @Column(name = "DiaChi", length = 300)
    private String diaChi;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Size(max = 50)
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @Column(name = "TrangThai")
    private Integer trangThai;

}