package com.example.manstore.controller.admin;


import com.example.manstore.dto.request.KhachHangRequest;
import com.example.manstore.entity.DiaChi;
import com.example.manstore.entity.KhachHang;
import com.example.manstore.repository.DiaChiRepository;
import com.example.manstore.service.Impl.KhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin/customer")
public class KhachHangController {

    @Autowired
    private KhachHangServiceImpl khsv;

    @Autowired
    private DiaChiRepository dcrp;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    private ResponseEntity<?> paginationLoad(@PathVariable("pageNumber") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("ngayTao").descending());
        Page<KhachHang> page = khsv.pageOfKhachHang(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/page/search/{pageNumber}/{keyWord}/{gender}/{sort}", method = RequestMethod.GET)
    private ResponseEntity<?> getPageAndSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord,
            @PathVariable("gender") String gender,
            @PathVariable("sort") int sort) {

        Pageable pageable;
        Page<KhachHang> page;

        Sort sortCriteria;
        switch (sort) {
            case 1:
                sortCriteria = Sort.by(Sort.Order.asc("ngaySinh"), Sort.Order.desc("ngayTao"));
                break;
            case 2:
                sortCriteria = Sort.by(Sort.Order.desc("ngaySinh"), Sort.Order.desc("ngayTao"));
                break;
            case 3:
                sortCriteria = Sort.by(Sort.Order.asc("ngayTao"));
                break;
            default:
                sortCriteria = Sort.by(Sort.Order.desc("ngayTao"));
                break;
        }
        pageable = PageRequest.of(pageNumber, 3, sortCriteria);

        if ("-1".equals(gender) && !"null".equalsIgnoreCase(keyWord)) {
            page = khsv.seacrch(keyWord, pageable);
        } else if (!"-1".equals(gender) && "null".equalsIgnoreCase(keyWord)) {
            boolean genderBoolean = "0".equals(gender);
            page = khsv.fillterByGenderNoSearch(pageable, genderBoolean);
        } else if (!"-1".equals(gender) && !"null".equalsIgnoreCase(keyWord)) {
            boolean genderBoolean = "0".equals(gender);
            page = khsv.fillterByGenderAndSearch(pageable, keyWord, genderBoolean);
        } else {
            page = khsv.pageOfKhachHang(pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @RequestMapping(value = "/find/{phone}", method = RequestMethod.GET)
    private ResponseEntity<?> findByPhone(@PathVariable("phone") String phone
    ) {
        if(khsv.getByPhone(phone) != null){
            return new ResponseEntity<>(khsv.getByPhone(phone), HttpStatus.OK);
        }
        return new ResponseEntity<>("null", HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String show() {
        return "admin/customer/customer-list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detailInvoice(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(khsv.getByID(Integer.valueOf(id)));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    private String update(@ModelAttribute("khachhang") KhachHang kh, @PathVariable("id") String id) {
        khsv.update(kh);
        return "redirect:/admin/customer";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "admin/customer/customer-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("khachHang") KhachHangRequest khachHang,
                      @RequestParam(value = "tinh") String tinh,
                      @RequestParam(value = "quan") String quan,
                      @RequestParam(value = "xa") String xa,
                      @RequestParam(value = "diaChiCT") String diaChiCT
    ) {
        boolean isValid = false;
        for (KhachHang kh : khsv.getALL()) {
            khachHang.setMa("KH" + khsv.getALL().size());
            if (khachHang.getSdt().equalsIgnoreCase(kh.getSdt())) {
                isValid = true;
                model.addAttribute("errorPhone", "Số điện thoại trùng !");
            }
            if (khachHang.getEmail().equalsIgnoreCase(kh.getEmail())) {
                isValid = true;
                model.addAttribute("errorEmail", "Email trùng !");
            }
        }
        if (khachHang.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Vui lòng nhập tên!");
        }
        if (khachHang.getSdt().isBlank()) {
            isValid = true;
            model.addAttribute("errorPhone", "Vui lòng nhập số điện thoại!");
        } else if (!khachHang.getSdt().matches("^(08|09|03|05|07)[0-9]{7,8}$")) {
            isValid = true;
            model.addAttribute("errorPhone", "Số điện thoại chưa đúng!");
        }
        if (khachHang.getEmail().isBlank()) {
            isValid = true;
            model.addAttribute("errorEmail", "Vui lòng email!");
        } else if (!khachHang.getEmail().matches(".+@.+\\.+.+")) {
            isValid = true;
            model.addAttribute("errorEmail", "Email chưa đúng!");
        }
        if (khachHang.getNgaySinh() == null || khachHang.getNgaySinh().isEmpty() || khachHang.getNgaySinh().isBlank()) {
            isValid = true;
            System.out.println("error birth");
            model.addAttribute("errorBirthday", "Vui lòng ngày sinh!");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(khachHang.getNgaySinh());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = null;
        if (parsedDate != null) {
            sqlDate = new java.sql.Date(parsedDate.getTime());
            if (!sqlDate.toLocalDate().isBefore(LocalDate.now())) {
                isValid = true;
                model.addAttribute("errorBirthday", "Vui lòng nhập đúng ngày sinh!");
            }
        }
        DiaChi diaChi = new DiaChi();
        diaChi.setTinhTp(tinh);
        diaChi.setQuanHuyen(quan);
        diaChi.setXaPhuongThitran(xa);
        diaChi.setDiaChiCuThe(diaChiCT);
        if (diaChi.getDiaChiCuThe().isBlank()) {
            isValid = true;
            model.addAttribute("errorAddress", "Vui lòng nhập địa chỉ!");
        }
        if (diaChi.getTinhTp().isBlank()) {
            isValid = true;
            model.addAttribute("errorProvince", "Chưa chọn Tỉnh!");
        }
        if (diaChi.getQuanHuyen().isBlank()) {
            isValid = true;
            model.addAttribute("errorDistrict", "Chưa chọn Quận Huyện!");
        }
        if (diaChi.getXaPhuongThitran().isBlank()) {
            isValid = true;
            model.addAttribute("errorWard", "Chưa chọn Xã Phường!");
        }
        if (!isValid) {
            KhachHang kh = new KhachHang();
            List<KhachHang> list = khsv.getALL();
            List<Integer> integerList = new ArrayList<>();
            if (list.size() == 0) {
                kh.setMa("KH1");
            } else {
                for (KhachHang khachHang1 : list) {
                    int index = Integer.parseInt(khachHang1.getMa().substring(2));
                    integerList.add(index);
                }
                Optional<Integer> maxNumber = integerList.stream().max(Integer::compareTo);
                maxNumber.ifPresent(integer -> kh.setMa("KH" + (integer + 1)));
            }
            kh.setSdt(khachHang.getSdt());
            kh.setTen(khachHang.getTen());
            kh.setEmail(khachHang.getEmail());
            kh.setGioiTinh(khachHang.isGioiTinh());
            kh.setNgaySinh(sqlDate.toLocalDate());
            kh.setMatKhau("12345");
            kh.setNgayTao(LocalDate.now());
            kh.setMaHoaMatKhau(passwordEncoder.encode(kh.getMatKhau()));
            diaChi.setIdKhachHang(kh);
            khsv.save(kh);
            dcrp.save(diaChi);
            model.addAttribute("message", true);
            return "admin/customer/customer-create";
        } else {
            return "admin/customer/customer-create";
        }
    }


}
