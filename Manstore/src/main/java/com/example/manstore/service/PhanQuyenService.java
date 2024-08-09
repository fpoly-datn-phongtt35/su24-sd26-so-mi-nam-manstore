package com.example.manstore.service;

import com.example.manstore.entity.PhanQuyen;

import java.util.List;
import java.util.Optional;

public interface PhanQuyenService {
    List<PhanQuyen> getAll();

    Optional<PhanQuyen> findById(Integer aLong);
}
