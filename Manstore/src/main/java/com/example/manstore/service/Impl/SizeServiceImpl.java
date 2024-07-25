package com.example.manstore.service.Impl;

import com.example.manstore.dto.respone.SizeResponse;
import com.example.manstore.entity.Size;
import com.example.manstore.repository.SizeRepository;
import com.example.manstore.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;
    @Override
    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getSizeById(Integer id) {
        Optional<Size> size = sizeRepository.findById(id);
        return size.orElse(null);
    }

    @Override
    public Size save(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public void update(Size size) {
        sizeRepository.save(size);
    }

    @Override
    public Page<Size> pageOfSize(Pageable pageable) {
        return sizeRepository.findAll(pageable);
    }

    //pagination
    public ResponseEntity<?> getAllSize(Pageable pageable) {
        Page<SizeResponse> sizeReponses = sizeRepository.findAllSize(pageable);
        if (sizeReponses.isEmpty()) {
            return new ResponseEntity<>(sizeReponses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sizeReponses, HttpStatus.OK);
    }
}
