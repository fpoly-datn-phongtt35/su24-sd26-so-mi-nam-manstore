package com.example.manstore.service.Impl;

import com.example.manstore.entity.PhanQuyen;
import com.example.manstore.repository.PhanQuyenRepository;
import com.example.manstore.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhanQuyenServiceImpl implements PhanQuyenService {
    @Autowired
    PhanQuyenRepository phanQuyenRepository;

    @Override
    public List<PhanQuyen> getAll() {
        return phanQuyenRepository.findAll();
    }

    @Override
    public Optional<PhanQuyen> findById(Integer aLong) {
        return phanQuyenRepository.findById(aLong);
    }
}
