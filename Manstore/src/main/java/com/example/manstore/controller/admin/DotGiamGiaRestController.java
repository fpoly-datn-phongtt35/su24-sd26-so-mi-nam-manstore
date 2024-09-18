package com.example.manstore.controller.admin;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.repository.GioHangRepository;
import com.example.manstore.repository.HoaDonRepository;
import com.example.manstore.service.DotGiamGiaService;
import com.example.manstore.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        List<HoaDon> count = hoaDonService.getByPromotion(String.valueOf(id));
        Optional<DotGiamGia> dgg = dotGiamGiaRepository.findById(id);

        if (dgg.isPresent()) {
            DotGiamGia dotGiamGia = dgg.get();

            if (dotGiamGia.getNgayKetThuc().isBefore(LocalDate.now()) && status == 1) {
                return ResponseEntity.status(HttpStatus.OK).body("out of date");
            }

//            if (count.size() > 0) {
//                return ResponseEntity.status(HttpStatus.OK).body("failure");
//            } else {
            dotGiamGia.setTrangThai(status == 1);
            dotGiamGiaService.create(dotGiamGia);
            return ResponseEntity.status(HttpStatus.OK).body("success");
//            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("promotion not exists");
        }
    }


//@GetMapping("/public/promotion/find-by-date")
//public ResponseEntity<?> findByDate(@RequestParam("orderValue") double orderValue) {
//    LocalDate now = LocalDate.now();
//    List<DotGiamGia> promotions = dotGiamGiaRepository.getPromotionAll(now, true);
//
//    // Lọc khuyến mãi hết hạn hoặc không có trạng thái là true
//    promotions.removeIf(km -> km.getNgayKetThuc().isBefore(now) || !km.getTrangThai());
//
//    // Tìm khuyến mãi phù hợp với đơn hàng
//    DotGiamGia bestPromotion = null;
//    double maxDiscount = 0;
//
//    for (DotGiamGia promo : promotions) {
//        double discount = 0;
//
//        // Kiểm tra điều kiện áp dụng khuyến mãi
//        if (orderValue >= promo.getGiaTriDonHang()) {
//            if (promo.getLoaiGiamGia()) {
//                // Giảm theo phần trăm
//                discount = orderValue * (promo.getGiaTriGiam() / 100.0);
//            } else {
//                // Giảm theo số tiền cố định
//                discount = promo.getGiaTriGiam();
//            }
//
//            // Chọn khuyến mãi có mức giảm cao nhất
//            if (discount > maxDiscount) {
//                maxDiscount = discount;
//                bestPromotion = promo;
//            }
//        }
//    }
//
//    return new ResponseEntity<>(bestPromotion != null ? bestPromotion : new DotGiamGia(), HttpStatus.OK);
//}

@GetMapping("/public/promotion/find-by-date")
public ResponseEntity<?> findByDate(@RequestParam("orderValue") double orderValue) {
    LocalDate now = LocalDate.now();
    List<DotGiamGia> promotions = dotGiamGiaRepository.getPromotionAll(now, true);

    // Lọc khuyến mãi hết hạn hoặc không có trạng thái là true
    promotions.removeIf(km -> km.getNgayKetThuc().isBefore(now) || !km.getTrangThai());

    // Tìm khuyến mãi phù hợp với đơn hàng
    DotGiamGia bestPromotion = null;
    double maxDiscount = 0;

    for (DotGiamGia promo : promotions) {
        double discount = 0;

        // Kiểm tra điều kiện áp dụng khuyến mãi
        if (orderValue >= promo.getGiaTriDonHang()) {
            if (promo.getLoaiGiamGia()) {
                // Giảm theo phần trăm
                discount = orderValue * (promo.getGiaTriGiam() / 100.0);
            } else {
                // Giảm theo số tiền cố định
                discount = promo.getGiaTriGiam();
            }

            // Chọn khuyến mãi có mức giảm cao nhất
            if (discount > maxDiscount) {
                maxDiscount = discount;
                bestPromotion = promo;
            }
        }
    }

    // Trả về khuyến mãi tốt nhất hoặc null nếu không có
    return new ResponseEntity<>(bestPromotion != null ? bestPromotion : new DotGiamGia(), HttpStatus.OK);
//    return new ResponseEntity<>(bestPromotion, HttpStatus.OK);
}

}
