package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "Ten", length = 100)
    private String ten;

    @Column(name = "SDT")
    private Integer sdt;

    @Column(name = "DiaChi", length = 300)
    private String diaChi;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idTaiKhoan", nullable = false, referencedColumnName = "id")
    private TaiKhoan idTaiKhoan;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idNhanVien", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

}