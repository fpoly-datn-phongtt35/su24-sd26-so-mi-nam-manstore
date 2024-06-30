package com.example.manstore.service;

import com.example.manstore.entity.CoAo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoAoService {

    List<CoAo> getAllCoAo();

    CoAo getCoAoById(Integer id);

    CoAo save(CoAo coAo);

    void update(CoAo coAo);

    Page<CoAo> pageOfCoAo(Pageable pageable);
}
