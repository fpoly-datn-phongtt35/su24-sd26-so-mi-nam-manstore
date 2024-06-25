package com.example.manstore.controller;


import com.example.manstore.dto.respone.KieuDangResponse;
import com.example.manstore.entity.KieuDang;
import com.example.manstore.repository.KieuDangRepository;
import com.example.manstore.service.Impl.KieuDangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/designs")
public class KieuDangController {

    @Autowired
    private KieuDangServiceImpl kieuDangService;

    @Autowired
    private KieuDangRepository kieuDangRepository;

    @GetMapping("/getAll")
    public String getAllKieuDang(Model model) {
        model.addAttribute("kieuDangs", kieuDangService.getAllKieuDang());
        return "designs_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllKieuDang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<KieuDangResponse> pageResult = kieuDangRepository.findAllKieuDang(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("kieuDang", new KieuDangResponse());
        return "admin/designs/designs-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("kieuDang") KieuDang kieuDang
    ) {
        boolean isValid = false;
        //vòng for check mã
        for (KieuDang s : kieuDangService.getAllKieuDang()) {
            if (kieuDang.getTen().equalsIgnoreCase(s.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên kiểu dáng đã tồn tại!");
            }
        }
        if (kieuDang.getMa().isBlank()){
            model.addAttribute("errorMa", "Mã kiểu dáng không được để trống!");
            isValid = true;
        }

        //vòng for check tên
        for (KieuDang s : kieuDangService.getAllKieuDang()) {
            if (kieuDang.getMa().equalsIgnoreCase(s.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã kiểu dáng đã tồn tại!");
            }
        }
        if (kieuDang.getTen().isBlank()) {
            model.addAttribute("errorName", "Tên kiểu dáng không được để trống!");
            isValid = true;
        }

        if (kieuDang.getMoTa().isBlank()){
            model.addAttribute("errorMoTa", "Mô tả kiểu dáng không được để trống!");
            isValid = true;
        }

        if (!isValid) {
            kieuDangService.save(kieuDang);
            model.addAttribute("message", true);
            return "admin/designs/designs-create";
        } else {
            model.addAttribute("message", false);
            return "admin/designs/designs-create";
        }

    }

}
