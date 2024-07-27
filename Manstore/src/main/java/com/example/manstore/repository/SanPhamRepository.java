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

    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp")
    public Page<SanPhanResponse> findAllSP(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp WHERE LOWER(sp.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(sp.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SanPham> searchSanPhamByNameOrCode(@Param("keyword") String keyword, Pageable pageable);

    //query lọc sản phẩm theo trạng thái
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.trangThai = :trangThai")
    public Page<SanPhanResponse> findAllByTrangThai(@Param("trangThai") int trangThai, Pageable pageable);

    //query lọc sản phẩm theo danh mục
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.idDanhMuc.id = :idDanhMuc")
    public Page<SanPhanResponse> findAllByDanhMuc(@Param("idDanhMuc") int idDanhMuc, Pageable pageable);

    //quere lọc sản phẩm theo thương hiệu
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.giaSale,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.idThuongHieu.id = :idThuongHieu")
    public Page<SanPhanResponse> findAllByThuongHieu(@Param("idThuongHieu") int idThuongHieu, Pageable pageable);
}
