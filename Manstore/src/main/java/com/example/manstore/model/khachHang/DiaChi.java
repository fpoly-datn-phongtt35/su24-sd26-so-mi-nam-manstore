package com.example.manstore.model.khachHang;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DiaChi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "Ma", length = 50)
    private String ma;

    @Size(max = 500)
    @Nationalized
    @Column(name = "MoTa", length = 500)
    private String moTa;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Tinh", length = 50)
    private String tinh;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Huyen", length = 50)
    private String huyen;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ThanhPho", length = 50)
    private String thanhPho;

}