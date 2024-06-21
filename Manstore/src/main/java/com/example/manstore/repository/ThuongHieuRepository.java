package com.example.manstore.repository;

import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu,Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.ThuongHieuResponse(th.id,th.ma,th.ten,th.moTa) FROM ThuongHieu th ")
    public Page<ThuongHieuResponse> findAllTH(Pageable pageable);
    @Query("SELECT th FROM ThuongHieu th WHERE th.ten LIKE %:keyword%")
    Page<ThuongHieu> searchTH(@Param("keyword") String keyword, Pageable pageable);
}
