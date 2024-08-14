package com.example.manstore.service.Impl;

import com.example.manstore.entity.GioHangChiTiet;
import com.example.manstore.repository.GioHangChiTietRepository;
import com.example.manstore.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository rp;

    @Override
    public Page<GioHangChiTiet> pagination(Pageable pageable) {
        return rp.findAll(pageable);
    }

    @Override
    public Page<GioHangChiTiet> getByIdGH(String id, Pageable pageable) {
        return rp.getByIdGH(id, pageable);
    }

    @Override
    public void save(GioHangChiTiet gioHangChiTiet) {
        rp.save(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet getById(String id) {
        if(rp.findById(Integer.parseInt(id)).isPresent()){
            return rp.findById(Integer.parseInt(id)).get();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        rp.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<GioHangChiTiet> getByIdGHList(String id) {
        return rp.getByIdGHList(id);
    }

    @Override
    public void deleteAll() {
        rp.deleteAll();
    }

}
