package com.example.manstore.repository;

import com.example.manstore.entity.GioHang;
import com.example.manstore.entity.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {
    @Override
    <S extends GioHangChiTiet> S save(S entity);

    @Override
    Optional<GioHangChiTiet> findById(Integer aLong);

    @Override
    boolean existsById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    Page<GioHangChiTiet> findAll(Pageable pageable);

    @Query("select ghct from GioHangChiTiet ghct where ghct.idGioHang.id = :id")
    Page<GioHangChiTiet> getByIdGH(@Param("id") String id, Pageable pageable);

    @Query("select ghct from GioHangChiTiet ghct where ghct.idGioHang.id = :id ORDER BY ghct.ngaySua DESC")
    List<GioHangChiTiet> getByIdGHList(@Param("id") String id);

    @Query("select ghct from GioHangChiTiet ghct where ghct.idGioHang.idKhachHang.id = :id ORDER BY ghct.ngaySua DESC")
    List<GioHangChiTiet> getByIdKH(@Param("id") String id);

    List<GioHangChiTiet> getAllByIdGioHangOrderByNgaySuaDesc(GioHang idGioHang);
}
