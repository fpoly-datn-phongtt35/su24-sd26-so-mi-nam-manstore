package com.example.manstore.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThuongHieuResponse {
    private Integer id;
    private String ma;
    private String ten;
    private String moTa;
}
