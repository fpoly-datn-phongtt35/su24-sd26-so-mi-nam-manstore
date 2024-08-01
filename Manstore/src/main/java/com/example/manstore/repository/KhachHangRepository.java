package com.example.manstore.repository;

import com.example.manstore.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    //tìm kh theo email
    @Query("Select kh from KhachHang kh where kh.email = :email")
    Optional<KhachHang> getByEmail(@Param("email") String email);


    //tim kiem kh theo ma, ten, sdt, email
    @Query("Select kh from KhachHang kh WHERE kh.sdt LIKE %:keyword% or kh.ma like  %:keyword% or kh.ten like  %:keyword% " +
            "or kh.email LIKE %:keyword% ")
    Page<KhachHang> searchKhachHang(@Param("keyword") String keyword, Pageable pageable);

    //lọc kh theo giới tính
    @Query("SELECT kh FROM KhachHang kh WHERE kh.gioiTinh = :gender")
    Page<KhachHang> filterByGenderNoSearch(Pageable pageable, @Param("gender") boolean gender);


    //lọc kh theo giới tính và tìm kiếm
    @Query("SELECT kh FROM KhachHang kh WHERE " +
            "(:keyword IS NULL OR kh.sdt LIKE %:keyword% OR kh.ma LIKE %:keyword% OR kh.ten LIKE %:keyword% OR kh.email LIKE %:keyword%) " +
            "AND kh.gioiTinh = :gender")
    Page<KhachHang> filterByGenderAndSearch(Pageable pageable, @Param("keyword") String keyword, @Param("gender") boolean gender);


    //tìm kh theo sdt
    @Query("Select kh from KhachHang kh where kh.sdt = ?1")
    Optional<KhachHang> findByPhone(String phone);

    //tìm kh theo ma
    @Query(value = "select top 1 k.id from KhachHang k order by k.id desc", nativeQuery = true)
    Integer findMaxId();

    @Query("Select kh from KhachHang kh where kh.email = ?1")
    Optional<KhachHang> findByEmail(String email);

}
