package com.example.manstore.service;

import com.example.manstore.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {

    List<NhanVien> getAll();

    Optional<NhanVien> findById(Integer aLong);

    void save(NhanVien nv);

    Page<NhanVien> page(Pageable pageable);

    Page<NhanVien> SearchPage(Pageable pageable, String keyword);

    Page<NhanVien> filterByStatusNoSearch(Pageable pageable, int status);

    Page<NhanVien> filterByStatusAndSearch(Pageable pageable, String keyword, int status);
}
