package com.example.manstore.repository;

import com.example.manstore.entity.DiaChi;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {

    @Override
    <S extends DiaChi> S saveAndFlush(S entity);

    @Override
    List<DiaChi> findAll();

    @Override
    <S extends DiaChi> S save(S entity);

    @Override
    Optional<DiaChi> findById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    List<DiaChi> findAll(Sort sort);

    @Query("select dc from DiaChi dc where dc.idKhachHang.id = :id")
    List<DiaChi> getByIdKH(@Param("id") String id);
}
