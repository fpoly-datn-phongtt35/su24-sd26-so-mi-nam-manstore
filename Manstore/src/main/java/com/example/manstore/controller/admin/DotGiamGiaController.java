package com.example.manstore.controller.admin;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.DotGiamGiaService;
import com.example.manstore.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

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
        } else if (promotion.getNgayBatDau().isBefore(LocalDate.now())) {
            isValid = true;
            model.addAttribute("errorNgayBatDau", "Ngày bắt đầu không được trước ngày hiện tại!");
        } else if (promotion.getNgayBatDau().isAfter(promotion.getNgayKetThuc())) {
            isValid = true;
            model.addAttribute("errorNgayBatDau", "Ngày bắt đầu phải trước ngày kết thúc!");
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
        }
        if (promotion.getNgayKetThuc() == null) {
            isValid = true;
            model.addAttribute("errorNgayKetThuc", "Ngày kết thúc chưa được chọn!");
            model.addAttribute("startDate", promotion.getNgayBatDau());
        } else if (promotion.getNgayKetThuc().compareTo(promotion.getNgayBatDau()) == 0 || promotion.getNgayKetThuc().isBefore(promotion.getNgayBatDau())) {
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

        if (promotion.getGiaTriGiam() == null) {
            isValid = true;
            model.addAttribute("errorGiaTriGiam", "Giá trị giảm chưa được nhập!");
        } else if (promotion.getGiaTriGiam() <= 5) {
            isValid = true;
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
            model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải lớn hơn 5%!");
        } else if (promotion.getGiaTriGiam() > 50 && promotion.getLoaiGiamGia()) {
            isValid = true;
            model.addAttribute("startDate", promotion.getNgayBatDau());
            model.addAttribute("endDate", promotion.getNgayKetThuc());
            model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải nhỏ hơn 50%!");
        }

        else if (!promotion.getLoaiGiamGia()) {
            if (promotion.getGiaTriGiam() > (promotion.getGiaTriDonHang() * 0.5)) {
                isValid = true;
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
                model.addAttribute("errorGiaTriGiam", "Giá trị giảm không được lớn hơn 50% giá trị đơn hàng!");
            } else if (promotion.getGiaTriGiam() < 1000 && !promotion.getLoaiGiamGia()) {
                isValid = true;
                model.addAttribute("startDate", promotion.getNgayBatDau());
                model.addAttribute("endDate", promotion.getNgayKetThuc());
                model.addAttribute("errorGiaTriGiam", "Giá trị giảm phải tối thiểu 1.000 vnđ!");
            }
        }

        if (promotion.getGiaTriDonHang() == null) {
            isValid = true;
            model.addAttribute("errorBill", "Giá trị đơn hàng chưa được nhập!");
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
        dotGiamGia.setTrangThai(dgg.getTrangThai());
        dotGiamGiaService.create(dgg);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/admin/promotion";
    }

}
