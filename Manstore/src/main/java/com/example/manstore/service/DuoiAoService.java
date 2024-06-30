package com.example.manstore.service;

import com.example.manstore.entity.DuoiAo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DuoiAoService {

    List<DuoiAo> getAllDuoiAo();

    DuoiAo getDuoiAoById(Integer id);

    DuoiAo save(DuoiAo duoiAo);

    void update(DuoiAo duoiAo);

    Page<DuoiAo> pageOfDuoiAo(Pageable pageable);
}
