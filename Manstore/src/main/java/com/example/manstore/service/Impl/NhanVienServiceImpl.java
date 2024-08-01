package com.example.manstore.service.Impl;

import com.example.manstore.entity.NhanVien;
import com.example.manstore.repository.NhanVienRepository;
import com.example.manstore.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public Optional<NhanVien> findById(Integer aLong) {
        return nhanVienRepository.findById(aLong);
    }

    @Override
    public void save(NhanVien nv) {
        nhanVienRepository.save(nv);
    }

    @Override
    public Page<NhanVien> page(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public Page<NhanVien> SearchPage(Pageable pageable, String keyword) {
        return nhanVienRepository.SearchPage(pageable, keyword);
    }

    @Override
    public Page<NhanVien> filterByStatusNoSearch(Pageable pageable, int status) {
        return nhanVienRepository.filterByStatusNoSearch(pageable, status);
    }

    @Override
    public Page<NhanVien> filterByStatusAndSearch(Pageable pageable, String keyword, int status) {
        return nhanVienRepository.filterByStatusAndSearch(pageable,keyword,status);
    }
}
