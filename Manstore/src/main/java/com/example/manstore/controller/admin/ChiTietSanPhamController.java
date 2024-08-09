package com.example.manstore.controller.admin;


import com.example.manstore.dto.request.ChiTietSanPhamRequest;
import com.example.manstore.entity.ChiTietSanPham;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.MauSacServiceImpl;
import com.example.manstore.service.Impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/admin/product_detail")
public class ChiTietSanPhamController {

    @Autowired
    ChiTietSanPhamImpl chiTietSanPhamService;

    @Autowired
    private MauSacServiceImpl mauSacService;

    @Autowired
    private SizeServiceImpl sizeService;

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getProductDetailById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(chiTietSanPhamService.getCTSPById(Integer.parseInt(id)));
    }
    @RequestMapping(value = "/detailTSL/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getAllProductDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(chiTietSanPhamService.getAllCTSPById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> saveProductDetail(@RequestBody ChiTietSanPhamRequest spct, @PathVariable("id") String id) {

        System.out.println("ID Product: " + id);
        System.out.println("productDetail: " + spct);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(Integer.parseInt(id));
        chiTietSanPham.setSoluong(Integer.parseInt(spct.getSoluong()));
        chiTietSanPham.setIdMauSac(mauSacService.getMauSacById(Integer.parseInt(spct.getMauSac())));
        chiTietSanPham.setIdSize(sizeService.getSizeById(Integer.parseInt(spct.getSize())));
        chiTietSanPham.setTrangThai(spct.getTrangThai());

        if (spct.getDuongDan() != null && !spct.getDuongDan().isEmpty()) {
            chiTietSanPham.setDuongDan(spct.getDuongDan());
        }

        List<ChiTietSanPham> list = chiTietSanPhamService.getListCTSPById(String.valueOf(chiTietSanPham.getIdSanPham().getId()));
        list.remove(chiTietSanPham);
        String valid = "No Valid";
        for (ChiTietSanPham ctspFor : list) {
            boolean isValidMauSac = ctspFor.getIdMauSac().getId() == chiTietSanPham.getIdMauSac().getId();
            boolean isValidSize = ctspFor.getIdSize().getId() == chiTietSanPham.getIdSize().getId();
            if (isValidMauSac && isValidSize) {
                valid = "Sản Phẩm Chi tiết đã tồn tại";
                break;
            }
        }

        if (valid.equals("No Valid")) {
            chiTietSanPhamService.save(chiTietSanPham);
            return ResponseEntity.ok(Collections.singletonMap("message", "success"));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("message", valid));
        }
    }
}
