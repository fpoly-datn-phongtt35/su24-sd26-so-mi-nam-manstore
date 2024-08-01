package com.example.manstore.CustomModel;

import com.example.manstore.entity.GioHangChiTiet;
import lombok.Data;

import java.util.List;

@Data
public class ResponseProduct {

    private List<ResponseMessage> listMessage;

    private List<GioHangChiTiet> listCart;
}
