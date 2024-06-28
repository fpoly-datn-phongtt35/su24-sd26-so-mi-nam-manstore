package com.example.manstore.controller;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.DotGiamGiaService;
import com.example.manstore.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class DotGiamGiaController {
    @Autowired
    DotGiamGiaService dotGiamGiaService;

    @Autowired
    DotGiamGiaRepository dotGiamGiaRepository;

    @Autowired
    HoaDonService hoaDonService;


    @RequestMapping("/admin/promotion")
    public String promotion() {
        return "admin/promotion/list-promotion";
    }

    @RequestMapping("/admin/add-promotion")
    public String addPromotion(Model model, @RequestParam(value = "id", required = false) Integer id) {
            model.addAttribute("promotion", new DotGiamGia());
        return "admin/promotion/add-promotion";
    }

    @RequestMapping(value = "/admin/add-promotion", method = RequestMethod.POST)
    public String addPromotionAction(@Valid @ModelAttribute("promotion") DotGiamGia promotion, Model model) {

        boolean isValid = false;
        if (promotion.getMa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorCode", "Mã đợt giảm giá chưa được nhập!");
        }
        if (promotion.getMa().matches("[a-zA-Z]+")) {
            isValid = true;
            model.addAttribute("errorCode", "Mã đợt giảm giá phải có kí tự chữ!");
        }
        if (promotion.getMa().length() > 50) {
            isValid = true;
            model.addAttribute("errorCode", "Mã đợt giảm giá quá dài!");
        }

        if (promotion.getTen().isEmpty()) {
            isValid = true;
            model.addAttribute("errorName", "Tên đợt giảm giá chưa được nhập!");
        }
        if (promotion.getTen().matches("[a-zA-Z]+")) {
            isValid = true;
            model.addAttribute("errorName", "Tên đợt giảm giá phải có kí tự chữ!");
        }
        if (promotion.getTen().length() > 50) {
            isValid = true;
            model.addAttribute("errorName", "Tên đợt giảm giá quá dài!");
        }

        if (promotion.getNgayBatDau() == null) {
            isValid = true;
            model.addAttribute("errorNgayBatDau", "Ngày bắt đầu chưa được chọn!");
        }
        if (promotion.getNgayKetThuc() == null) {
            isValid = true;
            model.addAttribute("errorNgayKetThuc", "Ngày bắt đầu chưa được chọn!");
            model.addAttribute("startDate", promotion.getNgayBatDau());
        }
        if (promotion.getNgayBatDau().isBefore(LocalDate.now())) {
            isValid = true;
            model.addAttribute("errorNgayBatDau", "Ngày bắt đầu không được trước ngày hiện tại!");
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
        }
        if (promotion.getNgayKetThuc().compareTo(promotion.getNgayBatDau()) == 0 || promotion.getNgayKetThuc().isBefore(promotion.getNgayBatDau())) {
            isValid = true;
            model.addAttribute("errorNgayKetThuc", "Ngày kết thúc phải sau ngày bắt đầu!");
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
        }
        if (promotion.getId() != null) {
            if (dotGiamGiaRepository.findByNameAndId(promotion.getTen(), promotion.getId()).isPresent()) {
                isValid = true;
                model.addAttribute("errorName", "Tên đợt giảm giá này đã tồn tại!");
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
            }
        } else {
            if (dotGiamGiaRepository.findByName(promotion.getTen()).isPresent()) {
                isValid = true;
                model.addAttribute("errorName", "Tên đợt giảm giá này đã tồn tại");
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
            }
        }
        if (promotion.getGiaTriGiam() <= 0) {
            isValid = true;
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
            model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải lớn hơn 0!");
        }
        if (promotion.getGiaTriGiam() >= 50 && promotion.getLoaiGiamGia()) {
            isValid = true;
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
            model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải nhỏ hơn 50%!");
        }
        if (!promotion.getLoaiGiamGia()) {
            if (promotion.getGiaTriGiam() <= 5000) {
                isValid = true;
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
                model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải tối thiểu 5.000 vnđ!");
            }
            double valueCompare = Double.parseDouble(promotion.getGiaTriDonHang() + "000");
            if (promotion.getGiaTriGiam() > (valueCompare * 0.1)) {
                isValid = true;
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
                model.addAttribute("errorGiaTriGiam", "Giảm giá tiền không quá 50% giá trị đơn hàng áp dụng!");
            }
        }

        if (isValid) {
            return "admin/promotion/add-promotion";
        }
        promotion.setNgayTao(LocalDate.now());
        promotion.setTrangThai(false);
        dotGiamGiaService.create(promotion);
        model.addAttribute("promotionCreated", true);
        return "admin/promotion/add-promotion";
    }

    @PostMapping("/admin/promotion/update/{id}")
    public String update(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes
            , @ModelAttribute("dotgiamgia") DotGiamGia dgg) {
        DotGiamGia dotGiamGia = dotGiamGiaService.findById(id).get();
        dotGiamGia.setMa(dgg.getMa());
        dotGiamGia.setTen(dgg.getTen());
        dotGiamGia.setGiaTriGiam(dgg.getGiaTriGiam());
        dotGiamGia.setLoaiGiamGia(dgg.getLoaiGiamGia());
        dotGiamGia.setGiaTriDonHang(dgg.getGiaTriDonHang());
        dotGiamGia.setNgayBatDau(dgg.getNgayBatDau());
        dotGiamGia.setNgayKetThuc(dgg.getNgayKetThuc());
        dotGiamGia.setNgayTao(dgg.getNgayTao());
        dotGiamGia.setTrangThai(dgg.getTrangThai());
        dotGiamGiaService.create(dgg);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/admin/promotion";
    }

    @PostMapping("/admin/promotion/validation")
    public ResponseEntity<?> validation(@ModelAttribute("promotion") DotGiamGia promotion) {
        if (promotion.getId() == null) {
            List<DotGiamGia> listAll = dotGiamGiaService.getAll();
            for (DotGiamGia dgg : listAll
            ) {
                if (!promotion.getNgayBatDau().isBefore(dgg.getNgayBatDau()) && !promotion.getNgayBatDau().isAfter(dgg.getNgayKetThuc())) {
                    return new ResponseEntity<>("failure", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(promotion.getId()).get();
            List<DotGiamGia> listAll = dotGiamGiaService.getAll();
            listAll.remove(dotGiamGia);
            for (DotGiamGia dgg : listAll
            ) {
                if (!promotion.getNgayBatDau().isBefore(dgg.getNgayBatDau()) && !promotion.getNgayBatDau().isAfter(dgg.getNgayKetThuc())) {
                    return new ResponseEntity<>("failure", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }
}
