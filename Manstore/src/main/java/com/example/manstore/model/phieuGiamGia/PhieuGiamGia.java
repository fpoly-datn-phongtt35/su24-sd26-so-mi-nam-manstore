package com.example.manstore.model.phieuGiamGia;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class PhieuGiamGia {
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

    @Column(name = "NgayMo")
    private LocalDate ngayMo;

    @Column(name = "NgayDong")
    private LocalDate ngayDong;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Size(max = 100)
    @Column(name = "LoaiGiamGia", length = 100)
    private String loaiGiamGia;

    @Column(name = "TrangThai")
    private Integer trangThai;

}