package com.example.manstore.service;
import com.example.manstore.entity.ChiTietHoaDon;
import com.example.manstore.entity.ThongTinVanChuyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface HoaDonChiTietService {
    Page<ChiTietHoaDon> getById(String id, Pageable pageable);

    void save(ChiTietHoaDon dhct);

    List<ChiTietHoaDon> findByIdHD(String id);

    Page<ChiTietHoaDon> getPagination(Pageable pageable, String id);

    ChiTietHoaDon getById2(String id);

    void deleteById(String id);
}
