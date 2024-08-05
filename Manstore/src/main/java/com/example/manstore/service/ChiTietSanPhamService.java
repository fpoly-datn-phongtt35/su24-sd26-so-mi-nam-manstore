package com.example.manstore.service;

import com.example.manstore.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChiTietSanPhamService {

    List<ChiTietSanPham> getAllCTSP();

    List<ChiTietSanPham> getAllCTSPById(Integer id);

    Page<ChiTietSanPham> pageOfCTSP(Pageable pageable, String id);

    void save(ChiTietSanPham chiTietSanPham);

    void update(ChiTietSanPham chiTietSanPham);

    ChiTietSanPham getCTSPById(Integer id);

    List<ChiTietSanPham> findListProductByColor(String id,String ms);

    List<ChiTietSanPham> getListCTSPById(String id);

    Page<ChiTietSanPham> Filter(int page, String color, String size, String id);

    ChiTietSanPham findIdProductByColorAndSize(String id,String ms,String size);

    List<ChiTietSanPham> search(String keyword);




}
