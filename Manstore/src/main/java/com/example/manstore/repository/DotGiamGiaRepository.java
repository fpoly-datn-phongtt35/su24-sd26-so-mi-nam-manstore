package com.example.manstore.repository;

import com.example.manstore.entity.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DotGiamGiaRepository extends JpaRepository<DotGiamGia,Integer> {
    @Query("select v from DotGiamGia v where v.ngayBatDau >= ?1 and v.ngayKetThuc <= ?2")
    Page<DotGiamGia> findByDate(LocalDate start, LocalDate end, Pageable pageable);

    @Query("select v from DotGiamGia v where v.loaiGiamGia = ?1")
    Page<DotGiamGia> findByPromotionType(boolean type, Pageable pageable);

    @Query("select v from DotGiamGia v where  v.ngayBatDau >= ?1 and v.ngayKetThuc <= ?2 and v.loaiGiamGia = ?3")
    Page<DotGiamGia> findByDateAndPromotionType(LocalDate start, LocalDate end,boolean type, Pageable pageable);

    @Query("select v from DotGiamGia v where v.ngayBatDau >= ?1 and v.ngayKetThuc <= ?2 and v.loaiGiamGia = ?3")
    Page<DotGiamGia> findByAll(LocalDate start, LocalDate end, boolean typePromotion, Pageable pageable);
}
