package com.example.manstore.service.Impl;

import com.example.manstore.entity.PhuongThucThanhToan;
import com.example.manstore.repository.PhuongThucThanhToanRepository;
import com.example.manstore.service.PhuongThucTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhuongThucTTServiceImpl implements PhuongThucTTService {
    @Autowired
    private PhuongThucThanhToanRepository rp;

    @Override
    public PhuongThucThanhToan getById(String id) {
        return rp.findById(Integer.valueOf(id)).get();
    }
}