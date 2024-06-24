package com.example.manstore.controller;

import com.example.manstore.dto.request.KhachHang_PhieuGiamRequest;
import com.example.manstore.service.KhachHang_PhieuGiamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class KhachHang_PhieuGiamContrller {
    @Autowired
    KhachHang_PhieuGiamService khachHang_phieuGiamService;

    @GetMapping("/manager/khachHangPhieuGiamGia/view")
    public ResponseEntity<List<KhachHang_PhieuGiamRequest>> getAllPhieuGiam(@RequestParam Optional<Integer> page) {
        try {
            List<KhachHang_PhieuGiamRequest> list =  khachHang_phieuGiamService.getAll(page.orElse(0));
            return new ResponseEntity<List<KhachHang_PhieuGiamRequest>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<KhachHang_PhieuGiamRequest>>(HttpStatus.NOT_FOUND);
        }
    }
}
