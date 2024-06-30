package com.example.manstore.service.Impl;

import com.example.manstore.entity.ChatLieu;
import com.example.manstore.repository.ChatLieuRepository;
import com.example.manstore.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    @Override
    public ChatLieu getChatLieuById(Integer id) {
        Optional<ChatLieu> chatLieu = chatLieuRepository.findById(id);
        return chatLieu.orElse(null);
    }

    @Override
    public ChatLieu save(ChatLieu chatLieu) {
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public void update(ChatLieu chatLieu) {
        chatLieuRepository.save(chatLieu);
    }

    @Override
    public Page<ChatLieu> pageOfCL(Pageable pageable) {
        return chatLieuRepository.findAll(pageable);
    }
}
