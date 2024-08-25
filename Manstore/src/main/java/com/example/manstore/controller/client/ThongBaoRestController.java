package com.example.manstore.controller.client;


import com.example.manstore.service.Impl.ThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThongBaoRestController {

    @Autowired
    private ThongBaoServiceImpl service;

    @GetMapping("/api/client/notification/get-by-customer")
    private ResponseEntity<?> findNotifiByCustomer(@RequestParam("id") String id) {
        return new ResponseEntity<>(service.findByCustomer(id), HttpStatus.OK);
    }

    @GetMapping("/api/admin/notification")
    private ResponseEntity<?> findAll(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword == null) {
            return new ResponseEntity<>(service.findAllByStatus(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.search(keyword), HttpStatus.OK);
    }
}
