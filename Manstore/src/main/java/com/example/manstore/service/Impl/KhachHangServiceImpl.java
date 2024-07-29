package com.example.manstore.service.Impl;

import com.example.manstore.entity.KhachHang;
import com.example.manstore.repository.KhachHangRepository;
import com.example.manstore.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public void update(KhachHang khachHang) {
        khachHangRepository.save(khachHang);
    }

    @Override
    public void save(KhachHang kh) {
        khachHangRepository.save(kh);
    }

    @Override
    public List<KhachHang> getALL() {
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHang getByID(Integer id) {
        return khachHangRepository.findById(id).get();
    }

    @Override
    public Optional<KhachHang> findById(Integer aLong) {
        return khachHangRepository.findById(aLong);
    }

    @Override
    public Page<KhachHang> pageOfKhachHang(Pageable pageable) {
        return khachHangRepository.findAll(pageable);
    }

    @Override
    public Page<KhachHang> seacrch(String keyword, Pageable pageable) {
        return khachHangRepository.searchKhachHang(keyword, pageable);
    }

    @Override
    public Page<KhachHang> fillterByGenderNoSearch(Pageable pageable, boolean gender) {
        return khachHangRepository.filterByGenderNoSearch(pageable,gender);
    }

    @Override
    public Page<KhachHang> fillterByGenderAndSearch(Pageable pageable, String keyword, boolean gender) {
        return khachHangRepository.filterByGenderAndSearch(pageable,keyword,gender);
    }

    @Override
    public KhachHang getByPhone(String phone) {
        return khachHangRepository.findByPhone(phone).isPresent() ? khachHangRepository.findByPhone(phone).get() : null;
    }

}
