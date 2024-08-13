package com.example.manstore.service;


import com.example.manstore.entity.GioHang;

public interface GioHangService {

    void save(GioHang gioHang);

    GioHang getById(String id);

    GioHang findByIdKH(Integer id);
}
