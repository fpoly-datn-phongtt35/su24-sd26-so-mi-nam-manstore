package com.example.manstore.service;

import com.example.manstore.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MauSacService {
    List<MauSac> getAllMauSac();
    MauSac getMauSacById(Integer id);

    MauSac save(MauSac mauSac);

    void update(MauSac mauSac);
    Page<MauSac> pageOfMauSac(Pageable pageable);
}
