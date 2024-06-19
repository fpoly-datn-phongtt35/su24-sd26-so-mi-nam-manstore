package com.example.manstore.service;

import com.example.manstore.entity.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface DotGiamGiaService {

    Page<DotGiamGia> findAll(LocalDate start, LocalDate end, String typePromotion, Pageable pageable);

    List<DotGiamGia> getAll();

    Optional<DotGiamGia> findById(Integer id);
}
