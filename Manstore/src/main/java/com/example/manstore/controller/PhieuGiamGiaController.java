package com.example.manstore.controller;

import com.example.manstore.dto.request.PhieuGiamGiaRequest;
import com.example.manstore.entity.PhieuGiamGia;
import com.example.manstore.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class PhieuGiamGiaController {

    @Autowired
    PhieuGiamGiaService phieuGiamGiaService;

    @GetMapping("/manager/phieuGiamGia/view")
    public ResponseEntity<List<PhieuGiamGiaRequest>> getAllPhieuGiam(@RequestParam Optional<Integer> page) {
        try {
            List<PhieuGiamGiaRequest> list =  phieuGiamGiaService.getAllPhieuGiamGia(page.orElse(0));
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/manager/phieuGiamGia/create")
    public ResponseEntity<PhieuGiamGiaRequest> createProduct(@RequestBody PhieuGiamGia phieuGiamGia) {
        try {
            PhieuGiamGiaRequest products2 = phieuGiamGiaService.createPhieuGiamGia(phieuGiamGia);
            return new ResponseEntity<PhieuGiamGiaRequest>(products2, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<PhieuGiamGiaRequest>(HttpStatus.NOT_FOUND);
        }
    }

    //	update product
    @PutMapping("/manager/phieuGiamGia/update")
    public ResponseEntity<PhieuGiamGiaRequest> updateProduct(@RequestBody PhieuGiamGia phieuGiamGia, @RequestParam Integer id) {
        try {
            PhieuGiamGiaRequest products2 = phieuGiamGiaService.updatePhieuGiamGia(phieuGiamGia, id);
            return new ResponseEntity<PhieuGiamGiaRequest>(products2, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<PhieuGiamGiaRequest>(HttpStatus.NOT_FOUND);
        }
    }

    //	find By Type
    @GetMapping("/manager/phieuGiamGia/type")
    public ResponseEntity<List<PhieuGiamGiaRequest>> getProductsByType(@RequestParam String loaiGiamGia) {
        try {
            List<PhieuGiamGiaRequest> list = phieuGiamGiaService.getPhieuGiamByLoaiGiamGia(loaiGiamGia);
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(HttpStatus.NOT_FOUND);
        }
    }

    //	Detail
    @GetMapping("/manager/phieuGiamGia/detail")
    public ResponseEntity<PhieuGiamGiaRequest> getProductsById(@RequestParam Integer id) {
        try {
            PhieuGiamGiaRequest products2 = phieuGiamGiaService.getById(id);
            return new ResponseEntity<PhieuGiamGiaRequest>(products2, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<PhieuGiamGiaRequest>(HttpStatus.NOT_FOUND);
        }
    }

    //	search By name
    @GetMapping("/manager/phieuGiamGia/search")
    public ResponseEntity<List<PhieuGiamGiaRequest>> searchByName(@RequestParam String tieuDe) {
        try {
            List<PhieuGiamGiaRequest> list = phieuGiamGiaService.searchByName(tieuDe);
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<PhieuGiamGiaRequest>>(HttpStatus.NOT_FOUND);
        }
    }
}
