package com.example.manstore.service;

import com.example.manstore.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HoaDonService {

    List<HoaDon> getAll();

    Optional<HoaDon> findById(Integer id);

    HoaDon findByHD(String hd);

    void save(HoaDon hd);

    Page<HoaDon> page(Pageable pageable);

    List<HoaDon> getByPromotion(String idPromotion);

    Page<HoaDon> filterByStatus(Pageable pageable,int status);

    Page<HoaDon> filterByDate(Pageable pageable,String startDate, String endDate);

    Page<HoaDon> filterByAll(Pageable pageable,int status,String startDate, String endDate);

    Page<HoaDon> searchAndFilterByAll(Pageable pageable,String keyword, int status,
                                       String startDate, String endDate);

    Page<HoaDon> search(Pageable pageable,String keyword);

    Page<HoaDon> searchAndFilter(Pageable pageable,String keyword, int status);

    Page<HoaDon> searchAndFilterByDate(Pageable pageable,String keyword, String startDate, String endDate);

    Page<HoaDon> findByIdKHAndStatus(Pageable pageable,String idkh,String status);

    BigDecimal calculateTotal(String id);

}
