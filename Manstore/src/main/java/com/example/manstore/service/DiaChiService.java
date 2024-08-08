package com.example.manstore.service;

import com.example.manstore.entity.DiaChi;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DiaChiService {

    List<DiaChi> findAll(Sort sort);

    List<DiaChi> getByIdKH(String id);

    void deleteById(String id);

    DiaChi getById(String id);

    void save(DiaChi diaChi);

    List<DiaChi> findAllAccount();
}
