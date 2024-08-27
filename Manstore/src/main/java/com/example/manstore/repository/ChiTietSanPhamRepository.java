package com.example.manstore.repository;

import com.example.manstore.dto.custom.ChiTietSanPhamDTO;
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

    @Query("SELECT new com.example.manstore.dto.custom.ChiTietSanPhamDTO(s.id, s.ngayTao, s.soluong, s.trangThai, s.duongDan, " +
            "sz.id, sz.ten, sp.id, sp.ten, ms.id, ms.ten) " +
            "FROM ChiTietSanPham s " +
            "JOIN s.idSize sz " +
            "JOIN s.idSanPham sp " +
            "JOIN s.idMauSac ms " +
            "WHERE sp.id = :id AND ms.id = :ms")
    List<ChiTietSanPhamDTO> findListProductByColor(@Param("id") Integer id, @Param("ms") String ms);


    @Query("select s from ChiTietSanPham s where s.idSanPham.id=:id and s.idMauSac.id =:ms and s.idSize.ten =:size ")
    ChiTietSanPham findIdProductByColorAndSize(@Param("id") String id, @Param("ms") String ms, @Param("size") String size);

    @Query("select s from ChiTietSanPham s where (s.idSanPham.ten like %:keyword% or s.idSanPham.ma like %:keyword%) and s.idSanPham.trangThai = 1")
    List<ChiTietSanPham> search(@Param("keyword") String keyword);

    @Query("select spct.duongDan from ChiTietSanPham spct where spct.idSanPham.id = :id")
    List<String> getImgByProductId(@Param("id") String id);

    //lấy đường dẫn theo id và màu sắc
    @Query("select spct.duongDan from ChiTietSanPham spct where spct.idSanPham.id = :id and spct.idMauSac.id = :color")
    List<String> getByIdProductAndColor(@Param("id") String id, @Param("color") String color);

    @Query("select s from ChiTietSanPham s where s.idMauSac.id = ?1")
    Page<ChiTietSanPham> FilterByColor(String color, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idSize.id = ?1")
    Page<ChiTietSanPham> FilterBySize(String size, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idMauSac.id = ?1 and s.idSize.id = ?2 ")
    Page<ChiTietSanPham> FilterByAll(String color, String size, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idSanPham.ten like ?1 and s.idMauSac.id = ?2 ")
    Page<ChiTietSanPham> searchAndFilterByColor(String search, String ms, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idSanPham.ten like ?1 and s.idSize = ?2 ")
    Page<ChiTietSanPham> searchAndFilterBySize(String search, String size, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idSanPham.ten like ?1 and s.idMauSac.id = ?2 and s.idSize = ?3")
    Page<ChiTietSanPham> searchAndFilterAll(String search, String ms, String size, Pageable pageable);

    @Query("select s from ChiTietSanPham s where s.idSanPham.ten like ?1")
    Page<ChiTietSanPham> search(String search, Pageable pageable);


}
