package com.example.manstore.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangRequest {
    private Integer id;
    private String ma;
    private String ten;
    private String matKhau;
    private String maHoaMatKhau;
    private String sdt;
    private String email;
    private String ngaySinh;
    private boolean gioiTinh;
    private LocalDate ngayTao;

}
