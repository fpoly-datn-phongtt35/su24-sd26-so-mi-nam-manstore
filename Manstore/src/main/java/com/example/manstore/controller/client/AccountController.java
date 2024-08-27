package com.example.manstore.controller.client;

import com.example.manstore.entity.HoaDon;
import com.example.manstore.entity.KhachHang;
import com.example.manstore.service.HoaDonService;
import com.example.manstore.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/client/account")
public class AccountController {
    @Autowired
    KhachHangService service;
    @Autowired
    HoaDonService donHangService;
    @Autowired
    PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String account(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("account", service.getByID(id));
        return "client/pages/users/account";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes
            , @ModelAttribute("khachHang") KhachHang khachHang) {
        KhachHang updateKH = service.getByID(id);
        updateKH.setTen(khachHang.getTen());
        updateKH.setSdt(khachHang.getSdt());
        updateKH.setNgaySinh(khachHang.getNgaySinh());
        updateKH.setGioiTinh(khachHang.isGioiTinh());
//        updateKH.setAnhKhachHang(khachHang.getAnhKhachHang());
        service.save(updateKH);
        System.out.println("after update" + updateKH.toString());
        redirectAttributes.addFlashAttribute("stateUpdateInformation", true);
        return "redirect:/client/account/" + id;
    }

    @RequestMapping(value = "/change_Password/{id}", method = RequestMethod.POST)
    public String changePassword(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes
            , @ModelAttribute("khachHang") KhachHang khachHang) {
        KhachHang updateKH = service.getByID(id);
        updateKH.setMatKhau(khachHang.getMatKhau());
        updateKH.setMaHoaMatKhau(encoder.encode(khachHang.getMatKhau()));
        service.save(updateKH);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/client/account/" + id;
    }

//    @RequestMapping(value = "/{id}/cancelOder/{idDH}", method = RequestMethod.POST)
//    public String huyDon(@PathVariable(value = "idDH") String idDH, @PathVariable(value = "id") String id, RedirectAttributes redirectAttributes
//            , @ModelAttribute("donHang") HoaDon donHang) {
//        try {
//            HoaDon donHangCapNhat = donHangService.findByHD(idDH);
//            if (donHangCapNhat != null) {
//                donHangCapNhat.setGhiChu(donHang.getGhiChu());
//                donHangCapNhat.setTrangThai(6); // Trạng thái 6 có thể là trạng thái hủy
//                donHangService.save(donHangCapNhat);
//                redirectAttributes.addFlashAttribute("message", "Đơn hàng đã được hủy thành công.");
//            } else {
//                redirectAttributes.addFlashAttribute("message", "Không tìm thấy đơn hàng để hủy.");
//            }
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi khi hủy đơn hàng.");
//        }
//        return "redirect:/client/account/" + id;
//    }

    @RequestMapping(value = "/{id}/cancelOder/{idDH}", method = RequestMethod.POST)
    public String cancelOder(@PathVariable(value = "idDH") String idHD, @PathVariable(value = "id") String id, RedirectAttributes redirectAttributes
            , @ModelAttribute("donHang") HoaDon donHang) {
        HoaDon updateDH = donHangService.findByHD(idHD);
        updateDH.setGhiChu(donHang.getGhiChu());
        updateDH.setTrangThai(6);
        donHangService.save(updateDH);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/client/account/" + id;
    }
}
