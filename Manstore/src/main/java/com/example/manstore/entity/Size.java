package com.example.manstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Ma", length = 50)
    private String ma;

    @Column(name = "Ten", length = 100)
    private String ten;

    @Column(name = "MoTa", length = 500)
    private String moTa;

    @OneToMany(mappedBy = "idSize", fetch = FetchType.LAZY)
    private List<ChiTietSanPham> chiTietSanPhams;

}