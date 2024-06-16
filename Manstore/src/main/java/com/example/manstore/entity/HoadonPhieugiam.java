package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HoaDon_PhieuGiam")
public class HoadonPhieugiam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPhieuGiamGia", nullable = false, referencedColumnName = "id")
    private PhieuGiamGia idPhieuGiamGia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idHoaDon", nullable = false, referencedColumnName = "id")
    private HoaDon idHoaDon;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idhoadonPhieugiam", fetch = FetchType.LAZY)
    private List<LichSu> lichSus;

}