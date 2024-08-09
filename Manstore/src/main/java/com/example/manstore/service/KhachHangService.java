package com.example.manstore.service;

import com.example.manstore.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KhachHangService {

    void update(KhachHang khachHang);

    void  save(KhachHang kh);

    List<KhachHang> getALL();

    KhachHang getByID(Integer id);

    Optional<KhachHang> findById(Integer aLong);

    Page<KhachHang> pageOfKhachHang(Pageable pageable);

    Page<KhachHang> seacrch(String keyword,Pageable pageable);

    Page<KhachHang> fillterByGenderNoSearch(Pageable pageable,boolean gender);

    Page<KhachHang> fillterByGenderAndSearch(Pageable pageable,String keyword,boolean gender);

    KhachHang getByPhone(String phone);

}
