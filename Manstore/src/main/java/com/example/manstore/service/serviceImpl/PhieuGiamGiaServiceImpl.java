package com.example.manstore.service.serviceImpl;

import com.example.manstore.dto.request.PhieuGiamGiaRequest;
import com.example.manstore.entity.PhieuGiamGia;
import com.example.manstore.repository.PhieuGiamGiaRepository;
import com.example.manstore.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;
    @Override
    public List<PhieuGiamGiaRequest> getAllPhieuGiamGia(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<PhieuGiamGia> phieuGiamGias = phieuGiamGiaRepository.findAll(pageable);
        List<PhieuGiamGiaRequest> phieuGiamGiaRequest = new ArrayList<>();
        for (PhieuGiamGia i : phieuGiamGias.getContent()) {
            PhieuGiamGiaRequest request = phieuGiamGiaRepository.getPhieuGiamById(i.getId());
            phieuGiamGiaRequest.add(request);
        }
        return phieuGiamGiaRequest;
    }

    @Override
    public PhieuGiamGiaRequest createPhieuGiamGia(PhieuGiamGia phieuGiamGia) {
        PhieuGiamGia p = phieuGiamGiaRepository.save(phieuGiamGia);
        return phieuGiamGiaRepository.getPhieuGiamById(p.getId());
    }

    @Override
    public PhieuGiamGiaRequest updatePhieuGiamGia(PhieuGiamGia phieuGiamGia, Integer id) {
        Optional<PhieuGiamGia> p = phieuGiamGiaRepository.findById(id);
        if(p.isEmpty()) {
            return null;
        }
        p.get().setTieuDe(phieuGiamGia.getTieuDe());
        p.get().setNgayMo(phieuGiamGia.getNgayMo());
        p.get().setNgayDong(phieuGiamGia.getNgayDong());
        p.get().setSoLuong(phieuGiamGia.getSoLuong());
        p.get().setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
        p.get().setGiaTriGiam(phieuGiamGia.getGiaTriGiam());
        p.get().setPhuongThucThanhToan(phieuGiamGia.getPhuongThucThanhToan());
        p.get().setDieuKien(phieuGiamGia.getDieuKien());
        p.get().setTrangThai(phieuGiamGia.getTrangThai());
        PhieuGiamGia phieuGiamGia1 = phieuGiamGiaRepository.save(p.get());
        return phieuGiamGiaRepository.getPhieuGiamById(phieuGiamGia1.getId());
    }

    @Override
    public List<PhieuGiamGiaRequest> getPhieuGiamByLoaiGiamGia(String loaiGiamGia) {
        Boolean loaiGiamGia1 = null;
        if(loaiGiamGia.equals("%")){
            loaiGiamGia1 = false;
        }
        if (loaiGiamGia.equals("vnđ")){
            loaiGiamGia1 = true;
        }
        System.out.println(loaiGiamGia1);
        return phieuGiamGiaRepository.getByType(loaiGiamGia1);
    }
    @Override
    public PhieuGiamGiaRequest getById(Integer id) {
        Optional<PhieuGiamGia> phieuGiamGia = phieuGiamGiaRepository.findById(id);
        if(phieuGiamGia.isEmpty()) {
            return null;
        }
        PhieuGiamGiaRequest request = phieuGiamGiaRepository.getPhieuGiamById(phieuGiamGia.get().getId());
        return request;
    }
    @Override
    public List<PhieuGiamGiaRequest> searchByName(String tieuDe) {
        return phieuGiamGiaRepository.searchByName(tieuDe);
    }
}
