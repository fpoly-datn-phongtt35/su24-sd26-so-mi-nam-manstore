package com.example.manstore.service.Impl;

import com.example.manstore.entity.KieuDang;
import com.example.manstore.repository.KieuDangRepository;
import com.example.manstore.service.KieuDangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class KieuDangServiceImpl implements KieuDangService {

    @Autowired
    private KieuDangRepository kieuDangRepository;
    @Override
    public List<KieuDang> getAllKieuDang() {
        return kieuDangRepository.findAll();
    }

    @Override
    public KieuDang getKieuDangById(Integer id) {
        Optional<KieuDang> kieuDang = kieuDangRepository.findById(id);
        return kieuDang.orElse(null);
    }

    @Override
    public KieuDang save(KieuDang kieuDang) {
        return kieuDangRepository.save(kieuDang);
    }

    @Override
    public Page<KieuDang> pageOfKieuDang(Pageable pageable) {
        return kieuDangRepository.findAll(pageable);
    }
}
