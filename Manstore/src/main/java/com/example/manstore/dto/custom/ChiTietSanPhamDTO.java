package com.example.manstore.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamDTO {
    private Integer id;
    private LocalDate ngayTao;
    private Integer soluong;
    private Integer trangThai;
    private String duongDan;
    private Integer sizeId;
    private String sizeTen;
    private Integer sanPhamId;
    private String sanPhamTen;
    private Integer mauSacId;
    private String mauSacTen;
}
