package com.example.manstore.repository;

import com.example.manstore.dto.respone.ChatLieuResponse;
import com.example.manstore.entity.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {

    @Query("SELECT new com.example.manstore.dto.respone.ChatLieuResponse(cl.id, cl.ma, cl.ten, cl.moTa) FROM ChatLieu cl")
    Page<ChatLieuResponse> findAllChatLieu(Pageable pageable);

    @Query("SELECT cl FROM ChatLieu cl WHERE LOWER(cl.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ChatLieu> searchCL(@Param("keyword") String keyword, Pageable pageable);
}
