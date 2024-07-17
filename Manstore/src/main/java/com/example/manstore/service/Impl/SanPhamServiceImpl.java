package com.example.manstore.service.Impl;

import com.example.manstore.entity.SanPham;
import com.example.manstore.repository.SanPhamRepository;
import com.example.manstore.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    @Override
    public Optional<SanPham> getSanPhamById(Integer id) {
        return sanPhamRepository.findById(id)
                .map(sanPham -> {
                    // Nạp các thuộc tính liên quan
                    sanPham.getIdDanhMuc();
                    sanPham.getIdThuongHieu();
                    sanPham.getIdCoAo();
                    sanPham.getIdDuoiAo();
                    sanPham.getIdKieuDang();
                    sanPham.getIdChatLieu();
                    return sanPham;
                });
    }

    @Override
    public Boolean save(SanPham sanPham) {
        sanPhamRepository.saveAndFlush(sanPham);
        return true;
    }

    @Override
    public Boolean update(SanPham sanPham) {
        sanPhamRepository.saveAndFlush(sanPham);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        sanPhamRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<SanPham> pageOfSP(Pageable pageable) {
        return sanPhamRepository.findAll(pageable);
    }

    @Override
    public Page<SanPham> SearchSPByNameOrCode(String keyword, Pageable pageable) {
        return sanPhamRepository.searchSanPhamByNameOrCode(keyword, pageable);
    }
}
