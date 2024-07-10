package com.example.manstore.service;

import com.example.manstore.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {

    List<SanPham> getAllSanPham();
    Optional<SanPham> getSanPhamById(Integer id);
    Boolean save(SanPham sanPham);
    Boolean update(SanPham sanPham);
    Boolean delete(Integer id);
    Page<SanPham> pageOfSP(Pageable pageable);
    Page<SanPham> SearchSPByName(String keyword, Pageable pageable);
//    Page<SanPham> searchSPByDanhMuc(String keword, Pageable pageable,String danhMuc);
//    Page<SanPham> searchSPByThuongHieu(String keword, Pageable pageable,String thuongHieu);




}
