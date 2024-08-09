package com.example.manstore.controller.client;


import com.example.manstore.service.Impl.DanhMucServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/category")
public class DanhMucRestController {

    @Autowired
    DanhMucServiceImpl danhMucService;

    @GetMapping("/find-all")
    private ResponseEntity<?> findAll() {
        return ResponseEntity.ok(danhMucService.getAllDanhMuc());
    }
}
