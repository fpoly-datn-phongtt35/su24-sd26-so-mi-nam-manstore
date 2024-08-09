package com.example.manstore.repository;

import com.example.manstore.entity.GioHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    @Override
    <S extends GioHang> S save(S entity);

    @Override
    Optional<GioHang> findById(Integer aLong);

    @Override
    boolean existsById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    Page<GioHang> findAll(Pageable pageable);

    @Query("SELECT gh FROM GioHang gh where gh.idKhachHang.id=:IdKH")
    Optional<GioHang> findByIdKH(@Param("IdKH") Integer id);
}
