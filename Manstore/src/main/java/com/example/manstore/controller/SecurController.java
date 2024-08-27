package com.example.manstore.controller;

import com.example.manstore.CustomModel.ResponseCustom;
import com.example.manstore.entity.KhachHang;
import com.example.manstore.entity.NhanVien;
import com.example.manstore.repository.KhachHangRepository;
import com.example.manstore.repository.NhanVienRepository;
import com.example.manstore.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/auth")
public class SecurController {

    @Autowired
    private MailService mailService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(SecurController.class.getName());

    @PostMapping("/init-reset-pass")
    public ResponseEntity<?> sendLinkResetPass(@RequestParam("email") String email) {
        System.out.println(email);
        Optional<KhachHang> khachHang = khachHangRepository.findByEmail(email);
        if (khachHang.isEmpty()) {
            Optional<NhanVien> nhanVien = nhanVienRepository.getByEmail(email);
            if (nhanVien.isEmpty()) {
                return new ResponseEntity<>("Không tìm thấy tài khoản", HttpStatus.EXPECTATION_FAILED);
            }
            mailService.sendEmail(email, "Quên mật khẩu", "<h2>TONER</h2>:<br>" +
                    "Chúng tôi đã nhận được yêu cầu quên mật khẩu của bạn<br>Mật khẩu của bạn là:<br><br>" +
                    "<a style=\"background-color: #2f5fad; padding: 10px; color: #fff; font-size: 18px; font-weight: bold;\">" + nhanVien.get().getMatKhau() + "</a>", false, true);
        } else {
            mailService.sendEmail(email, "Quên mật khẩu", "<h2>TONER</h2>:<br>" +
                    "Chúng tôi đã nhận được yêu cầu quên mật khẩu của bạn<br>Mật khẩu của bạn là:<br><br>" +
                    "<a style=\"background-color: #2f5fad; padding: 10px; color: #fff; font-size: 18px; font-weight: bold;\">" + khachHang.get().getMatKhau() + "</a>", false, true);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/regis-customer", consumes = {"application/json", "application/json;charset=UTF-8"} )
    public ResponseEntity<?> regisAccount(@RequestBody KhachHang khachHang) {
        logger.info("Received request to register customer with email: " + khachHang.getEmail());
        logger.info("Customer data: " + khachHang.toString());

        Optional<KhachHang> kh = khachHangRepository.findByEmail(khachHang.getEmail());
        Optional<NhanVien> nv = nhanVienRepository.getByEmail(khachHang.getEmail());
        if (kh.isPresent() || nv.isPresent()) {
            ResponseCustom response = new ResponseCustom();
            response.setMessage("Email đã được sử dụng!");
            response.setStatusText("failure");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        Optional<KhachHang> khphone = khachHangRepository.findByPhone(khachHang.getSdt());
        if (khphone.isPresent()) {
            ResponseCustom response = new ResponseCustom();
            response.setMessage("Số điện thoại đã được sử dụng!!");
            response.setStatusText("failure");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        logger.info("Customer information before saving: " + khachHang);

        khachHang.setMaHoaMatKhau(passwordEncoder.encode(khachHang.getMatKhau()));
        khachHang.setNgayTao(LocalDate.now());
        khachHang.setNgaySinh(new Date(System.currentTimeMillis()).toLocalDate());
        khachHang.setGioiTinh(true);

        List<KhachHang> list = khachHangRepository.findAll();
        List<Integer> integerList = new ArrayList<>();
        if (list.size() == 0) {
            khachHang.setMa("KH1");
        } else {
            for (KhachHang khachHang1 : list) {
                int index = Integer.parseInt(khachHang1.getMa().substring(2));
                integerList.add(index);
            }
            Optional<Integer> maxNumber = integerList.stream().max(Integer::compareTo);
            maxNumber.ifPresent(integer -> khachHang.setMa("KH" + (integer + 1)));
        }

        KhachHang result = khachHangRepository.save(khachHang);

//        ThongBao thongBao = new ThongBao();
//
//        thongBao.setTrangThaiDonHang(0);
//
//        thongBao.setThoiGianGui(LocalDateTime.now());
//
//        thongBao.setKh(khachHang);
//
//        thongBaoService.save(thongBao);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
