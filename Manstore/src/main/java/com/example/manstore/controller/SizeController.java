package com.example.manstore.controller;

import com.example.manstore.dto.respone.SizeResponse;
import com.example.manstore.entity.Size;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.SizeRepository;
import com.example.manstore.service.Impl.SizeServiceImpl;
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
@RequestMapping("/admin/size")
@CrossOrigin("*")
public class SizeController {

    @Autowired
    private SizeServiceImpl sizeService;

    @Autowired
    private SizeRepository sizeRepository;

    @GetMapping("/getAll")
    public String getAllSize(Model model) {
        model.addAttribute("sizes", sizeService.getAllSize());
        return "size_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllSize(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SizeResponse> pageResult = sizeRepository.findAllSize(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("size", new Size());
        return "admin/size/size-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("size") Size size
    ) {
        boolean isValid = false;
        for (Size s : sizeService.getAllSize()) {
            if (size.getTen().equalsIgnoreCase(s.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên size đã tồn tại!");
            }
        }
        if (size.getTen().isBlank()) {
            model.addAttribute("errorName", "Tên size không được để trống!");
            isValid = true;
        }
        //vòng for check trùng mã
        for (Size s : sizeService.getAllSize()) {
            if (size.getMa().equalsIgnoreCase(s.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã size đã tồn tại!");
            }
        }
        if (size.getMa().isBlank()) {
            model.addAttribute("errorMa", "Mã size không được để trống!");
            isValid = true;
        }

        if (size.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (size.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }
        if (!isValid) {
            sizeService.save(size);
            model.addAttribute("message", true);
            return "admin/size/size-create";
        } else {
            model.addAttribute("message", false);
            return "admin/size/size-create";
        }

    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(sizeService.getSizeById(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("th") Size size, Model model) {
        Size s = sizeService.getSizeById(id);
        s.setTen(size.getTen());
        s.setMoTa(size.getMoTa());
        sizeService.update(size);
        model.addAttribute("updateSuccess", true);
        return "admin/size/size-list";
    }

    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<Size> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = sizeService.pageOfSize(pageable);
        } else {
            page = sizeRepository.searchSize(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }


}
