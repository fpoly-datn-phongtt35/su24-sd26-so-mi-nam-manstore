package com.example.manstore.dto.respone;


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
public class SanPhanResponse {

    private Integer id;
    private String ma;
    private String ten;
    private Integer soLuong;
    private LocalDate ngayTao;
    private BigDecimal gia;
    private BigDecimal giaSale;
    private String danhMuc;
    private String duongDan;
    private String thuongHieu;
    private String coAo;
    private String duoiAo;
    private String kieuDang;
    private String chatLieu;
    private Integer trangThai;

    public SanPhanResponse(Integer id, String ma, String ten, int soLuong, LocalDate ngayTao, BigDecimal gia, BigDecimal giaSale, String danhMuc,String duongDan, String thuongHieu, String coAo, String duoiAo, String kieuDang, String chatLieu, Integer trangThai) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.gia = gia;
        this.giaSale = giaSale;
        this.danhMuc = danhMuc;
        this.duongDan = duongDan;
        this.thuongHieu = thuongHieu;
        this.coAo = coAo;
        this.duoiAo = duoiAo;
        this.kieuDang = kieuDang;
        this.chatLieu = chatLieu;
        this.trangThai = trangThai;
    }




}
