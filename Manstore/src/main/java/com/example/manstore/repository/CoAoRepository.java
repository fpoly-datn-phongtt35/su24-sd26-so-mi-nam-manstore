package com.example.manstore.repository;

import com.example.manstore.dto.respone.CoAoResponse;
import com.example.manstore.entity.CoAo;
import com.example.manstore.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoAoRepository extends JpaRepository<CoAo, Integer> {


    @Query("SELECT ca FROM CoAo ca WHERE LOWER(ca.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<CoAo> searchCA(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.example.manstore.dto.respone.CoAoResponse(c.id, c.ma, c.ten, c.moTa) FROM CoAo c")
    Page<CoAoResponse> findAllCoAo(Pageable pageable);
}
