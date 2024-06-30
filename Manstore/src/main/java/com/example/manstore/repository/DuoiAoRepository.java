package com.example.manstore.repository;

import com.example.manstore.dto.respone.DuoiAoResponse;
import com.example.manstore.entity.DuoiAo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DuoiAoRepository extends JpaRepository<DuoiAo,Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.DuoiAoResponse(da.id,da.ma,da.ten,da.moTa) FROM DuoiAo da ")
    public Page<DuoiAoResponse> findAllDA(Pageable pageable);

    @Query("SELECT da FROM DuoiAo da WHERE LOWER(da.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DuoiAo> searchDA(@Param("keyword") String keyword, Pageable pageable);
}
