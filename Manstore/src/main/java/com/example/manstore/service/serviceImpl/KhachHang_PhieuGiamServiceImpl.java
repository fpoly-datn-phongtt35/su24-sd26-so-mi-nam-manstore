package com.example.manstore.service.serviceImpl;

import com.example.manstore.dto.request.KhachHang_PhieuGiamRequest;
import com.example.manstore.entity.KhahhangPhieugiam;
import com.example.manstore.repository.KhachHang_PhieuGiamRepository;
import com.example.manstore.service.KhachHang_PhieuGiamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KhachHang_PhieuGiamServiceImpl implements KhachHang_PhieuGiamService {

    @Autowired
    KhachHang_PhieuGiamRepository khachHangPhieuGiamRepository;

    @Override
    public List<KhachHang_PhieuGiamRequest> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<KhahhangPhieugiam> phieuGiam = khachHangPhieuGiamRepository.findAll(pageable);
        List<KhachHang_PhieuGiamRequest> phieuGiamGiaRequest = new ArrayList<>();
        for (KhahhangPhieugiam i : phieuGiam.getContent()) {
            KhachHang_PhieuGiamRequest request = khachHangPhieuGiamRepository.getKhahhangPhieugiamById(i.getId());
            phieuGiamGiaRequest.add(request);
        }
        return phieuGiamGiaRequest;
    }
}
