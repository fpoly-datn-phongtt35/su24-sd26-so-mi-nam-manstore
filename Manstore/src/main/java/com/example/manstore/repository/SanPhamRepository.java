package com.example.manstore.repository;

import com.example.manstore.dto.respone.SanPhanResponse;
import com.example.manstore.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp")
    public Page<SanPhanResponse> findAllSP(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp WHERE LOWER(sp.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(sp.ma) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SanPham> searchSanPhamByNameOrCode(@Param("keyword") String keyword, Pageable pageable);

    //query lọc sản phẩm theo trạng thái
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia,sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.trangThai = :trangThai")
    public Page<SanPhanResponse> findAllByTrangThai(@Param("trangThai") int trangThai, Pageable pageable);

    //query lọc sản phẩm theo danh mục
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.idDanhMuc.id = :idDanhMuc")
    public Page<SanPhanResponse> findAllByDanhMuc(@Param("idDanhMuc") int idDanhMuc, Pageable pageable);

    //quere lọc sản phẩm theo thương hiệu
    @Query("SELECT new com.example.manstore.dto.respone.SanPhanResponse(sp.id, sp.ma, sp.ten, sp.soLuong, sp.ngayTao, sp.gia, sp.idDanhMuc.id,sp.idDanhMuc.ten,sp.DuongDan,sp.idThuongHieu.id,sp.idThuongHieu.ten,sp.idDuoiAo.id,sp.idDuoiAo.ten,sp.idKieuDang.id,sp.idKieuDang.ten,sp.idChatLieu.id,sp.idChatLieu.ten,sp.trangThai) FROM SanPham sp WHERE sp.idThuongHieu.id = :idThuongHieu")
    public Page<SanPhanResponse> findAllByThuongHieu(@Param("idThuongHieu") int idThuongHieu, Pageable pageable);


    //Client ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Price max
    @Query("Select MAX(sp.gia) FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1")
    Integer priceMax();

    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1")
    Page<SanPham> pageClient(Pageable pageable);

    //color
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idMauSac.id in :color")
    Page<SanPham> filterColor(Pageable pageable, @Param("color") List<Integer> color);

    //size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idSize.ten in :size")
    Page<SanPham> filterSize(Pageable pageable, @Param("size") List<String> size);

    //brand
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE sp.idThuongHieu.id in :trademark")
    Page<SanPham> filterTrademark(@Param("trademark") String trademark, Pageable pageable);

    //category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE sp.idDanhMuc.id in :category")
    Page<SanPham> filterCatergory(@Param("category") String category, Pageable pageable);

    //price
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)")
    Page<SanPham> price(Pageable pageable, @Param("priceStart") String priceStart, @Param("priceEnd") String priceEnd);

    //color+size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idMauSac.id in :color) AND (spct.idSize.ten in :size)")
    Page<SanPham> filterColorAndSize(Pageable pageable, @Param("color") List<Integer> color, @Param("size") List<String> size);

    //color+trademark
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idMauSac.id in :color and sp.idThuongHieu.id = :trademark")
    Page<SanPham> filterColorAndTrademark(Pageable pageable,
                                          @Param("color") List<Integer> color, @Param("trademark") String trademark);

    //color+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idMauSac.id in :color and sp.idDanhMuc.id = :category")
    Page<SanPham> filterColorAndCategory(Pageable pageable,
                                         @Param("color") List<Integer> color, @Param("category") String category);

    //size+trademark
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idSize.ten in :size and sp.idThuongHieu.id = :trademark")
    Page<SanPham> filterSizeAndTrademark(Pageable pageable, @Param("size") List<String> size, @Param("trademark") String trademark);

    //size+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idSize.ten in :size and sp.idDanhMuc.id = :category")
    Page<SanPham> filterSizeAndCategory(Pageable pageable, @Param("size") List<String> size, @Param("category") String category);


    //Trademark+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE spct.idSanPham.idThuongHieu.id = :trademark and sp.idDanhMuc.id = :category")
    Page<SanPham> filterTrademarkAndCategory(Pageable pageable, @Param("trademark") String trademark, @Param("category") String category);

    //price+trademark
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(sp.idThuongHieu.id = :trademark)")
    Page<SanPham> priceAndFilterTrademark(Pageable pageable, @Param("priceStart") String priceStart,
                                          @Param("priceEnd") String priceEnd, @Param("trademark") String trademark);

    //price+color
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idMauSac.id in :color)")
    Page<SanPham> priceAndFilterColor(Pageable pageable, @Param("priceStart") String priceStart, @Param("priceEnd") String priceEnd, @Param("color") List<Integer> color);

    //price+size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idSize.ten in :size)")
    Page<SanPham> priceAndFilterSize(Pageable pageable, @Param("priceStart") String priceStart, @Param("priceEnd") String priceEnd, @Param("size") List<String> size);

    //price+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(sp.idDanhMuc.id = :category)")
    Page<SanPham> filterPriceAndCategory(Pageable pageable, @Param("priceStart") String priceStart, @Param("priceEnd") String priceEnd, @Param("category") String category);

    //color+size+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idMauSac.id in :color) and (spct.idSize.ten in :size) and(sp.idDanhMuc.id = :category)")
    Page<SanPham> filterColorAndSizeAndCategory(Pageable pageable, @Param("color") List<Integer> color,
                                             @Param("size") List<String> size, @Param("category") String category);

    //price+color+size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idMauSac.id in :color)AND(spct.idSize.ten in :size)")
    Page<SanPham> priceAndFilterColorAndSize(Pageable pageable, @Param("priceStart") String priceStart, @Param("priceEnd") String priceEnd, @Param("color") List<Integer> color, @Param("size") List<String> size);

    //price+color+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(sp.idDanhMuc.id = :category)AND(spct.idMauSac.id in :color)")
    Page<SanPham> priceAndFilterColorAndCategory(Pageable pageable, @Param("priceStart") String priceStart,
                                              @Param("priceEnd") String priceEnd, @Param("category") String category,
                                              @Param("color") List<Integer> color);


    //price+size+trademark
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(sp.idThuongHieu.id = :trademark)AND(spct.idSize.ten in :size)")
    Page<SanPham> priceAndFilterSizeAndTrademark(Pageable pageable, @Param("priceStart") String priceStart,
                                             @Param("priceEnd") String priceEnd, @Param("trademark") String trademark,
                                             @Param("size") List<String> size);

    //price+size+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart) AND (sp.idDanhMuc.id = :category)AND(spct.idSize.ten in :size)")
    Page<SanPham> priceAndFilterSizeAndCategory(Pageable pageable, @Param("priceStart") String priceStart,
                                             @Param("priceEnd") String priceEnd, @Param("category") String category,
                                             @Param("size") List<String> size);

    //price+trademark+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(sp.idDanhMuc.id = :category)AND(sp.idThuongHieu.id = :trademark)")
    Page<SanPham> priceAndFilterTrademarkAndCategory(Pageable pageable, @Param("priceStart") String priceStart,
                                              @Param("priceEnd") String priceEnd, @Param("category") String category,
                                              @Param("trademark") String trademark);
    //price+trademark+size+color
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idMauSac.id in :color)AND(spct.idSize.ten in :size)AND(sp.idThuongHieu.id = :trademark)")
    Page<SanPham> priceAndFilterTrademarkAndSizeAndColor(Pageable pageable, @Param("priceStart") String priceStart,
                                                     @Param("priceEnd") String priceEnd, @Param("color") List<Integer> color,
                                                     @Param("size") List<String> size, @Param("trademark") String trademark);

    //price+category+size+color
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idMauSac.id in :color)AND(spct.idSize.ten in :size)AND(sp.idDanhMuc.id = :category)")
    Page<SanPham> priceAndFilterCategoryAndSizeAndColor(Pageable pageable, @Param("priceStart") String priceStart,
                                                     @Param("priceEnd") String priceEnd, @Param("color") List<Integer> color,
                                                     @Param("size") List<String> size, @Param("category") String category);

    //price+category+trademark+color
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idMauSac.id in :color)AND(sp.idThuongHieu.id = :trademark)AND(sp.idDanhMuc.id = :category)")
    Page<SanPham> priceAndFilterCategoryAndTrademarkAndColor(Pageable pageable, @Param("priceStart") String priceStart,
                                                      @Param("priceEnd") String priceEnd, @Param("color") List<Integer> color,
                                                      @Param("trademark") String trademark, @Param("category") String category);

    //price+category+trademark+size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idSize.ten in :size)AND(sp.idThuongHieu.id = :trademark)AND(sp.idDanhMuc.id = :category)")
    Page<SanPham> priceAndFilterCategoryAndTrademarkAndSize(Pageable pageable, @Param("priceStart") String priceStart,
                                                     @Param("priceEnd") String priceEnd, @Param("size") List<String> size,
                                                     @Param("trademark") String trademark, @Param("category") String category);

    //filter All(color+size+price+trademark+category)
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (sp.gia <= :priceEnd and sp.gia >= :priceStart)AND(spct.idSize.ten in :size)AND(spct.idMauSac.id in :color)AND(sp.idThuongHieu.id = :trademark)AND(sp.idDanhMuc.id = :category)")
    Page<SanPham> filterAll1(Pageable pageable, @Param("priceStart") String priceStart,
                             @Param("priceEnd") String priceEnd, @Param("size") List<String> size,
                             @Param("color") List<Integer> color,
                             @Param("trademark") String brand, @Param("category") String category);


    //color+size+trademark
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idMauSac.id in :color) and (spct.idSize.ten in :size) and(sp.idThuongHieu.id = :trademark)")
    Page<SanPham> filterColorAndSizeAndTrademark(Pageable pageable, @Param("color") List<Integer> color,
                                             @Param("size") List<String> size, @Param("trademark") String trademark);

    //color+trademark+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idMauSac.id in :color) and (sp.idThuongHieu.id = :trademark) and(sp.idDanhMuc.id = :category)")
    Page<SanPham> filterColorAndTrademarkAndCategory(Pageable pageable, @Param("color") List<Integer> color,
                                              @Param("trademark") String trademark, @Param("category") String category);

    //size+trademark+category
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idSize.ten in :size) AND (sp.idThuongHieu.id = :trademark) AND (sp.idDanhMuc.id in :category)")
    Page<SanPham> filterSizeAndCategoryAndTrademark(Pageable pageable, @Param("size") List<String> size,
                                             @Param("trademark") String trademark, @Param("category") String category);

    //color+trademark+category+size
    @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietSanPham spct ON sp.id = spct.idSanPham.id and sp.trangThai=1 WHERE (spct.idMauSac.id in :color) and (spct.idSize.ten in :size) and (sp.idThuongHieu.id = :trademark) and(sp.idDanhMuc.id = :category)")
    Page<SanPham> filterColorAndTrademarkAndCategorytAndSize(Pageable pageable, @Param("color") List<Integer> color,
                                                     @Param("size") List<String> size,
                                                     @Param("trademark") String trademark, @Param("category") String category);

    @Query(value = "select top 1000 sp.*, \n" +
            "            (select sum(ctdhsl.SoLuong) from ChiTietHoaDon ctdhsl inner join ChiTietSanPham spctsl on spctsl.id = ctdhsl.idChiTietSanPham \n" +
            "            inner join HoaDon dhsl on dhsl.id = ctdhsl.idHoaDon\n" +
            "            where spctsl.idSanPham = sp.id and dhsl.NgayTao >= ?1 and dhsl.NgayTao <= ?2 and dhsl.TrangThai = 4) \n" +
            "            as Soluong\n" +
            "            from ChiTietHoaDon ctdh inner join ChiTietSanPham spct on spct.id = ctdh.idChiTietSanPham\n" +
            "            inner join HoaDon dh on dh.id = ctdh.idHoaDon\n" +
            "            inner join SanPham sp on sp.id = spct.idSanPham where dh.NgayTao >= ?1 and dh.NgayTao <= ?2 and dh.TrangThai = 4\n" +
            "            group by sp.id, sp.Ma, sp.Ten, sp.SoLuong, sp.NgayTao, sp.Gia, sp.MoTa\n" +
            "            ,sp.TrangThai, sp.idThuongHieu, sp.idDanhMuc, sp.idCoAo, sp.idDuoiAo, sp.idKieuDang, sp.idChatLieu, sp.DuongDan", nativeQuery = true)
    List<SanPham> sanPhamBanChayByDate(String from, String to);

    @Query(value = "select sum(ctdhsl.SoLuong) from ChiTietHoaDon ctdhsl inner join ChiTietSanPham spctsl on spctsl.id = ctdhsl.idChiTietSanPham \n" +
            "inner join HoaDon dhsl on dhsl.id = ctdhsl.idHoaDon and dhsl.TrangThai = 4 \n" +
            "where spctsl.idSanPham = ?1 and dhsl.NgayTao >= ?2 and dhsl.NgayTao <= ?3", nativeQuery = true)
    Integer soLuongBan(Integer idSp, String from, String to);

}
