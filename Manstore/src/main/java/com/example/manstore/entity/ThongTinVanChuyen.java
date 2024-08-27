package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class ThongTinVanChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "SDT", length = 50)
    private String sdt;

    @Size(max = 50)
    @Nationalized
    @Column(name = "TenNguoiNhan", length = 50)
    private String tenNguoiNhan;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Tinh_ThanhPho", length = 100)
    private String tinhThanhpho;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Quan_Huyen", length = 100)
    private String quanHuyen;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Xa_Phuong_ThiTran", length = 100)
    private String xaPhuongThitran;

    @Size(max = 200)
    @Nationalized
    @Column(name = "DiaChiCuThe", length = 200)
    private String diaChiCuThe;
//
//    @OneToMany(mappedBy = "idThongTinVanChuyen", fetch = FetchType.LAZY)
//    private List<HoaDon> hoaDons;

}