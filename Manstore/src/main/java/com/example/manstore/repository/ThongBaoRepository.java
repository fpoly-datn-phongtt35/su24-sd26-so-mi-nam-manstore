package com.example.manstore.repository;

import com.example.manstore.entity.ThongBao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {

    @Override
    <S extends ThongBao> S saveAndFlush(S entity);

    @Override
    List<ThongBao> findAll();

    @Override
    Optional<ThongBao> findById(Integer aLong);

    @Override
    boolean existsById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    List<ThongBao> findAll(Sort sort);

    @Query("select tb from ThongBao tb where tb.trangThaiDonHang > 0")
    List<ThongBao> findAllByStatus();

    @Query("select tb from ThongBao tb where tb.idHoaDon.id = :id")
    List<ThongBao> getNotificationByInvoice(@Param("id") String id, Sort sort);

    @Query("select tb from ThongBao tb where tb.idKhachHang.id = :id")
    List<ThongBao> getNotificationByCustomer(@Param("id") String id, Sort sort);

    @Query("select tb from ThongBao tb where tb.idNhanVien.id = :id")
    List<ThongBao> getNotificationByStaff(@Param("id") String id, Sort sort);

    @Query("select tb from ThongBao tb where tb.idHoaDon.id = :id and tb.idHoaDon.trangThai=:status")
    ThongBao getDate(@Param("id") String id, @Param("status") String status);

    @Query("select tb from ThongBao tb where tb.idHoaDon.ma like %:keyword% and tb.trangThaiDonHang > 0")
    List<ThongBao> searchHistory(@Param("keyword") String keyword);
}
