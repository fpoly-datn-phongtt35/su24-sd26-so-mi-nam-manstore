package com.example.manstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamRequest {
    private String soluong;
    private String size;
    private String mauSac;

    private String duongDan;
    private Integer trangThai;

    @Override
    public String toString() {
        return "ChiTietSanPhamRequest{" +
                ", soluong='" + soluong + '\'' +
                ", size='" + size + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", duongDan='" + duongDan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }

}
