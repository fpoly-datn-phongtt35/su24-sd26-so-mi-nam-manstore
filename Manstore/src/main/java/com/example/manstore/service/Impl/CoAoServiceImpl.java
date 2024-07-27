package com.example.manstore.service.Impl;

import com.example.manstore.entity.CoAo;
import com.example.manstore.repository.CoAoRepository;
import com.example.manstore.service.CoAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoAoServiceImpl implements CoAoService {

    @Autowired
    private CoAoRepository coAoRepository;
    @Override
    public List<CoAo> getAllCoAo() {
        return coAoRepository.findAll();
    }

    @Override
    public CoAo getCoAoById(Integer id) {
        Optional<CoAo> coAo = coAoRepository.findById(id);
        return coAo.orElse(null);
    }

    @Override
    public CoAo save(CoAo coAo) {
        return coAoRepository.save(coAo);
    }

    @Override
    public void update(CoAo coAo) {
        coAoRepository.save(coAo);
    }

    @Override
    public Page<CoAo> pageOfCoAo(Pageable pageable) {
        return coAoRepository.findAll(pageable);
    }
}
