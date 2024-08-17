package com.example.manstore.controller.admin;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.entity.GioHang;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.repository.GioHangRepository;
import com.example.manstore.repository.HoaDonRepository;
import com.example.manstore.service.DotGiamGiaService;
import com.example.manstore.service.HoaDonService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @GetMapping("/admin/promotion/findAll-page")
    public ResponseEntity<?> findAll(@RequestParam(value = "start", required = false) LocalDate start,
                                     @RequestParam(value = "end", required = false) LocalDate end,
                                     @RequestParam(value = "promotion_type", required = false) String promotionType,
                                     Pageable pageable) {
        Page<DotGiamGia> result = dotGiamGiaRepository.findAllDGG(start, end, promotionType, pageable);
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

    @GetMapping("/admin/promotion/change-status/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @PathVariable("status") int status) {
//        List<HoaDonService> count = hoaDonService.getByPromotion(String.valueOf(id));
        Optional<DotGiamGia> dgg = dotGiamGiaRepository.findById(id);

        if (dgg.isPresent()) {
            DotGiamGia dotGiamGia = dgg.get();

            if (dotGiamGia.getNgayKetThuc().isBefore(LocalDate.now()) && status == 1) {
                return ResponseEntity.status(HttpStatus.OK).body("out of date");
        }

//        if (count.size() > 0) {
//            return ResponseEntity.status(HttpStatus.OK).body("failure");
//            } else {
                dotGiamGia.setTrangThai(status == 1);
                dotGiamGiaService.create(dotGiamGia);
                return ResponseEntity.status(HttpStatus.OK).body("success");
//            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("promotion not exists");
        }
    }

    @GetMapping("/public/promotion/find-by-date")
    public ResponseEntity<?> findByDate(@RequestParam("type") String type) {
        LocalDate now = LocalDate.now();
        List<DotGiamGia> promotions = dotGiamGiaRepository.getPromotionAll(now, true);

        // Lọc khuyến mãi hết hạn
        promotions.removeIf(km -> km.getNgayKetThuc().isBefore(now));

        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/client/promotion/find-by-customer")
    public ResponseEntity<?> findByIdCustomer(@RequestParam("id") Integer id) {
        try {
            List<DotGiamGia> promotions = dotGiamGiaRepository.getByCustomer(id, LocalDate.now(), true);
            promotions.removeIf(promotion -> promotion.getNgayKetThuc().isBefore(LocalDate.now()));

            if (promotions.isEmpty()) {
                List<DotGiamGia> allPromotions = dotGiamGiaRepository.getPromotionAll(LocalDate.now(), true);
                return new ResponseEntity<>(allPromotions, HttpStatus.OK);
            }

            return new ResponseEntity<>(promotions, HttpStatus.OK);
        } catch (Exception e) {
            // Log lỗi và trả về mã lỗi phù hợp
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
