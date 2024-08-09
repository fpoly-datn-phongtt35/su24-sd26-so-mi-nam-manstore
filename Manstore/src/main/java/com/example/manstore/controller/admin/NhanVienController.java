package com.example.manstore.controller.admin;

import com.example.manstore.entity.NhanVien;
import com.example.manstore.service.NhanVienService;
import com.example.manstore.service.PhanQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin/staff")
public class NhanVienController {
    @Autowired
    NhanVienService nhanVienService;

    @Autowired
    PhanQuyenService phanQuyenService;

    @Autowired
    PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("list", nhanVienService.getAll());
        model.addAttribute("chucVu", phanQuyenService.getAll());
        return "admin/staff/staff-list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("chucVu", phanQuyenService.getAll());
        return "admin/staff/staff-add";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("nhanVien") NhanVien nhanVien) {
        boolean isValid = false;
        model.addAttribute("chucVu", phanQuyenService.getAll());
        for (NhanVien nv : nhanVienService.getAll()) {
            nhanVien.setMa("NV" + nhanVienService.getAll().size());
            if (nhanVien.getMa().equalsIgnoreCase(nv.getMa())) {
                nhanVien.setMa("NV" + (nhanVienService.getAll().size() + 1));
            }
            if (nhanVien.getSdt().equalsIgnoreCase(nv.getSdt())) {
                isValid = true;
                model.addAttribute("errorPhone", "Số điện thoại trùng !");
            }
            if (nhanVien.getEmail().equalsIgnoreCase(nv.getEmail())) {
                isValid = true;
                model.addAttribute("errorEmail", "Email trùng !");
            }
        }
        nhanVien.setMatKhau("12345");
        nhanVien.setMaHoaMatKhau(encoder.encode("12345"));
        if (nhanVien.getDiaChi().isEmpty()) {
            isValid = true;
            model.addAttribute("errorAdreess", "Địa chỉ trống!");
        }

        if (nhanVien.getTen().isEmpty()) {
            isValid = true;
            model.addAttribute("errorName", "Họ tên trống !");
        } else if (!nhanVien.getTen().matches("^([AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+)((\\s{1}[AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+){1,})$")) {
            isValid = true;
            model.addAttribute("errorName", "Họ tên không đúng định dạng !");
        }

        if (nhanVien.getSdt().isEmpty()) {
            isValid = true;
            model.addAttribute("errorPhone", "Số điện thoại trống !");
        } else if (!nhanVien.getSdt().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
            isValid = true;
            model.addAttribute("errorPhone", "Số điện thoại không đúng định dạng !");
        }

        if (nhanVien.getEmail().isEmpty()) {
            isValid = true;
            model.addAttribute("errorEmail", "Email trống !");
        } else if (!nhanVien.getEmail().matches("^(([a-zA-Z0-9\\._-]+)@([a-zA-Z0-9-]+)\\.([a-zA-Z]{2,4})(\\.([a-zA-Z]{2,4})|))$")) {
            isValid = true;
            model.addAttribute("errorEmail", "Email không đúng định dạng !");
        }

        if (nhanVien.getNgaySinh() == null) {
            isValid = true;
            model.addAttribute("errorBirthday", "Ngày sinh trống !");
        } else {
            LocalDate now = LocalDate.now();
            int age = Period.between(nhanVien.getNgaySinh(), now).getYears();
            if (age < 18) {
                isValid = true;
                model.addAttribute("errorBirthday", "Chưa đủ 18 tuổi !");
            }
        }
        if (isValid == false) {
//            nhanVien.setIdPhanQuyen(phanQuyenService.findById(Integer.parseInt("1")).get());
            nhanVien.setTrangThai(0);
            nhanVienService.save(nhanVien);
            model.addAttribute("message", true);
            return "admin/staff/staff-add";
        } else {
            model.addAttribute("message", false);
            return "admin/staff/staff-add";
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(nhanVienService.findById(id));
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes
            , @ModelAttribute("nhanVien") NhanVien nhanVien) {
        NhanVien updateNV = nhanVienService.findById(id).get();
        updateNV.setTen(nhanVien.getTen());
        updateNV.setSdt(nhanVien.getSdt());
        updateNV.setNgaySinh(nhanVien.getNgaySinh());
        updateNV.setTrangThai(nhanVien.getTrangThai());
        updateNV.setEmail(nhanVien.getEmail());
        updateNV.setGioiTinh(nhanVien.getGioiTinh());
        updateNV.setDiaChi(nhanVien.getDiaChi());
        updateNV.setMaHoaMatKhau((nhanVien.getMaHoaMatKhau()));
        nhanVienService.save(nhanVien);
        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/admin/staff";
    }

}
