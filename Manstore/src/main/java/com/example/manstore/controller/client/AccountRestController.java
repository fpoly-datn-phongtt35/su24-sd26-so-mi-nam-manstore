package com.example.manstore.controller.client;

import com.example.manstore.entity.DiaChi;
import com.example.manstore.service.DiaChiService;
import com.example.manstore.service.HoaDonService;
import com.example.manstore.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/client/account")
public class AccountRestController {
    @Autowired
    KhachHangService service;
    @Autowired
    HoaDonService donHangService;
//    @Autowired
//    DonHangChiTietService donHangChiTietService;
    @Autowired
    DiaChiService diaChiService;
//    @Autowired
//    ThongBaoService thongBaoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> account(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.getByID(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/updateAddress/{idAddress}", method = RequestMethod.POST)
    public ResponseEntity<?> updateAddress(@PathVariable("id") String id, @PathVariable("idAddress") String idAddress) {
        for (DiaChi diaChi : diaChiService.getByIdKH(id)) {
            diaChi.setDefault(diaChi.getId() == Integer.parseInt(idAddress));
            diaChiService.save(diaChi);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}