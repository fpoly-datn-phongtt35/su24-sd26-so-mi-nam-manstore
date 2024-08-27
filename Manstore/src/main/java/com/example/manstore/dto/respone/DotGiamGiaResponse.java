package com.example.manstore.dto.respone;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DotGiamGiaResponse {
    private Integer id;
    private String ma;
    private String ten;
    private LocalDate ngayTao;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private Boolean loaiGiamGia;
    private Float giaTriGiam;
    private Double giaTriDonHang;
    private Boolean trangThai;
}
