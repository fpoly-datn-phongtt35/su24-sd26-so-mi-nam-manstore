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
    private Integer danhMuc;
    private String tenDanhMuc;
    private String duongDan;
    private Integer thuongHieu;
    private String tenThuongHieu;
    private Integer duoiAo;
    private String tenDuoiAo;
    private Integer kieuDang;
    private String tenKieuDang;
    private Integer chatLieu;
    private String tenChatLieu;
    private Integer trangThai;

    public SanPhanResponse(Integer id, String ma, String ten, Integer soLuong, LocalDate ngayTao, BigDecimal gia, BigDecimal giaSale, Integer danhMuc, String duongDan, Integer thuongHieu, Integer duoiAo, Integer kieuDang, Integer chatLieu, Integer trangThai) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.gia = gia;
        this.danhMuc = danhMuc;
        this.duongDan = duongDan;
        this.thuongHieu = thuongHieu;
        this.duoiAo = duoiAo;
        this.kieuDang = kieuDang;
        this.chatLieu = chatLieu;
        this.trangThai = trangThai;
    }






}
