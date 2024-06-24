package com.example.manstore.controller;

import com.example.manstore.dto.request.HoaDon_PhieuGiamRequest;
import com.example.manstore.service.HoaDon_PhieuGiamService;
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
public class HoaDon_PhieuGiamController {

    @Autowired
    HoaDon_PhieuGiamService hoaDon_phieuGiamService;

    @GetMapping("/manager/hoaDonPhieuGiamGia/view")
    public ResponseEntity<List<HoaDon_PhieuGiamRequest>> getAllPhieuGiam(@RequestParam Optional<Integer> page) {
        try {
            List<HoaDon_PhieuGiamRequest> list =  hoaDon_phieuGiamService.getAllPhieuGiam(page.orElse(0));
            return new ResponseEntity<List<HoaDon_PhieuGiamRequest>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<HoaDon_PhieuGiamRequest>>(HttpStatus.NOT_FOUND);
        }
    }
}
