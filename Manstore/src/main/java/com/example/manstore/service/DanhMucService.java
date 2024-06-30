package com.example.manstore.service;

import com.example.manstore.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DanhMucService {
    List<DanhMuc> getAllDanhMuc();
    DanhMuc getDanhMucById(Integer id);
    DanhMuc save(DanhMuc danhMuc);
    void update(DanhMuc danhMuc);
    Page<DanhMuc> pageOfDM(Pageable pageable);
}
