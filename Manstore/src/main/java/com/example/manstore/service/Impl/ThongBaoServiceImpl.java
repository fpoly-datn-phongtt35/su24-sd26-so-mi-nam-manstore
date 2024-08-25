package com.example.manstore.service.Impl;

import com.example.manstore.entity.ThongBao;

import com.example.manstore.repository.ThongBaoRepository;
import com.example.manstore.service.ThongBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongBaoServiceImpl implements ThongBaoService {

    @Autowired
    ThongBaoRepository rp;

    Sort sort = Sort.by("ngayGui").descending();

    @Override
    public void save(ThongBao thongBao) {
        rp.saveAndFlush(thongBao);
    }

    @Override
    public void delete(String id) {
        rp.deleteById(Integer.valueOf((id)));
    }

    @Override
    public List<ThongBao> findAll() {
        return rp.findAll(sort);
    }

    @Override
    public List<ThongBao> findByInvoice(String id) {
        return rp.getNotificationByInvoice(id,sort);
    }

    @Override
    public List<ThongBao> findByCustomer(String id) {
        return rp.getNotificationByCustomer(id,sort);
    }

    @Override
    public List<ThongBao> findByStaff(String id) {
        return rp.getNotificationByStaff(id,sort);
    }

    @Override
    public ThongBao getDate(String id, String status) {
        return rp.getDate(id,status);
    }

    @Override
    public List<ThongBao> search(String keyword) {
        return rp.searchHistory(keyword);
    }

    @Override
    public List<ThongBao> findAllByStatus() {
        return rp.findAllByStatus();
    }
}