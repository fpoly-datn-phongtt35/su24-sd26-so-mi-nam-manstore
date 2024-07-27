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
public class ChiTietSanPhamValidationRequest {

    private String soluong;
    private String size;
    private String mauSac;
    private Integer trangThai;

    private String duongDan;
    private boolean isValid;

    @Override
    public String toString() {
        return "ChiTietSanPhamValidationRequest{" +
                ", soluong='" + soluong + '\'' +
                ", size='" + size + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", duongDan='" + duongDan + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
