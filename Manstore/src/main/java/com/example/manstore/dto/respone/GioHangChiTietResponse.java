package com.example.manstore.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHangChiTietResponse {


    private Integer id;
    private SanPhamChiTietResponse sanPhamChiTiet;

    private GioHangResponse gioHang;

    private String NgaySua;

    private Integer soLuong;


}
