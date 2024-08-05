package com.example.manstore.controller.client;

import com.example.manstore.CustomModel.ResponseCustom;
import com.example.manstore.entity.SanPham;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/client/product_detail", "/api/product_detail"
})
public class SanPhamChiTietRestController {
    @Autowired
    private ChiTietSanPhamImpl sv;
    @Autowired
    private SanPhamServiceImpl sanPhamService;
//    @Autowired
//    private AnhSanPhamServiceImpl serviceASP;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(sv.getAllCTSP(), HttpStatus.OK);
    }

    @RequestMapping(value = "/detailPD/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getByid(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(sv.getListCTSPById(id));
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
    private ResponseEntity<?> findIdProductAndColor(@PathVariable("id") String id, @PathVariable("color") String color) {
        return ResponseEntity.ok().body(sv.findListProductByColor(id, color));
    }

//    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
//    private ResponseEntity<?> getPictureByIdProductDetail(@PathVariable("id") String id) {
//        if (serviceASP.getByIdProduct(id).size() == 0) {
//            System.out.println("Product Picture is null");
//            ResponseCustom responseCustom = new ResponseCustom();
//            responseCustom.setStatusText("failure");
//            responseCustom.setMessage("List Picture Is Null");
//            return ResponseEntity.ok().body(responseCustom);
//        }
//        System.out.println(serviceASP.getByIdProduct(id).toString());
//        return ResponseEntity.ok().body(serviceASP.getByIdProduct(id));
//    }

//    @RequestMapping(value = "/picture-detail/{id}/{color}", method = RequestMethod.GET)
//    private ResponseEntity<?> getPictureByIdProductAndColor(@PathVariable("id") String id,
//                                                            @PathVariable("color") String color) {
//        if (serviceASP.getByIdProductAndColor(id, color).size() == 0) {
//            System.out.println("Product Picture is null");
//            ResponseCustom responseCustom = new ResponseCustom();
//            responseCustom.setStatusText("failure");
//            responseCustom.setMessage("List Picture Is Null");
//            return ResponseEntity.ok().body(responseCustom);
//        }
//        System.out.println(serviceASP.getByIdProductAndColor(id, color).toString());
//        return ResponseEntity.ok().body(serviceASP.getByIdProductAndColor(id, color));
//    }

    @RequestMapping(value = "/detailSL/{id}/{color}/{size}", method = RequestMethod.GET)
    private ResponseEntity<?> findIdProductAndColorAndSize(@PathVariable("id") String id, @PathVariable("color") String color, @PathVariable("size") String size) {
        return ResponseEntity.ok().body(sv.findIdProductByColorAndSize(id, color, size));
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getByidSPCT(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(sv.getCTSPById(Integer.parseInt(id)));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private ResponseEntity<?> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok().body(sv.search(keyword));
    }
}
