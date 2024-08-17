package com.example.manstore.service;

import com.example.manstore.entity.ThongBao;

import java.util.List;

public interface ThongBaoService {

    void save(ThongBao thongBao);

    void delete(String id);

    List<ThongBao> findAll();

    List<ThongBao> findByInvoice(String id);

    List<ThongBao> findByCustomer(String id);

    List<ThongBao> findByStaff(String id);

    ThongBao getDate(String id, String status);

    List<ThongBao> search(String keyword);

    List<ThongBao> findAllByStatus();
}
