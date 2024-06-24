package com.example.manstore.repository;

import com.example.manstore.dto.request.KhachHang_PhieuGiamRequest;
import com.example.manstore.entity.KhahhangPhieugiam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHang_PhieuGiamRepository extends JpaRepository<KhahhangPhieugiam, Integer> {
    @Query("SELECT new KhachHang_PhieuGiamRequest (k.id, k.idKhachHang.ten, k.idPhieuGiamGia.tieuDe, k.idPhieuGiamGia.loaiGiamGia," +
            " k.idPhieuGiamGia.giaTriGiam, k.trangThai, k.soLuong) FROM KhahhangPhieugiam k WHERE k.id = :id")
    public KhachHang_PhieuGiamRequest getKhahhangPhieugiamById(@Param("id") Integer id);
}
