package com.example.manstore.controller.client;


import com.example.manstore.dto.custom.ChiTietSanPhamDTO;
import com.example.manstore.dto.custom.ResponseCustom;
import com.example.manstore.entity.SanPham;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/client/product_detail", "/api/product_detail"
})
public class SanPhamChiTietRestController {

    @Autowired
    ChiTietSanPhamImpl chiTietSanPhamService;

    @Autowired
    SanPhamServiceImpl sanPhamService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(chiTietSanPhamService.getAllCTSP(), HttpStatus.OK);
    }

    @RequestMapping(value = "/detailPD/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getByid(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(chiTietSanPhamService.getListCTSPById(id));
    }

    @RequestMapping(value = "/check-status/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> check(@PathVariable("id") Integer id) {
        SanPham sp = sanPhamService.getSanPhamById(id).isPresent() ? sanPhamService.getSanPhamById(id).get() : null;
        if (sp == null) {
            return ResponseEntity.ok().body("failure");
        }
        return ResponseEntity.ok().body(sp.getTrangThai() != 0);
    }

    @RequestMapping(value = "/detail/{id}/{color}", method = RequestMethod.GET)
    private ResponseEntity<?> findIdProductAndColor(@PathVariable("id") Integer id, @PathVariable("color") String color) {
        List<ChiTietSanPhamDTO> products = chiTietSanPhamService.findListProductByColor(id, color);
        System.out.println("Controller: Found " + products.size() + " products for id " + id + " and color " + color);
        return ResponseEntity.ok().body(products);
    }

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getPictureByIdProductDetail(@PathVariable("id") String id){
        List<String> pictures = chiTietSanPhamService.getImgByProductId(id);
        if (pictures == null || pictures.isEmpty()) {
            System.out.println("Product Picture is null");
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("List Picture Is Null");
            return ResponseEntity.ok().body(responseCustom);
        }
        System.out.println("anh san pham chi tiet: " + pictures.toString());
        return ResponseEntity.ok().body(pictures);
    }

    @RequestMapping(value = "/picture-detail/{id}/{color}", method = RequestMethod.GET)
    private ResponseEntity<?> getPictureByIdProductAndColor(@PathVariable("id") String id,
                                                            @PathVariable("color") String color) {
        List<String> pictures = chiTietSanPhamService.getByIdProductAndColor(id, color);
        if (pictures == null || pictures.isEmpty()) {
            System.out.println("Product Picture is null");
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("List Picture Is Null");
            return ResponseEntity.ok().body(responseCustom);
        }
        System.out.println("anh san pham chi tiet theo mau sac: " + pictures.toString());
        return ResponseEntity.ok().body(pictures);
    }

    @RequestMapping(value = "/detailSL/{id}/{color}/{size}", method = RequestMethod.GET)
    private ResponseEntity<?> findIdProductAndColorAndSize(@PathVariable("id") String id, @PathVariable("color") String color, @PathVariable("size") String size) {
        return ResponseEntity.ok().body(chiTietSanPhamService.findIdProductByColorAndSize(id, color, size));
    }



    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getByidSPCT(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(chiTietSanPhamService.getCTSPById(id));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private ResponseEntity<?> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok().body(chiTietSanPhamService.search(keyword));
    }


}
