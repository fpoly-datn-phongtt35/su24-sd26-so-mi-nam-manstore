package com.example.manstore.repository;

import com.example.manstore.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Query("SELECT hd FROM HoaDon hd")
    List<HoaDon> findByStatus();


    @Query("SELECT hd FROM HoaDon hd WHERE hd.trangThai = :status and hd.trangThai > 0")
    Page<HoaDon> filterByStatus(Pageable pageable, @Param("status") int status);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start and hd.ngayTao <= :end and hd.trangThai > 0")
    Page<HoaDon> filterByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start and hd.ngayTao <= :end and hd.trangThai = :status and hd.trangThai > 0")
    Page<HoaDon> filterByDateAndStatus(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end,
                                       @Param("status") int status,
                                       Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start and hd.ngayTao <= :end " +
            "and hd.trangThai = :status and hd.ma LIKE %:keyword% and hd.trangThai > 0")
    Page<HoaDon> searchAndFilterByAll(@Param("status") int status,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ma LIKE %:keyword% and hd.trangThai > 0")
    Page<HoaDon> searchByName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ma LIKE %:keyword%" +
            " and hd.trangThai = :status")
    Page<HoaDon> searchAndFilter(@Param("status") int status, @Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT hd FROM HoaDon hd WHERE hd.ma LIKE %:keyword% and hd.trangThai > 0 and hd.ngayTao >= :start and hd.ngayTao <= :end")
    Page<HoaDon> searchAndFilterByDate(@Param("keyword") String keyword, @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.idKhachHang.id = :idkh AND hd.trangThai=:status")
    Page<HoaDon> findByIdKHAndStatus(Pageable pageable, @Param("idkh") String idkh, @Param("status") String status);


}
