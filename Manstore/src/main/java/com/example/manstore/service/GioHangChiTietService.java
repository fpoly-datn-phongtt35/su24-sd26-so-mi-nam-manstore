package com.example.manstore.service;


import com.example.manstore.dto.respone.GioHangChiTietResponse;
import com.example.manstore.entity.GioHang;
import com.example.manstore.entity.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GioHangChiTietService {

    Page<GioHangChiTiet> pagination(Pageable pageable);

    Page<GioHangChiTiet> getByIdGH(String id,Pageable pageable);

    void save(GioHangChiTiet gioHangChiTiet);

    void delete(String id);

    GioHangChiTiet getById(String id);

    List<GioHangChiTiet> getByIdGHList(String id);

    List<GioHangChiTietResponse> getAllByIdGioHangOrderByNgaySuaDesc(String idGioHang);

    void deleteAll();

}