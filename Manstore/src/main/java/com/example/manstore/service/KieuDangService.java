package com.example.manstore.service;

import com.example.manstore.entity.KieuDang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KieuDangService {
    List<KieuDang> getAllKieuDang();

    KieuDang getKieuDangById(Integer id);

    KieuDang save(KieuDang kieuDang);

    void update(KieuDang kieuDang);

    Page<KieuDang> pageOfKieuDang(Pageable pageable);
}
