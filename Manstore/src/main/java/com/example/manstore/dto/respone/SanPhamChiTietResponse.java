package com.example.manstore.dto.respone;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietResponse {

    private Integer id;
    private String soluong;
    private String duongDan;
    private Integer trangThai;

    private SanPham1Respone sanPham;

    private SizeResponse size;

    private MauSacResponse mauSac;

}
