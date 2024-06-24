package com.example.manstore.service.serviceImpl;

import com.example.manstore.dto.request.HoaDon_PhieuGiamRequest;
import com.example.manstore.entity.HoadonPhieugiam;
import com.example.manstore.repository.HoaDon_PhieuGiamRepository;
import com.example.manstore.service.HoaDon_PhieuGiamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HoaDon_PhieuGiamServiceImpl implements HoaDon_PhieuGiamService {

    @Autowired
    HoaDon_PhieuGiamRepository hoaDonPhieuGiamRepository;

    @Override
    public List<HoaDon_PhieuGiamRequest> getAllPhieuGiam(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<HoadonPhieugiam> phieuGiam = hoaDonPhieuGiamRepository.findAll(pageable);
        List<HoaDon_PhieuGiamRequest> phieuGiamGiaRequest = new ArrayList<>();
        for (HoadonPhieugiam i : phieuGiam.getContent()) {
            HoaDon_PhieuGiamRequest request = hoaDonPhieuGiamRepository.getHdPhieuGiamById(i.getId());
            phieuGiamGiaRequest.add(request);
        }
        return phieuGiamGiaRequest;
    }

    @Override
    public HoaDon_PhieuGiamRequest createPhieuGiam(HoadonPhieugiam hdPhieuGiam) {
        return null;
    }
}
