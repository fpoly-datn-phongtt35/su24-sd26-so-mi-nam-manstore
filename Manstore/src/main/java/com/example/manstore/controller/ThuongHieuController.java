package com.example.manstore.controller;

import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.ThuongHieuRepository;
import com.example.manstore.service.Impl.ThuongHieuServiceImpl;
import com.example.manstore.service.ThuongHieuService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/trademark")
@CrossOrigin("*")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @GetMapping("/getAll")
    public String getAllThuongHieu(Model model) {
        model.addAttribute("thuongHieus", thuongHieuService.getAllThuongHieu());
        return "trademark_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllTH(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ThuongHieuResponse> pageResult = thuongHieuRepository.findAllTH(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(thuongHieuService.getThuongHieuById(id));
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("th", new ThuongHieu());
        return "admin/trademark/trademark-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("th") ThuongHieu thuongHieu) {
        boolean isValid = false;
        for (ThuongHieu th : thuongHieuService.getAllThuongHieu()) {
            if (thuongHieu.getTen().equalsIgnoreCase(th.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên thương hiệu đã tồn tại!");
            }
        }
        if (thuongHieu.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Hãy nhập tên thương hiêu!");
        }
        //vòng for check trùng mã
        for (ThuongHieu th : thuongHieuService.getAllThuongHieu()) {
            if (thuongHieu.getMa().equalsIgnoreCase(th.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã thương hiệu đã tồn tại!");
            }
        }

        if (thuongHieu.getMa().isBlank()) {
            isValid = true;
            model.addAttribute("errorMa", "Hãy nhập mã thương hiệu!");
        }

        if (thuongHieu.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (thuongHieu.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }

        if (!isValid) {
            thuongHieuService.save(thuongHieu);
            model.addAttribute("message", true);
            return "admin/trademark/trademark-create";
        } else {
            model.addAttribute("message", false);
            return "admin/trademark/trademark-create";
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("th") ThuongHieu thuongHieu, Model model) {
        ThuongHieu th = thuongHieuService.getThuongHieuById(id);
        th.setTen(thuongHieu.getTen());
        th.setMoTa(thuongHieu.getMoTa());
        thuongHieuService.update(thuongHieu);
        model.addAttribute("updateSuccess", true);
        return "admin/trademark/trademark-list";
    }



    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<ThuongHieu> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = thuongHieuService.pageOfTH(pageable);
        } else {
            page = thuongHieuRepository.searchTH(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
