package com.example.manstore.service;

import com.example.manstore.dto.custom.ChiTietSanPhamDTO;
import com.example.manstore.entity.ChiTietSanPham;
import com.example.manstore.repository.ChiTietSanPhamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietSanPhamService {

    List<ChiTietSanPham> getAllCTSP();

    List<ChiTietSanPham> getAllCTSPById(Integer id);

    Page<ChiTietSanPham> pageOfCTSP(Pageable pageable, String id);

    void save(ChiTietSanPham chiTietSanPham);

    void update(ChiTietSanPham chiTietSanPham);

    ChiTietSanPham getCTSPById(Integer id);

    List<ChiTietSanPham> getListCTSPById(String id);

    Page<ChiTietSanPham> Filter(int page, String color, String size, String id);

    Page<ChiTietSanPham> searchAndFilter(int page,String keyword,String color,String size);


//    List<ChiTietSanPham> findListProductByColor(String id, String ms);

    List<ChiTietSanPhamDTO> findListProductByColor(Integer id, String ms);

    List<String> getImgByProductId( String id);

    List<String> getByIdProductAndColor(String id, String color);

    ChiTietSanPham findIdProductByColorAndSize(String id, String ms, String size);

    List<ChiTietSanPham> search(String keyword);

}
