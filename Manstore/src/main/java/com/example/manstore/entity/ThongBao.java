package com.example.manstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idNhanVien", nullable = false, referencedColumnName = "id")
    private NhanVien idNhanVien;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idHoaDon", nullable = false, referencedColumnName = "id")
    private HoaDon idHoaDon;

    @Column(name = "TrangThaiDonHang")
    private Integer trangThaiDonHang;

    @Nationalized
    @Lob
    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "NgayGui")
    private LocalDateTime ngayGui;

}