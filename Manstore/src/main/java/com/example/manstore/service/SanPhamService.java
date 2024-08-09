package com.example.manstore.service;

import com.example.manstore.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {

    List<SanPham> getAllSanPham();

//    Integer getTotalQuantityForProduct(Integer idSanPham);
    Optional<SanPham> getSanPhamById(Integer id);
    Boolean save(SanPham sanPham);
    Boolean update(SanPham sanPham);
    Boolean delete(Integer id);
    Page<SanPham> pageOfSP(Pageable pageable);
    Page<SanPham> SearchSPByNameOrCode(String keyword, Pageable pageable);

    Integer priceMax();
    Page<SanPham> pageClient(Pageable pageable);

    Page<SanPham> filterColor(Pageable pageable, List<Integer> color);

    Page<SanPham> filterSize(Pageable pageable, List<String> size);

    Page<SanPham> filterTrademark(Pageable pageable, String trademark);

    Page<SanPham> filterCatergory(Pageable pageable, String category);

    Page<SanPham> filterColorAndSize(Pageable pageable, List<Integer> color, List<String> size);

    Page<SanPham> price(Pageable pageable, String priceStart, String priceEnd);

    //brand+color
    Page<SanPham> filterTrademarkAndColor(Pageable pageable, String trademark, List<Integer> color);

    Page<SanPham> filterColorAndCategory(Pageable pageable, String category, List<Integer> color);

    Page<SanPham> filterSizeAndTrademark(Pageable pageable, String trademark, List<String> size);

    Page<SanPham> filterSizeAndCategory(Pageable pageable, String category, List<String> size);

    Page<SanPham> filterTrademarkAndCategory(Pageable pageable, String category, String trademark);

    Page<SanPham> filterTrademarkAndPrice(Pageable pageable, String trademark, String start, String end);

    Page<SanPham> priceAndFilterColor(Pageable pageable, String priceStart, String priceEnd, List<Integer> color);

    Page<SanPham> priceAndFilterSize(Pageable pageable, String priceStart, String priceEnd, List<String> size);

    Page<SanPham> filterPriceAndCategory(Pageable pageable, String category, String start, String end);

    Page<SanPham> filterColorAndSizeAndCategory(Pageable pageable, String category, List<Integer> color, List<String> size);

    Page<SanPham> priceAndFilterColorAndSize(Pageable pageable, String priceStart, String priceEnd, List<Integer> color, List<String> size);

    Page<SanPham> filterColorAndPriceAndCategory(Pageable pageable, String category, String start, String end, List<Integer> color);

    Page<SanPham> filterSizeAndPriceAndTrademark(Pageable pageable, String trademark, String start, String end, List<String> size);

    Page<SanPham> filterSizeAndCategoryAndPrice(Pageable pageable, String category, String start, String end, List<String> size);

    Page<SanPham> filterTrademarkAndCategoryAndPrice(Pageable pageable, String category, String trademark, String start, String end);

    Page<SanPham> filterColorAndSizeAndTrademarkAndPrice(Pageable pageable, String trademark, String start, String end, List<Integer> color, List<String> size);

    Page<SanPham> filterColorAndSizeAndCategoryAndPrice(Pageable pageable, String category, String start, String end, List<Integer> color, List<String> size);

    Page<SanPham> filterColorAndPriceAndCategoryAndTrademark(Pageable pageable, String category, String trademark, String start, String end, List<Integer> color);

    Page<SanPham> filterSizeAndPriceAndCategoryAndTrademark(Pageable pageable, String sport, String brand, String start, String end, List<String> size);

    Page<SanPham> filterAll1(Pageable pageable, String category, String trademark, String start, String end, List<Integer> color, List<String> size);

    Page<SanPham> filterColorAndSizeAndTrademark(Pageable pageable, String trademark, List<Integer> color, List<String> size);

    Page<SanPham> filterColorAndTrademarkAndCategory(Pageable pageable, String category, String trademark, List<Integer> color);

    Page<SanPham> filterSizeAndCategoryAndTrademark(Pageable pageable, String trademark, String category, List<String> size);

    Page<SanPham> filterColorAndSizeAndTrademarkAndCategory(Pageable pageable, String category, String trademark, List<Integer> color, List<String> size);









}
