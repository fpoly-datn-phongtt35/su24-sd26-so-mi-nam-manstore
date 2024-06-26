package com.example.manstore.service;

import com.example.manstore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SizeService {
    List<Size> getAllSize();
    Size getSizeById(Integer id);

    Size save(Size size);

    void update(Size size);
    Page<Size> pageOfSize(Pageable pageable);
}
