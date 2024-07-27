package com.example.manstore.service;

import com.example.manstore.entity.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatLieuService {

    List<ChatLieu> getAllChatLieu();

    ChatLieu getChatLieuById(Integer id);

    ChatLieu save(ChatLieu chatLieu);

    void update(ChatLieu chatLieu);

    Page<ChatLieu> pageOfCL(Pageable pageable);
}
