package com.example.manstore.repository;

import com.example.manstore.dto.request.HoaDon_PhieuGiamRequest;
import com.example.manstore.entity.HoadonPhieugiam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDon_PhieuGiamRepository extends JpaRepository<HoadonPhieugiam, Integer> {
    @Query("SELECT new HoaDon_PhieuGiamRequest (h.id, h.idHoaDon.ma, h.idPhieuGiamGia.ma, h.idPhieuGiamGia.loaiGiamGia," +
            " h.idPhieuGiamGia.giaTriGiam, h.trangThai) FROM HoadonPhieugiam h WHERE h.id = :id")
    public HoaDon_PhieuGiamRequest getHdPhieuGiamById(@Param("id") Integer id);
}
