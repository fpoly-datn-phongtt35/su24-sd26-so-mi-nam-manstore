package com.example.manstore.dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HoaDon_PhieuGiamRequest {

    @Id
    Integer id;

    String maHoaDon;

    String maPhieuGiamGia;

    Boolean loaiGiamGia;

    Integer giaTriGiam;

    Integer trangThai;



}
