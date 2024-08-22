package com.example.manstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GioHang")
public class GioHang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private KhachHang idKhachHang;

//    @OneToMany(mappedBy = "idGioHang", fetch = FetchType.LAZY)
//    private List<GioHangChiTiet> gioHangChiTiets;

}