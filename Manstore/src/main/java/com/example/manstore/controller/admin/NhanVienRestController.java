package com.example.manstore.controller.admin;

import com.example.manstore.entity.NhanVien;
import com.example.manstore.service.NhanVienService;
import com.example.manstore.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/staff")
public class NhanVienRestController {

    @Autowired
    NhanVienService nhanVienService;

    @Autowired
    PhanQuyenService phanQuyenService;

    @GetMapping("/page/{number}/{keyword}/{status}/{sort}")
    public ResponseEntity<?> getPageAndSearchAndFilter(@PathVariable("number") int number
            , @PathVariable("keyword") String keyword
            , @PathVariable("status") int status
            , @PathVariable("sort") int sort
    ) {

        Pageable pageable = null;
        Page<NhanVien> page = null;
        if (sort == 1) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.asc("ngaySinh"),
                    Sort.Order.desc("id")
            );
            pageable = PageRequest.of(number, 5, sortCriteria);
        } else if (sort == 2) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.desc("ngaySinh"),
                    Sort.Order.desc("id")
            );
            pageable = PageRequest.of(number, 5, sortCriteria);
        } else if (sort == 3) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.asc("id")
            );
            pageable = PageRequest.of(number, 5, sortCriteria);
        }else {
            pageable = PageRequest.of(number, 5, Sort.by("id").descending());
        }
        if (status == -1  && keyword.equals("null")) {
            page = nhanVienService.page(pageable);
        }
        else if (status == -1  && !keyword.equals("null")) {
            page = nhanVienService.SearchPage(pageable, keyword);
        }
        else if (status != -1  && keyword.equalsIgnoreCase("null")) {
            page = nhanVienService.filterByStatusNoSearch(pageable, status);
        }
        else if (status != -1  && !keyword.equalsIgnoreCase("null")) {
            page = nhanVienService.filterByStatusAndSearch(pageable, keyword, status);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/update_status/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") String id
            , @PathVariable("status") String status) {
        try {
            int nhanVienId = Integer.parseInt(id);
            int trangThai = Integer.parseInt(status);

            Optional<NhanVien> optionalNv = nhanVienService.findById(nhanVienId);
            if (optionalNv.isPresent()) {
                NhanVien nv = optionalNv.get();
                nv.setTrangThai(trangThai);
                nhanVienService.save(nv);
                return ResponseEntity.status(HttpStatus.OK).body("success");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("fail");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id or status");
        }
    }
}
