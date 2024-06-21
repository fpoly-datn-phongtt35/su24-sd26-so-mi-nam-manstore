package com.example.manstore.controller;

import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.ThuongHieuRepository;
import com.example.manstore.service.Impl.ThuongHieuServiceImpl;
import com.example.manstore.service.ThuongHieuService;
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
@RequestMapping("/admin/trademark")
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
    public ResponseEntity<?> getAllTH(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ThuongHieuResponse> pageResult = thuongHieuRepository.findAllTH(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    @ResponseBody
    public ThuongHieu getThuongHieuDetail(@PathVariable("id") Integer id) {
        return thuongHieuService.getThuongHieuById(id);
    }

//    @GetMapping("/page")
//    public ResponseEntity<Page<ThuongHieu>> getThuongHieuPage(@RequestParam int page, @RequestParam int size) {
//        Page<ThuongHieu> thuongHieuPage = thuongHieuService.pageOfTH(PageRequest.of(page, size));
//        return ResponseEntity.ok(thuongHieuPage);
//    }

//    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
//    private ResponseEntity<?> paginationLoad(@PathVariable("pageNumber") int pageNumber) {
//        Pageable pageable = PageRequest.of(pageNumber, 2
////                , Sort.by("id").descending()
//        );
//        Page<ThuongHieu> page = thuongHieuService.pageOfTH(pageable);
//        return new ResponseEntity<>(page, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
//    private ResponseEntity<?> findAll() {
//        List<ThuongHieu> list = thuongHieuService.getAll();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }



}
