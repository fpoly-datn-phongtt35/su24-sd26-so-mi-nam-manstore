package com.example.manstore.controller.client;


import com.example.manstore.service.ChiTietSanPhamService;
import com.example.manstore.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/client/product", "/product"})
public class ControllerSanPham {
    @Autowired
    SanPhamService service;
    @Autowired
    ChiTietSanPhamService chiTietService;

    @GetMapping()
    public String getAll(@RequestParam(value = "sport", required = false) String sports, Model model) {
        if (sports != null && !sports.equalsIgnoreCase("")) {
            model.addAttribute("sport_param", sports);
        }
        return "client/pages/products/grid/product-grid-defualt";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("sanPham", service.getSanPhamById(id).get());
        return "client/pages/products/product-details";
    }

}
