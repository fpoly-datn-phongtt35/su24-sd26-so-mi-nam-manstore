package com.example.manstore.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamRequest {
    private String id;
    private String ten;
    private Integer soLuong;
    private LocalDate ngayTao;
    private BigDecimal gia;
    private BigDecimal giaSale;
    private String moTa;
    private Integer danhMuc;
    private Integer thuongHieu;
    private Integer coAo;
    private Integer duoiAo;
    private Integer kieuDang;
    private Integer chatLieu;
    private String duongDan;
    private Integer trangThai;

    @Override
    public String toString() {
        return "SanPhamRequest{" +
                "id='" + id + '\'' +
                ", ten='" + ten + '\'' +
                ", soLuong=" + soLuong +
                ", ngayTao=" + ngayTao +
                ", gia=" + gia +
                ", giaSale=" + giaSale +
                ", moTa='" + moTa + '\'' +
                ", danhMuc=" + danhMuc +
                ", duongDan=" + duongDan +
                ", thuongHieu=" + thuongHieu +
                ", coAo=" + coAo +
                ", duoiAo=" + duoiAo +
                ", kieuDang=" + kieuDang +
                ", chatLieu=" + chatLieu +
                ", trangThai=" + trangThai +
                '}';
    }
}
