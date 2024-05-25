package com.example.manstore.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 50)
    @Column(name = "Ma", length = 50)
    private String ma;

    @jakarta.validation.constraints.Size(max = 100)
    @Nationalized
    @Column(name = "Ten", length = 100)
    private String ten;

    @jakarta.validation.constraints.Size(max = 500)
    @Nationalized
    @Column(name = "MoTa", length = 500)
    private String moTa;

}