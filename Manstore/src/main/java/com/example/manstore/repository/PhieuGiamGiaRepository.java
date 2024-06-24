package com.example.manstore.repository;

import com.example.manstore.dto.request.PhieuGiamGiaRequest;
import com.example.manstore.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {

//    @Query("SELECT new ProductsViewDTO(p.id, p.productType.name, p.nameProduct, p.price, "
//            + "p.avartarImageProduct, p.title, p.discount) FROM Products p WHERE p.id = :id")
    @Query("SELECT new PhieuGiamGiaRequest(p.id, p.ma, p.tieuDe, p.ngayMo, p.ngayDong, p.soLuong, p.loaiGiamGia," +
            "p.giaTriGiam, p.phuongThucThanhToan, p.dieuKien, p.trangThai) FROM PhieuGiamGia p WHERE p.id = :id")
    public PhieuGiamGiaRequest getPhieuGiamById(@Param("id") Integer id);

    @Query("SELECT new PhieuGiamGiaRequest(p.id, p.ma, p.tieuDe, p.ngayMo, p.ngayDong, p.soLuong, p.loaiGiamGia," +
            "p.giaTriGiam, p.phuongThucThanhToan, p.dieuKien, p.trangThai) FROM PhieuGiamGia p WHERE p.loaiGiamGia = :loaiGiamGia")
    public List<PhieuGiamGiaRequest> getByType(@Param("loaiGiamGia") Boolean loaiGiamGia);

    @Query("SELECT new PhieuGiamGiaRequest(p.id, p.ma, p.tieuDe, p.ngayMo, p.ngayDong, p.soLuong, p.loaiGiamGia," +
            "p.giaTriGiam, p.phuongThucThanhToan, p.dieuKien, p.trangThai) FROM PhieuGiamGia p WHERE p.tieuDe LIKE %:tieuDe%")
    public List<PhieuGiamGiaRequest> searchByName(@Param("tieuDe") String tieuDe);
}
