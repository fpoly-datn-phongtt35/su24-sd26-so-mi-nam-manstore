package com.example.manstore.service.Impl;

import com.example.manstore.entity.MauSac;
import com.example.manstore.repository.MauSacRepository;
import com.example.manstore.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    @Override
    public MauSac getMauSacById(Integer id) {
        Optional<MauSac> mauSac = mauSacRepository.findById(id);
        return mauSac.orElse(null);
    }

    @Override
    public MauSac save(MauSac mauSac) {
        return mauSacRepository.save(mauSac);
    }

    @Override
    public Page<MauSac> pageOfMauSac(Pageable pageable) {
        return mauSacRepository.findAll(pageable);
    }
}
