package com.example.manstore.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KhachHang_PhieuGiamRequest {

    @Id
    Integer id;

    String tenKhachHang;

    String tenPhieuGiam;

    Boolean loaiGiamGia;

    Integer giaTriGiam;

    Integer trangThai;

    Integer soLuong;

}
