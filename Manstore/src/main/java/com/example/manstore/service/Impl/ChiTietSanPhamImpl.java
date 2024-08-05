package com.example.manstore.service.Impl;

import com.example.manstore.entity.ChiTietSanPham;
import com.example.manstore.repository.ChiTietSanPhamRepository;
import com.example.manstore.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietSanPhamImpl implements ChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository ctspRepository;

    @Override
    public List<ChiTietSanPham> getAllCTSP() {
        return ctspRepository.findAll();
    }

    @Override
    public List<ChiTietSanPham> getAllCTSPById(Integer id) {
        return ctspRepository.getListSpctByIdSp(String.valueOf(id));
    }

    @Override
    public Page<ChiTietSanPham> pageOfCTSP(Pageable pageable, String id) {
        return ctspRepository.pageOfCTSP(pageable, id);
    }

    @Override
    public void save(ChiTietSanPham chiTietSanPham) {
        ctspRepository.save(chiTietSanPham);
    }

    @Override
    public void update(ChiTietSanPham chiTietSanPham) {
        ctspRepository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham getCTSPById(Integer id) {
        if (ctspRepository.findById(id).isPresent()) {
            return ctspRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<ChiTietSanPham> findListProductByColor(String id, String ms) {
        return ctspRepository.findListProductByColor(id,ms);
    }

    @Override
    public List<ChiTietSanPham> getListCTSPById(String id) {
        return ctspRepository.getListSpctByIdSp(id);
    }

    @Override
    public Page<ChiTietSanPham> Filter(int page, String color, String size, String id) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by("ngayTao").descending());
        Page<ChiTietSanPham> pagination;
        Integer sizeId = null;
        try {
            sizeId = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (color.equals("0") && size.equals("0")) {
            pagination = ctspRepository.getSpctByIdSp(id, pageable);
        } else if (!color.equals("0") && size.equals("0")) {
            pagination = ctspRepository.FilterByColorAndProduct(color, id, pageable);
        } else if (color.equals("0") && !size.equals("0")) {
            pagination = ctspRepository.FilterBySizeAndProduct(sizeId, id, pageable);
        } else {
            pagination = ctspRepository.FilterByAllAndProduct(color, sizeId, id, pageable);
        }
        return pagination;
    }

    @Override
    public ChiTietSanPham findIdProductByColorAndSize(String id, String ms, String size) {
        return ctspRepository.findIdProductByColorAndSize(id,ms,size);
    }

    @Override
    public List<ChiTietSanPham> search(String keyword) {
        return ctspRepository.search(keyword);
    }

}
