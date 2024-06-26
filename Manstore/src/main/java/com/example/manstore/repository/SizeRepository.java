package com.example.manstore.repository;

import com.example.manstore.dto.respone.SizeResponse;
import com.example.manstore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.SizeResponse(s.id,s.ma,s.ten,s.moTa) FROM Size s ")
    public Page<SizeResponse> findAllSize(Pageable pageable);

    @Query("SELECT s FROM Size s WHERE LOWER(s.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Size> searchSize(@Param("keyword") String keyword, Pageable pageable);


}
