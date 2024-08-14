package com.example.manstore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/client/cart", "/cart"
})
public class GioHangController {
    @GetMapping()
    public String getAll() {
        return "client/shop/shop-cart";
    }
}