package com.example.manstore.dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuGiamGiaRequest {

    @Id
    private Integer id;

    private String ma;

    private String tieuDe;

    private LocalDate ngayMo;

    private LocalDate ngayDong;

    private Integer soLuong;

    private Boolean loaiGiamGia;

    private Integer giaTriGiam;

    private String phuongThucThanhToan;

    private String dieuKien;

    private Integer trangThai;

}
