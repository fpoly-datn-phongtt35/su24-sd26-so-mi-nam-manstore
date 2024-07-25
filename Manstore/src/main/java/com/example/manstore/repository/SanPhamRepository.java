package com.example.manstore.repository;

import com.example.manstore.dto.respone.SanPhanResponse;
import com.example.manstore.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale, sp.idDanhMuc.ten,sp.DuongDan, sp.idThuongHieu.ten, sp.idCoAo.ten, sp.idDuoiAo.ten, sp.idKieuDang.ten, sp.idChatLieu.ten, sp.trangThai) FROM SanPham sp")
    public Page<SanPhanResponse> findAllSP(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp WHERE LOWER(sp.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(sp.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SanPham> searchSanPhamByNameOrCode(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale, sp.idDanhMuc.ten,sp.DuongDan, sp.idThuongHieu.ten, sp.idCoAo.ten, sp.idDuoiAo.ten, sp.idKieuDang.ten, sp.idChatLieu.ten, sp.trangThai) " +
            "FROM SanPham sp " +
            "ORDER BY " +
            "CASE WHEN :sortField = 'ngayTao' AND :sortDirection = 'asc' THEN sp.ngayTao END ASC, " +
            "CASE WHEN :sortField = 'ngayTao' AND :sortDirection = 'desc' THEN sp.ngayTao END DESC, " +
            "CASE WHEN :sortField = 'gia' AND :sortDirection = 'asc' THEN sp.gia END ASC, " +
            "CASE WHEN :sortField = 'gia' AND :sortDirection = 'desc' THEN sp.gia END DESC")
    public Page<SanPhanResponse> findAllSorted(
            @Param("sortField") String sortField,
            @Param("sortDirection") String sortDirection,
            Pageable pageable
    );

    //query lọc sản phẩm theo trạng thái
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale, sp.idDanhMuc.ten,sp.DuongDan, sp.idThuongHieu.ten, sp.idCoAo.ten, sp.idDuoiAo.ten, sp.idKieuDang.ten, sp.idChatLieu.ten, sp.trangThai) FROM SanPham sp WHERE sp.trangThai = :trangThai")
    public Page<SanPhanResponse> findAllByTrangThai(@Param("trangThai") int trangThai, Pageable pageable);


}
