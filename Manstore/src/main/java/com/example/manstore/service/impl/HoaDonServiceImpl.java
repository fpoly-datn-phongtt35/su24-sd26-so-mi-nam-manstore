package com.example.manstore.service.impl;

import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.HoaDonRepository;
import com.example.manstore.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;


    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepository.findById(id);
    }

    @Override
    public HoaDon findByHD(String hd) {
        if (hoaDonRepository.findByHD(hd).isPresent()) {
            return hoaDonRepository.findByHD(hd).get();
        } else {
            return null;
        }
    }

    @Override
    public void save(HoaDon hd) {
        hoaDonRepository.save(hd);
    }

    @Override
    public Page<HoaDon> page(Pageable pageable) {
        return hoaDonRepository.findAllBut0(pageable);
    }

    @Override
    public List<HoaDon> getByPromotion(String idPromotion) {
        return hoaDonRepository.findByPromotion(idPromotion);
    }
}
}
