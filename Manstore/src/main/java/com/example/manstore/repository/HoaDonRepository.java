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

    @Query("SELECT dh FROM HoaDon dh WHERE dh.trangThai = :status and dh.trangThai > 0")
    Page<HoaDon> filterByStatus(@Param("status") int status, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.ngayTao >= :start and dh.ngayTao <= :end and dh.trangThai > 0")
    Page<HoaDon> filterByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.ngayTao >= :start and dh.ngayTao <= :end and dh.trangThai = :status and dh.trangThai > 0")
    Page<HoaDon> filterByDateAndStatus(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                        @Param("status") int status, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.ngayTao >= :start and dh.ngayTao <= :end and dh.trangThai = :status and dh.ma LIKE %:keyword% and dh.trangThai > 0")
    Page<HoaDon> searchAndFilterByAll(@Param("status") int status, @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end, @Param("keyword") String keyword,
                                       Pageable pageable);
    @Query("SELECT dh FROM HoaDon dh WHERE dh.ma LIKE %:keyword% and dh.trangThai > 0")
    Page<HoaDon> searchByName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.ma LIKE %:keyword%" +
            " and dh.trangThai = :status")
    Page<HoaDon> searchAndFilter(@Param("status") int status, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.ma LIKE %:keyword% and dh.trangThai > 0 and dh.ngayTao >= :start and dh.ngayTao <= :end")
    Page<HoaDon> searchAndFilterByDate(@Param("keyword") String keyword, @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT dh FROM HoaDon dh WHERE dh.idKhachHang.id = :idkh AND dh.trangThai=:status")
    Page<HoaDon> findByIdKHAndStatus(Pageable pageable, @Param("idkh") String idkh, @Param("status") String status);

    @Query(value = "select sum(i.TongTien) from HoaDon i " +
            "where Month(i.NgayTao) = ?1 and Year(i.NgayTao) = ?2 and i.TrangThai = 4", nativeQuery = true)
    Double calDt(int i, Integer year);

    @Query(value = "select sum(i.TongTien) from HoaDon i where i.TrangThai = 4", nativeQuery = true)
    Double tongDoanhThu();

    @Query(value = "select sum(i.TongTien) from HoaDon i where i.NgayTao >= ?1 and i.NgayTao <= ?2 and i.TrangThai = 4", nativeQuery = true)
    Double tongDoanhThu(LocalDateTime start,LocalDateTime end);
}
