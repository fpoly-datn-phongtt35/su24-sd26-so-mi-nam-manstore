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
        if (promotion.getTen().isEmpty()) {
            isValid = true;
            model.addAttribute("errorName", "Tên đợt giảm giá chưa được nhập!");
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
}
