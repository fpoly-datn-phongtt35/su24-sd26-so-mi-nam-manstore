package com.example.manstore.service.Impl;

import com.example.manstore.entity.DuoiAo;
import com.example.manstore.repository.DuoiAoRepository;
import com.example.manstore.service.DuoiAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DuoiAoServiceImpl implements DuoiAoService {

    @Autowired
    private DuoiAoRepository duoiAoRepository;

    @Override
    public List<DuoiAo> getAllDuoiAo() {
        return duoiAoRepository.findAll();
    }

    @Override
    public DuoiAo getDuoiAoById(Integer id) {
        Optional<DuoiAo> duoiAo = duoiAoRepository.findById(id);
        return duoiAo.orElse(null);
    }

    @Override
    public DuoiAo save(DuoiAo duoiAo) {
        return duoiAoRepository.save(duoiAo);
    }

    @Override
    public void update(DuoiAo duoiAo) {
        duoiAoRepository.save(duoiAo);
    }

    @Override
    public Page<DuoiAo> pageOfDuoiAo(Pageable pageable) {
        return duoiAoRepository.findAll(pageable);
    }

}
