package com.example.manstore.service.Impl;

import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.ThuongHieuRepository;
import com.example.manstore.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;


    @Override
    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    // pagination
    public ResponseEntity<?> getAllTH(Pageable pageable) {
        Page<ThuongHieuResponse> thuongHieuResponses = thuongHieuRepository.findAllTH(pageable);
        if (thuongHieuResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(thuongHieuResponses, HttpStatus.OK);
    }
    @Override
    public ThuongHieu getThuongHieuById(Integer id) {
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(id);
        return thuongHieu.orElse(null);
    }

    @Override
    public ThuongHieu save(ThuongHieu thuongHieu) {
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public ThuongHieu update(Integer id, ThuongHieu thuongHieu) {
        if (thuongHieuRepository.existsById(id)) {
            thuongHieu.setId(id);
            return thuongHieuRepository.save(thuongHieu);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        thuongHieuRepository.deleteById(id);
    }

    @Override
    public Page<ThuongHieu> pageOfTH(Pageable pageable) {
        return thuongHieuRepository.findAll(pageable);
    }

    @Override
    public Page<ThuongHieu> SearchTH(String keyword, Pageable pageable) {
        return thuongHieuRepository.searchTH(keyword,pageable);
    }
}
