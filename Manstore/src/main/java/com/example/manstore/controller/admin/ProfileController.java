package com.example.manstore.controller.admin;

import com.example.manstore.entity.NhanVien;
import com.example.manstore.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class ProfileController {
    @Autowired
    NhanVienService service;
    @Autowired
    PasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @GetMapping("/admin/profile/{id}")
    public String profile(Model model, @PathVariable(value = "id") Integer id) {
        model.addAttribute("profile", service.findById(id).get());
        return "admin/profile/account";
    }

    @RequestMapping(value = "/admin/profile/change_Password/{id}", method = RequestMethod.POST)
    public String changePassword(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes
            , @ModelAttribute("nhanVien") NhanVien nhanVien) {
        NhanVien update = service.findById(id).get();
        update.setMatKhau(nhanVien.getMatKhau());
        update.setMaHoaMatKhau(encoder.encode(nhanVien.getMatKhau()));
        service.save(update);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/admin/profile/" + id;
    }

    @RequestMapping(value = "/api/admin/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> account(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id).get(), HttpStatus.OK);
    }
}
