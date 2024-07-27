package com.example.manstore.repository;

import com.example.manstore.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {


    @EntityGraph(attributePaths = {"idSanPham", "idMauSac", "idSize"})
    @Query("SELECT ctsp FROM ChiTietSanPham ctsp WHERE ctsp.idSanPham.id = :id")
    Page<ChiTietSanPham> pageOfCTSP(Pageable pageable, @Param("id") String id);

    @Query("select spct from ChiTietSanPham spct where spct.idSanPham.id = :id")
    List<ChiTietSanPham> getListSpctByIdSp(@Param("id") String id);

    @EntityGraph(attributePaths = {"idSanPham", "idMauSac", "idSize"})
    @Query("SELECT spct FROM ChiTietSanPham spct WHERE spct.idSanPham.id = :id")
    Page<ChiTietSanPham> getSpctByIdSp(@Param("id") String id, Pageable pageable);

    @EntityGraph(attributePaths = {"idSanPham", "idMauSac", "idSize"})
    @Query("SELECT s FROM ChiTietSanPham s WHERE s.idMauSac.id = :color AND s.idSanPham.id = :id")
    Page<ChiTietSanPham> FilterByColorAndProduct(@Param("color") String color, @Param("id") String id, Pageable pageable);

    @EntityGraph(attributePaths = {"idSanPham", "idMauSac", "idSize"})
    @Query("SELECT s FROM ChiTietSanPham s WHERE s.idSize.id = :size AND s.idSanPham.id = :id")
    Page<ChiTietSanPham> FilterBySizeAndProduct(@Param("size") Integer size, @Param("id") String id, Pageable pageable);

    @EntityGraph(attributePaths = {"idSanPham", "idMauSac", "idSize"})
    @Query("SELECT s FROM ChiTietSanPham s WHERE s.idMauSac.id = :color AND s.idSize.id = :size AND s.idSanPham.id = :id")
    Page<ChiTietSanPham> FilterByAllAndProduct(@Param("color") String color, @Param("size") Integer size, @Param("id") String id, Pageable pageable);
}
