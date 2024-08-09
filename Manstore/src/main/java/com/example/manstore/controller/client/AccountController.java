package com.example.manstore.controller.client;

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
}
