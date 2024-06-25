package com.example.manstore.repository;

import com.example.manstore.dto.respone.KieuDangResponse;
import com.example.manstore.entity.KieuDang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KieuDangRepository extends JpaRepository<KieuDang, Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.KieuDangResponse(kd.id, kd.ma, kd.ten, kd.moTa) FROM KieuDang kd")
    public Page<KieuDangResponse> findAllKieuDang(Pageable pageable);


}
