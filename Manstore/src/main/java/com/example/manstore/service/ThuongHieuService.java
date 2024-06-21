package com.example.manstore.service;

import com.example.manstore.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThuongHieuService {
    List<ThuongHieu> getAllThuongHieu();
    ThuongHieu getThuongHieuById(Integer id);
    ThuongHieu save(ThuongHieu thuongHieu);
    ThuongHieu update(Integer id, ThuongHieu thuongHieu);
    void delete(Integer id);

    Page<ThuongHieu> pageOfTH(Pageable pageable);

    Page<ThuongHieu> SearchTH(String keyword, Pageable pageable);
}
