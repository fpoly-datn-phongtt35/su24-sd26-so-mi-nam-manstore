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

//    @Override
//    public Integer getTotalQuantityForProduct(Integer idSanPham) {
//        return sanPhamRepository.findTotalQuantityBySanPhamId(idSanPham);
//    }

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

    @Override
    public Integer priceMax() {
        return sanPhamRepository.priceMax();
    }

    @Override
    public Page<SanPham> pageClient(Pageable pageable) {
        return sanPhamRepository.pageClient(pageable);
    }

    @Override
    public Page<SanPham> filterColor(Pageable pageable, List<Integer> color) {
        return sanPhamRepository.filterColor(pageable, color);
    }

    @Override
    public Page<SanPham> filterSize(Pageable pageable, List<String> size) {
        return sanPhamRepository.filterSize(pageable, size);
    }

    @Override
    public Page<SanPham> filterTrademark(Pageable pageable, String trademark) {
        return sanPhamRepository.filterTrademark(trademark, pageable);
    }

    @Override
    public Page<SanPham> filterCatergory(Pageable pageable, String category) {
        return sanPhamRepository.filterCatergory(category, pageable);
    }

    @Override
    public Page<SanPham> price(Pageable pageable, String priceStart, String priceEnd) {
        return sanPhamRepository.price(pageable, priceStart, priceEnd);
    }

    @Override
    public Page<SanPham> filterColorAndSize(Pageable pageable, List<Integer> color, List<String> size) {
        return sanPhamRepository.filterColorAndSize(pageable, color, size);
    }

    @Override
    public Page<SanPham> filterTrademarkAndColor(Pageable pageable, String trademark, List<Integer> color) {
        return sanPhamRepository.filterColorAndTrademark(pageable, color, trademark);
    }

    @Override
    public Page<SanPham> filterColorAndCategory(Pageable pageable, String category, List<Integer> color) {
        return sanPhamRepository.filterColorAndCategory(pageable, color, category);
    }

    @Override
    public Page<SanPham> filterSizeAndTrademark(Pageable pageable, String trademark, List<String> size) {
        return sanPhamRepository.filterSizeAndTrademark(pageable, size, trademark);
    }

    @Override
    public Page<SanPham> filterSizeAndCategory(Pageable pageable, String category, List<String> size) {
        return sanPhamRepository.filterSizeAndCategory(pageable, size, category);
    }

    @Override
    public Page<SanPham> filterTrademarkAndCategory(Pageable pageable, String category, String trademark) {
        return sanPhamRepository.filterTrademarkAndCategory(pageable, category, trademark);
    }

    @Override
    public Page<SanPham> filterTrademarkAndPrice(Pageable pageable, String trademark, String start, String end) {
        return sanPhamRepository.priceAndFilterTrademark(pageable, trademark, start, end);
    }

    @Override
    public Page<SanPham> priceAndFilterColor(Pageable pageable, String priceStart, String priceEnd, List<Integer> color) {
        return sanPhamRepository.priceAndFilterColor(pageable, priceStart, priceEnd, color);
    }

    @Override
    public Page<SanPham> priceAndFilterSize(Pageable pageable, String priceStart, String priceEnd, List<String> size) {
        return sanPhamRepository.priceAndFilterSize(pageable, priceStart, priceEnd, size);
    }

    @Override
    public Page<SanPham> filterPriceAndCategory(Pageable pageable, String category, String start, String end) {
        return sanPhamRepository.filterPriceAndCategory(pageable, category, start, end);
    }

    @Override
    public Page<SanPham> filterColorAndSizeAndCategory(Pageable pageable, String category, List<Integer> color, List<String> size) {
        return sanPhamRepository.filterColorAndSizeAndCategory(pageable, color, size, category);
    }

    @Override
    public Page<SanPham> priceAndFilterColorAndSize(Pageable pageable, String priceStart, String priceEnd, List<Integer> color, List<String> size) {
        return sanPhamRepository.priceAndFilterColorAndSize(pageable, priceStart, priceEnd, color, size);
    }

    @Override
    public Page<SanPham> filterColorAndPriceAndCategory(Pageable pageable, String category, String start, String end, List<Integer> color) {
        return sanPhamRepository.priceAndFilterColorAndCategory(pageable, category, start, end, color);
    }

    @Override
    public Page<SanPham> filterSizeAndPriceAndTrademark(Pageable pageable, String trademark, String start, String end, List<String> size) {
        return sanPhamRepository.priceAndFilterSizeAndTrademark(pageable, trademark,start, end, size);
    }

    @Override
    public Page<SanPham> filterSizeAndCategoryAndPrice(Pageable pageable, String category, String start, String end, List<String> size) {
        return sanPhamRepository.priceAndFilterSizeAndCategory(pageable, category, start, end, size);
    }

    @Override
    public Page<SanPham> filterTrademarkAndCategoryAndPrice(Pageable pageable, String category, String trademark, String start, String end) {
        return sanPhamRepository.priceAndFilterTrademarkAndCategory(pageable, category, trademark, start, end);
    }

    @Override
    public Page<SanPham> filterColorAndSizeAndTrademarkAndPrice(Pageable pageable, String trademark, String start, String end, List<Integer> color, List<String> size) {
        return sanPhamRepository.priceAndFilterTrademarkAndSizeAndColor(pageable, start, end, color, size, trademark);
    }

    @Override
    public Page<SanPham> filterColorAndSizeAndCategoryAndPrice(Pageable pageable, String category, String start, String end, List<Integer> color, List<String> size) {
        return sanPhamRepository.priceAndFilterCategoryAndSizeAndColor(pageable, start, end, color, size, category);
    }

    @Override
    public Page<SanPham> filterColorAndPriceAndCategoryAndTrademark(Pageable pageable, String category, String trademark, String start, String end, List<Integer> color) {
        return sanPhamRepository.priceAndFilterCategoryAndTrademarkAndColor(pageable, start, end, color, category, trademark);
    }

    @Override
    public Page<SanPham> filterSizeAndPriceAndCategoryAndTrademark(Pageable pageable, String sport, String brand, String start, String end, List<String> size) {
        return sanPhamRepository.priceAndFilterCategoryAndTrademarkAndSize(pageable, start, end, size, sport, brand);
    }

    @Override
    public Page<SanPham> filterAll1(Pageable pageable, String category, String trademark, String start, String end, List<Integer> color, List<String> size) {
        return sanPhamRepository.filterAll1(pageable, start, end, size, color, category, trademark);
    }

    @Override
    public Page<SanPham> filterColorAndSizeAndTrademark(Pageable pageable, String trademark, List<Integer> color, List<String> size) {
        return sanPhamRepository.filterColorAndSizeAndTrademark(pageable, color, size, trademark);
    }

    @Override
    public Page<SanPham> filterColorAndTrademarkAndCategory(Pageable pageable, String category, String trademark, List<Integer> color) {
        return sanPhamRepository.filterColorAndTrademarkAndCategory(pageable, color, category, trademark);
    }

    @Override
    public Page<SanPham> filterSizeAndCategoryAndTrademark(Pageable pageable, String trademark, String category, List<String> size) {
        return sanPhamRepository.filterSizeAndCategoryAndTrademark(pageable, size, category, trademark);
    }

    @Override
    public Page<SanPham> filterColorAndSizeAndTrademarkAndCategory(Pageable pageable, String category, String trademark, List<Integer> color, List<String> size) {
        return sanPhamRepository.filterColorAndTrademarkAndCategorytAndSize(pageable, color, size, category, trademark);
    }
}
