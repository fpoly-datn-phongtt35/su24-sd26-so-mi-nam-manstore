package com.example.manstore.controller.client;

import com.example.manstore.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/client/cart", "/api/cart"})
public class GioHangRestController {

    @Autowired
    GioHangService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getGHByIdKH(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(service.findByIdKH(id));
    }
}