package com.example.manstore.repository;

import com.example.manstore.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {

    @Query("select dc from DiaChi dc where dc.idKhachHang.id = :id")
    List<DiaChi> getByIdKH(@Param("id")String id);
}
