package com.example.manstore.controller.admin;

import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.HoaDonChiTietService;
import com.example.manstore.service.HoaDonService;
import com.example.manstore.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/invoice")
public class RestControllerHoaDon {
    @Autowired
    HoaDonServiceImpl serviceInvoice;

    @Autowired
    HoaDonChiTietServiceImpl serviceDetailInvoce;

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @Autowired
    private ChiTietSanPhamImpl spService;

    @Autowired
    private HoaDonService donHangService;

    @Autowired
    private HoaDonChiTietService donHangCTService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @Autowired
    private DotGiamGiaServiceImpl dotGiamGiaService;

    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;

    @GetMapping("/index/{pageNumber}")
    private ResponseEntity<?> index(@PathVariable("pageNumber") int pageNumber,
                                    @RequestParam(value = "status", required = false) String status,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("ngayTao").descending());
        Page<HoaDon> page = serviceInvoice.page(pageable);
        if (status != null && keyword == null && startDate == null & endDate == null) {
            page = serviceInvoice.filterByStatus(pageable, Integer.parseInt(status));
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword != null && startDate == null & endDate == null) {
            page = serviceInvoice.search(pageable, keyword);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword != null && startDate == null & endDate == null) {
            page = serviceInvoice.searchAndFilter(pageable, keyword, Integer.parseInt(status));
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword == null && startDate != null & endDate != null) {
            page = serviceInvoice.filterByDate(pageable, startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword == null && startDate != null & endDate != null) {
            page = serviceInvoice.filterByAll(pageable, Integer.parseInt(status), startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword != null && startDate != null & endDate != null) {
            page = serviceInvoice.searchAndFilterByDate(pageable, keyword, startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword != null && startDate != null & endDate != null) {
            page = serviceInvoice.searchAndFilterByAll(pageable, keyword, Integer.parseInt(status), startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
