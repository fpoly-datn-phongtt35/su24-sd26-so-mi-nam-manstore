package com.example.manstore.service.Impl;

import com.example.manstore.entity.DiaChi;
import com.example.manstore.repository.DiaChiRepository;
import com.example.manstore.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository rp;

    @Override
    public List<DiaChi> findAll(Sort sort) {
        return rp.findAll(sort);
    }

    @Override
    public List<DiaChi> getByIdKH(String id) {
        return rp.getByIdKH(id);
    }

    @Override
    public void deleteById(String id) {
        rp.deleteById(Integer.parseInt(id));
    }

    @Override
    public DiaChi getById(String id) {
        if(rp.findById(Integer.parseInt(id)).isPresent()) {
            return rp.findById(Integer.parseInt(id)).get();
        } else {
            return null;
        }
    }

    @Override
    public void save(DiaChi diaChi) {
        rp.save(diaChi);
    }

    @Override
    public List<DiaChi> findAllAccount() {
        return rp.findAll();
    }
}
