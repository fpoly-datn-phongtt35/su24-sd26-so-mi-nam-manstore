package com.example.manstore.service;

import com.example.manstore.dto.request.KhachHang_PhieuGiamRequest;

import java.util.List;


public interface KhachHang_PhieuGiamService {
    List<KhachHang_PhieuGiamRequest> getAll(Integer page);
}
