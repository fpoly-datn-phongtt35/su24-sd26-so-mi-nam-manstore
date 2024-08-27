package com.example.manstore.service.Impl;
import com.example.manstore.entity.ChiTietHoaDon;
import com.example.manstore.repository.HoaDonChiTietRepository;
import com.example.manstore.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository rp;


    @Override
    public Page<ChiTietHoaDon> getById(String id, Pageable pageable) {
        return rp.getByIdDH(id, pageable);
    }

    @Override
    public void save(ChiTietHoaDon dhct) {
        System.out.println(dhct);
        rp.save(dhct);
    }

    @Override
    public List<ChiTietHoaDon> findByIdHD(String id) {
        return rp.getByIdDHList(id);
    }

    @Override
    public Page<ChiTietHoaDon> getPagination(Pageable pageable, String id) {
        return rp.getPaginationByIdDHList(id, pageable);
    }

    @Override
    public ChiTietHoaDon getById2(String id) {
        if (rp.findById(Integer.valueOf(id)).isPresent()) {
            return rp.findById(Integer.valueOf(id)).get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        rp.deleteById(Integer.parseInt(id));
    }
}
