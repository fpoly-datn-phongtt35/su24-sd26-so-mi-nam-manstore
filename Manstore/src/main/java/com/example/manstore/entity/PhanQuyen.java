package com.example.manstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhanQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "Ten", length = 100)
    private String ten;


    @Column(name = "MoTa", length = 200)
    private String moTa;

    @OneToMany(mappedBy = "idPhanQuyen")
    private Set<TaikhoanPhanquyen> taikhoanPhanquyens = new LinkedHashSet<>();

}