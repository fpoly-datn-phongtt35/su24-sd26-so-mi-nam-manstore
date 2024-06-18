package com.example.manstore.controller;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.DotGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class DotGiamGiaController {
    @Autowired
    DotGiamGiaService dotGiamGiaService;
    @Autowired
    DotGiamGiaRepository dotGiamGiaRepository;

    @RequestMapping("/admin/promotion")
    public String promotion() {
        return "admin/promotion/list-promotion";
    }


    @GetMapping("/admin/promotion/findAll-page")
    public ResponseEntity<?> findAll(@RequestParam(value = "start", required = false) LocalDate start,
                                     @RequestParam(value = "end", required = false) LocalDate end,
                                     @RequestParam(value = "promotion_type", required = false) String promotionType,
                                     Pageable pageable) {
        Page<DotGiamGia> result = dotGiamGiaService.findAll(start, end, promotionType, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
