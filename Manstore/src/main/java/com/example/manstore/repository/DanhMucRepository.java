package com.example.manstore.repository;

import com.example.manstore.dto.respone.DanhMucResponse;
import com.example.manstore.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.DanhMucResponse(dm.id,dm.ma,dm.ten,dm.moTa) FROM DanhMuc dm ")
    public Page<DanhMucResponse> findAllDM(Pageable pageable);

    @Query("SELECT dm FROM DanhMuc dm WHERE LOWER(dm.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DanhMuc> searchDM(@Param("keyword") String keyword, Pageable pageable);
}
