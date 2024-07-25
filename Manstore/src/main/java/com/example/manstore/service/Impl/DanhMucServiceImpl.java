package com.example.manstore.service.Impl;

import com.example.manstore.entity.DanhMuc;
import com.example.manstore.repository.DanhMucRepository;
import com.example.manstore.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;
    @Override
    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    @Override
    public DanhMuc getDanhMucById(Integer id) {
        Optional<DanhMuc> danhMuc = Optional.ofNullable(danhMucRepository.findById(id).orElse(null));
        return danhMuc.orElse(null);
    }

    @Override
    public DanhMuc save(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    @Override
    public void update(DanhMuc danhMuc) {
        danhMucRepository.save(danhMuc);
    }

    @Override
    public Page<DanhMuc> pageOfDM(Pageable pageable) {
        return danhMucRepository.findAll(pageable);
    }
}
