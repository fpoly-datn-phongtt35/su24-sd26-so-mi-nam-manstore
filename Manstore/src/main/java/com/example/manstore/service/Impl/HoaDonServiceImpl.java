package com.example.manstore.service.Impl;

import com.example.manstore.entity.ChiTietHoaDon;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.HoaDonChiTietRepository;
import com.example.manstore.repository.HoaDonRepository;
import com.example.manstore.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository donHangCTRepositoty;


    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepository.findById(id);
    }

    @Override
    public HoaDon findByHD(String hd) {
        if (hoaDonRepository.findByHD(hd).isPresent()) {
            return hoaDonRepository.findByHD(hd).get();
        } else {
            return null;
        }
    }

    @Override
    public void save(HoaDon hd) {
        hoaDonRepository.save(hd);
    }

    @Override
    public Page<HoaDon> page(Pageable pageable) {
        return hoaDonRepository.findAllBut0(pageable);
    }

    @Override
    public List<HoaDon> getByPromotion(String idPromotion) {
        return hoaDonRepository.findByPromotion(idPromotion);
    }

    @Override
    public Page<HoaDon> filterByStatus(Pageable pageable, int status) {
        return hoaDonRepository.filterByStatus(status, pageable);
    }

    @Override
    public Page<HoaDon> filterByDate(Pageable pageable, String startDate, String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startDate + "T00:00:00", dateFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endDate + "T23:59:59", dateFormatter);
        return hoaDonRepository.filterByDate(dateTimeStart, dateTimeEnd, pageable);
    }

    @Override
    public Page<HoaDon> filterByAll(Pageable pageable, int status, String startDate, String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startDate + "T00:00:00", dateFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endDate + "T23:59:59", dateFormatter);
        return hoaDonRepository.filterByDateAndStatus(dateTimeStart, dateTimeEnd, status, pageable);
    }

    @Override
    public Page<HoaDon> searchAndFilterByAll(Pageable pageable, String keyword, int status, String startDate, String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startDate + "T00:00:00", dateFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endDate + "T23:59:59", dateFormatter);
        return hoaDonRepository.searchAndFilterByAll(status, dateTimeStart, dateTimeEnd, keyword, pageable);
    }

    @Override
    public Page<HoaDon> search(Pageable pageable, String keyword) {
        return hoaDonRepository.searchByName(keyword, pageable);
    }

    @Override
    public Page<HoaDon> searchAndFilter(Pageable pageable, String keyword, int status) {
        return hoaDonRepository.searchAndFilter(status, keyword, pageable);
    }

    @Override
    public Page<HoaDon> searchAndFilterByDate(Pageable pageable, String keyword, String startDate, String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startDate + "T00:00:00", dateFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endDate + "T23:59:59", dateFormatter);
        return hoaDonRepository.searchAndFilterByDate(keyword, dateTimeStart, dateTimeEnd, pageable);
    }

    @Override
    public Page<HoaDon> findByIdKHAndStatus(Pageable pageable, String idkh, String status) {
        return hoaDonRepository.findByIdKHAndStatus(pageable, idkh,status);
    }

    @Override
    public BigDecimal calculateTotal(String id) {
        BigDecimal total = BigDecimal.ZERO;
        for (ChiTietHoaDon dhct : donHangCTRepositoty.getByIdDHList(id)) {
            total = total.add(dhct.getTongTien());
        }
        return total;
    }
}
