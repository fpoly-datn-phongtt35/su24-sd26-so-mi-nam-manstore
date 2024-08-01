package com.example.manstore.repository;

import com.example.manstore.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query("SELECT hd FROM HoaDon hd WHERE hd.trangThai > 0")
    Page<HoaDon> findAllBut0(Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ma = :hd")
    Optional<HoaDon> findByHD(@Param("hd") String hd);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.idDotGiamGia.id = :idPromotion")
    List<HoaDon> findByPromotion(@Param("idPromotion") String idPromotion);

}
