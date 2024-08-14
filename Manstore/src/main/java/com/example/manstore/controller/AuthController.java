package com.example.manstore.controller;

import com.example.manstore.CustomModel.AuthRequest;
import com.example.manstore.CustomModel.AuthResponse;
import com.example.manstore.entity.KhachHang;
import com.example.manstore.entity.NhanVien;
import com.example.manstore.repository.GioHangChiTietRepository;
import com.example.manstore.repository.KhachHangRepository;
import com.example.manstore.repository.NhanVienRepository;
import com.example.manstore.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @Autowired
    NhanVienRepository nvrp;
    @Autowired
    KhachHangRepository khrp;
    @Autowired
    GioHangChiTietRepository ghrp;
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/getStaff/{email}")
    public NhanVien getStaff(@PathVariable("email") String email, @RequestBody String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println(email);
        if (nvrp.getByEmail(email).isPresent()) {
            return nvrp.getByEmail(email).get();
        } else {
            return null;
        }
    }

    @PostMapping("/getCustomer/{email}")
    public KhachHang getCustomer(@PathVariable("email") String email, @RequestBody String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("user_token", token);
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println(email);
        if (khrp.findByEmail(email).isPresent()) {
            return khrp.getByEmail(email).get();
        } else {
            return null;
        }
    }

    @PostMapping("/getInformation-staff/{id}")
    public ResponseEntity<?> getStaffInformation(@PathVariable("id") String id) {
        if (nvrp.findById(Integer.parseInt(id)).isPresent()) {
            return new ResponseEntity<>(nvrp.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.OK);
        }
    }

//    @GetMapping("/get-quantity-cart/{id}")
//    public ResponseEntity<?> getQuantityCart(@PathVariable("id") String id) {
//        if (ghrp.getByIdKH(id).size() > 0) {
//            return new ResponseEntity<>(ghrp.getByIdKH(id).size(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("0", HttpStatus.OK);
//        }
//    }
@GetMapping("/get-quantity-cart/{id}")
public ResponseEntity<?> getQuantityCart(@PathVariable("id") String id) {
    try {
        // Lấy danh sách giỏ hàng
        List<?> cartList = ghrp.getByIdKH(id);

        // Trả về số lượng giỏ hàng dưới dạng số nguyên
        int quantity = cartList.size();

        // Trả về số lượng cùng với mã trạng thái HTTP OK
        return new ResponseEntity<>(quantity, HttpStatus.OK);
    } catch (Exception e) {
        // Xử lý lỗi và trả về mã lỗi thích hợp
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//    @PostMapping("/getInformation-customer/{id}")
//    public ResponseEntity<?> getCustomerInformation(@PathVariable("id") String id) {
//        if (khrp.findById(Integer.parseInt(id)).isPresent()) {
//            return new ResponseEntity<>(khrp.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("null", HttpStatus.OK);
//        }
//    }
@PostMapping("/getInformation-customer/{id}")
public ResponseEntity<?> getCustomerInformation(@PathVariable("id") String id) {
    try {
        int customerId = Integer.parseInt(id);
        Optional<KhachHang> khachHangOptional = khrp.findById(customerId);

        if (khachHangOptional.isPresent()) {
            return new ResponseEntity<>(khachHangOptional.get(), HttpStatus.OK);
        } else {
            // Trả về null nhưng dưới dạng JSON thay vì chuỗi "null"
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    } catch (NumberFormatException e) {
        // Xử lý lỗi nếu id không phải là số nguyên hợp lệ
        return new ResponseEntity<>("Invalid customer ID format", HttpStatus.BAD_REQUEST);
    }
}


}
