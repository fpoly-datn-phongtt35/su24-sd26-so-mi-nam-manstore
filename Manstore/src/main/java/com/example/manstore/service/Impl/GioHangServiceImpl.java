package com.example.manstore.service.Impl;


import com.example.manstore.entity.GioHang;
import com.example.manstore.repository.GioHangRepository;
import com.example.manstore.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository rp;

    @Override
    public void save(GioHang gioHang) {
        rp.save(gioHang);
    }

    @Override
    public GioHang getById(String id) {
        if (rp.findById(Integer.parseInt(id)).isPresent()) {
            return rp.findById(Integer.parseInt(id)).get();
        } else {
            return null;
        }
    }

    @Override
    public GioHang findByIdKH(Integer id) {
        if(rp.findByIdKH(id).isPresent()) {
            return rp.findByIdKH(id).get();
        } else {
            return null;
        }
    }
}