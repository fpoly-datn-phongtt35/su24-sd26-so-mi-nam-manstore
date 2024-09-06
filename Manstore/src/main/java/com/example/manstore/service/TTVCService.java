package com.example.manstore.service;

import com.example.manstore.entity.ThongTinVanChuyen;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TTVCService {

    void save (ThongTinVanChuyen ttvc);

    ThongTinVanChuyen update(ThongTinVanChuyen ttvc);

    List<ThongTinVanChuyen> findAll(Sort sort);

    void deleteById(String id);

    ThongTinVanChuyen findById(String id);
}
