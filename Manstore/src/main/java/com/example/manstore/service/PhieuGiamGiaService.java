package com.example.manstore.service;

import com.example.manstore.dto.request.PhieuGiamGiaRequest;
import com.example.manstore.entity.PhieuGiamGia;

import java.util.List;

public interface PhieuGiamGiaService {
    List<PhieuGiamGiaRequest> getAllPhieuGiamGia(Integer page);

    PhieuGiamGiaRequest createPhieuGiamGia(PhieuGiamGia phieuGiamGia);

    PhieuGiamGiaRequest updatePhieuGiamGia(PhieuGiamGia phieuGiamGia, Integer id);

    List<PhieuGiamGiaRequest> getPhieuGiamByLoaiGiamGia(String loaiGiamGia);

    PhieuGiamGiaRequest getById(Integer id);

    List<PhieuGiamGiaRequest> searchByName(String tieuDe);
//    List<PhieuGiamGia> filterByNgay(Date NgayMo, Date ngayDong);
}
