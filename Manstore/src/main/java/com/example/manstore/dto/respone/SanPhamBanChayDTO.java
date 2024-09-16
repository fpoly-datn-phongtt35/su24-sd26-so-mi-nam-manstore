package com.example.manstore.dto.respone;

import com.example.manstore.entity.DanhMuc;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SanPhamBanChayDTO {

    private Integer id;

    private String ma;

    private String ten;

    private BigDecimal gia;

    private DanhMuc idDanhMuc;

    private Integer soLuong;
}