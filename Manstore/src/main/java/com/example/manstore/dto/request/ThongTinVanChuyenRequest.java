package com.example.manstore.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinVanChuyenRequest {
    private String sdt;
    private String tenNguoiNhan;

    private String tinhThanhpho;
    private String quanHuyen;
    private String xaPhuongThitran;
    private String diaChiCuThe;

}
