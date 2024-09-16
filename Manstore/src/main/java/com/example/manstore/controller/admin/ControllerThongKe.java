package com.example.manstore.controller.admin;

import com.example.manstore.dto.respone.SanPhamBanChayDTO;
import com.example.manstore.dto.respone.ThoiGianDTO;
import com.example.manstore.entity.SanPham;
import com.example.manstore.repository.HoaDonChiTietRepository;
import com.example.manstore.repository.HoaDonRepository;
import com.example.manstore.repository.SanPhamRepository;
import com.example.manstore.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/statistic")
public class ControllerThongKe {

    @Autowired
    private HoaDonRepository donHangRepository;

    @Autowired
    private HoaDonChiTietRepository donHangChiTietRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/sales-year")
    public List<Double> doanhThu(@RequestParam("year") Integer year) {
        List<Double> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            Double sum = donHangRepository.calDt(i, year);
            if (sum == null) {
                sum = 0D;
            }
            list.add(sum);
        }
        return list;
    }


    @PostMapping("/selling-products")
    public List<SanPhamBanChayDTO> sanPhamBanChay(@RequestParam(value = "type", required = false) String type, @RequestBody ThoiGianDTO thoiGianDto) {
        List<SanPham> list = new ArrayList<>();
        Date ngayBd = thoiGianDto.getFrom();
        Date ngayKt = thoiGianDto.getTo();

        if (type.equals("tuan")) {
            String startDateTime = ngayBd + " 00:00:00";
            String endDateTime = ngayKt + " 23:59:59";
            System.out.println(startDateTime + "" + endDateTime);
            list = sanPhamRepository.sanPhamBanChayByDate(startDateTime, endDateTime);
            for (SanPham sp : list
            ) {
                System.out.println("SP Bán Chay " + sp.getTen());
            }
        }
        if (type.equals("thang")) {
            ngayBd = Date.valueOf(thoiGianDto.getNam() + "-" + Integer.parseInt(thoiGianDto.getThang()) + "-01");
            Date lastDate = DateService.lastDate(thoiGianDto.getNam() + "-" + thoiGianDto.getThang() + "-01");
            String startDateTime = ngayBd + " 00:00:00";
            String endDateTime = lastDate + " 23:59:59";
            list = sanPhamRepository.sanPhamBanChayByDate(startDateTime, endDateTime);
        }
        if (type.equals("quy")) {
            Date firstDate = DateService.firstDateByQuy(thoiGianDto.getQuy(), thoiGianDto.getNam());
            Date lastDate = DateService.lastDateByQuy(thoiGianDto.getQuy(), thoiGianDto.getNam());
            String startDateTime = firstDate + " 00:00:00";
            String endDateTime = lastDate + " 23:59:59";
            list = sanPhamRepository.sanPhamBanChayByDate(startDateTime, endDateTime);
        }
        if (type.equals("nam")) {
            Date firstDate = Date.valueOf(thoiGianDto.getNam() + "-01-01");
            Date lastDate = DateService.lastDate(thoiGianDto.getNam() + "-12-31");
            String startDateTime = firstDate + " 00:00:00";
            String endDateTime = lastDate + " 23:59:59";
            list = sanPhamRepository.sanPhamBanChayByDate(startDateTime, endDateTime);
        }

        if (type.equals("default")) {
            ngayBd = Date.valueOf("2000-01-01");
            ngayKt = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
            System.out.println("Date : " + ngayBd.toString() + "," + ngayKt.toString());
            String startDateTime = ngayBd + " 00:00:00";
            String endDateTime = ngayKt + " 23:59:59";
            list = sanPhamRepository.sanPhamBanChayByDate(startDateTime, endDateTime);
        }
        List<SanPhamBanChayDTO> result = new ArrayList<>();
        int count = 0;
        for (SanPham s : list) {
            SanPhamBanChayDTO sp = new SanPhamBanChayDTO();
            sp.setId(s.getId());
            sp.setGia(s.getGia());
            sp.setMa(s.getMa());
            sp.setTen(s.getTen());
            sp.setIdDanhMuc(s.getIdDanhMuc());
            String startDateTime = null;
            String endDateTime = null;
            if (ngayBd == null || ngayKt == null) {
                startDateTime = "2000-01-01 00:00:00";
                endDateTime = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" +
                        LocalDate.now().getDayOfMonth() + " 23:59:59";
            } else {
                startDateTime = ngayBd + " 00:00:00";
                endDateTime = ngayKt + " 23:59:59";
            }
            Integer soluong = sanPhamRepository.soLuongBan(s.getId(), startDateTime, endDateTime);
            sp.setSoLuong(soluong);
            count += soluong;
            result.add(sp);
        }
        System.out.println("total count " + count);
        return result;
    }

    @GetMapping("/total-product")
    public ResponseEntity<?> tongSpBan() {
        return new ResponseEntity<>(donHangChiTietRepository.countSp(), HttpStatus.OK);
    }

    @GetMapping("/total-sales")
    public ResponseEntity<?> tongDoanhThu() {
        return new ResponseEntity<>(donHangRepository.tongDoanhThu(), HttpStatus.OK);
    }

    @GetMapping("/total-sales-day")
    public ResponseEntity<?> tongDoanhThuTrongNgay(@RequestParam(value = "from", required = false) Date date) {
        LocalDateTime firstTimeOfDay;
        LocalDateTime endTimeOfDay;
        firstTimeOfDay = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 00, 00);
        endTimeOfDay = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 23, 59);
        return new ResponseEntity<>(donHangRepository.tongDoanhThu(firstTimeOfDay, endTimeOfDay), HttpStatus.OK);
    }
}