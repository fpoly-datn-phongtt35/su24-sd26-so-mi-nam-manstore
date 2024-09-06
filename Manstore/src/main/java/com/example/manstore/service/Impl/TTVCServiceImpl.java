package com.example.manstore.service.Impl;

import com.example.manstore.entity.ThongTinVanChuyen;
import com.example.manstore.repository.ThongTinVanChuyenRepository;
import com.example.manstore.service.TTVCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TTVCServiceImpl implements TTVCService {
    @Autowired
    private ThongTinVanChuyenRepository rp;

    @Override
    public void save(ThongTinVanChuyen ttvc) {
        rp.saveAndFlush(ttvc);
    }

    @Override
    public ThongTinVanChuyen update(ThongTinVanChuyen ttvc) {
        return rp.save(ttvc);
    }

    @Override
    public List<ThongTinVanChuyen> findAll(Sort sort) {
        return rp.findAll(sort);
    }

    @Override
    public void deleteById(String id) {
        rp.deleteById(Integer.valueOf(id));
    }

    @Override
    public ThongTinVanChuyen findById(String id) {
        if (rp.findById(Integer.valueOf(id)).isPresent()) {
            return rp.findById(Integer.valueOf(id)).get();
        } else {
            return null;
        }
    }
}