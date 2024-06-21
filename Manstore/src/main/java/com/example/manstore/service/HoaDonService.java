package com.example.manstore.service;

import com.example.manstore.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    List<HoaDon> getAll();

    Optional<HoaDon> findById(Integer id);

    HoaDon findByHD(String hd);

    void save(HoaDon hd);

    Page<HoaDon> page(Pageable pageable);

    List<HoaDon> getByPromotion(String idPromotion);
}
