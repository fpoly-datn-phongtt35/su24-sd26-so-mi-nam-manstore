package com.example.manstore.controller;


import com.example.manstore.dto.respone.DanhMucResponse;
import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.DanhMuc;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.DanhMucRepository;
import com.example.manstore.service.Impl.DanhMucServiceImpl;
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
@RequestMapping("/admin/category")
@CrossOrigin("*")
public class DanhMucController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @GetMapping("/getAll")
    public String getAllDanhMuc(Model model) {
        model.addAttribute("danhMucs", danhMucService.getAllDanhMuc());
        return "category_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllTH(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DanhMucResponse> pageResult = danhMucRepository.findAllDM(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(danhMucService.getDanhMucById(id));
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("dm", new DanhMuc());
        return "admin/category/category-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("dm") DanhMuc danhMuc) {
        boolean isValid = false;
        for (DanhMuc dm : danhMucService.getAllDanhMuc()) {
            if (danhMuc.getTen().equalsIgnoreCase(dm.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên danh mục đã tồn tại!");
            }
        }
        if (danhMuc.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Hãy nhập tên danh mục!");
        }
        //vòng for check trùng mã
        for (DanhMuc dm : danhMucService.getAllDanhMuc()) {
            if (danhMuc.getMa().equalsIgnoreCase(dm.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã danh muc đã tồn tại!");
            }
        }

        if (danhMuc.getMa().isBlank()) {
            isValid = true;
            model.addAttribute("errorMa", "Hãy nhập mã danh mục!");
        }

        if (danhMuc.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (danhMuc.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }

        if (!isValid) {
            danhMucService.save(danhMuc);
            model.addAttribute("message", true);
            return "admin/category/category-create";
        } else {
            model.addAttribute("message", false);
            return "admin/category/category-create";
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("dm") DanhMuc danhMuc, Model model) {
        DanhMuc dm = danhMucService.getDanhMucById(id);
        dm.setTen(danhMuc.getTen());
        dm.setMoTa(danhMuc.getMoTa());
        danhMucService.update(danhMuc);
        model.addAttribute("updateSuccess", true);
        return "admin/category/category-list";
    }



    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<DanhMuc> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = danhMucService.pageOfDM(pageable);
        } else {
            page = danhMucRepository.searchDM(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
