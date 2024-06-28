package com.example.manstore.controller;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.DotGiamGiaService;
import com.example.manstore.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class DotGiamGiaRestController {

    @Autowired
    DotGiamGiaService dotGiamGiaService;

    @Autowired
    DotGiamGiaRepository dotGiamGiaRepository;

    @Autowired
    HoaDonService hoaDonService;

    @GetMapping("/admin/promotion/findAll-page")
    public ResponseEntity<?> findAll(@RequestParam(value = "start", required = false) LocalDate start,
                                     @RequestParam(value = "end", required = false) LocalDate end,
                                     @RequestParam(value = "promotion_type", required = false) String promotionType,
                                     Pageable pageable) {
        Page<DotGiamGia> result = dotGiamGiaService.findAll(start, end, promotionType, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/promotion/find-by-id")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
        Optional<DotGiamGia> dGG = dotGiamGiaRepository.findById(id);
        return new ResponseEntity<>(dGG.orElse(null), HttpStatus.OK);
    }

    @GetMapping("/admin/promotion/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(dotGiamGiaService.findById(id));
    }

    @RequestMapping(value = "/admin/promotion/check-status", method = RequestMethod.GET)
    public ResponseEntity<?> checkPromotion(@RequestParam(value = "id", required = false) Integer id) {
        if (hoaDonService.getByPromotion(String.valueOf(id)).size() == 0) {
            Optional<DotGiamGia> dgg = dotGiamGiaService.findById(id);
            if (dgg.isEmpty()) {
                System.out.println("promotion is null");
                return new ResponseEntity<>("not exist", HttpStatus.OK);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("error", HttpStatus.OK);
        }
    }

    @GetMapping("/admin/promotion/change-status/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @PathVariable("status") int status) {
        List<HoaDon> count = hoaDonService.getByPromotion(String.valueOf(id));
        DotGiamGia dgg = dotGiamGiaRepository.findById(id).isPresent() ? dotGiamGiaRepository.findById(id).get() : null;
        if (dgg != null) {
            if (dgg.getNgayKetThuc().isBefore(LocalDate.now()) && status == 1) {
                return new ResponseEntity<>("out of date", HttpStatus.OK);
            }
            if (count.size() > 0) {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            } else {
                dgg.setTrangThai(status == 0);
                dotGiamGiaService.create(dgg);
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("promotion not exists", HttpStatus.OK);
    }


}
