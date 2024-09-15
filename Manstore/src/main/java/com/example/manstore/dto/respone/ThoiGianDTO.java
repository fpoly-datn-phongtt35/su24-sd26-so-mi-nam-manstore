package com.example.manstore.dto.respone;

import lombok.Data;

import java.sql.Date;

@Data
public class ThoiGianDTO {

    private Date from;
    private Date to;
    private String thang;
    private Integer nam;
    private Integer quy;
}