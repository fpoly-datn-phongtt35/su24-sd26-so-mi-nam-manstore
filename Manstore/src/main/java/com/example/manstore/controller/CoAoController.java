package com.example.manstore.controller;


import com.example.manstore.dto.respone.CoAoResponse;
import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.CoAo;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.CoAoRepository;
import com.example.manstore.service.Impl.CoAoServiceImpl;
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
@RequestMapping("/admin/collar")
@CrossOrigin("*")
public class CoAoController {

    @Autowired
    private CoAoServiceImpl coAoService;

    @Autowired
    private CoAoRepository coAoRepository;

    @GetMapping("/getAll")
    public String getAllThuongHieu(Model model) {
        model.addAttribute("coAos", coAoService.getAllCoAo());
        return "collar_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllTH(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CoAoResponse> pageResult = coAoRepository.findAllCoAo(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(coAoService.getCoAoById(id));
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("ca", new ThuongHieu());
        return "admin/collar/collar-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("ca") CoAo coAo) {
        boolean isValid = false;
        for (CoAo ca : coAoService.getAllCoAo()) {
            if (coAo.getTen().equalsIgnoreCase(ca.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên cổ áo đã tồn tại!");
            }
        }
        if (coAo.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Hãy nhập tên cồ áo!");
        }
        //vòng for check trùng mã
        for (CoAo ca : coAoService.getAllCoAo()) {
            if (coAo.getMa().equalsIgnoreCase(ca.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã cổ áo đã tồn tại!");
            }
        }
        if (coAo.getMa().isBlank()) {
            isValid = true;
            model.addAttribute("errorMa", "Hãy nhập mã cổ áo!");
        }

        if (coAo.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (coAo.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }

        if (!isValid) {
            coAoService.save(coAo);
            model.addAttribute("message", true);
            return "admin/collar/collar-create";
        } else {
            model.addAttribute("message", false);
            return "admin/collar/collar-create";
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("coAos") CoAo coAo, Model model) {
        CoAo ca = coAoService.getCoAoById(id);
        ca.setTen(coAo.getTen());
        ca.setMoTa(coAo.getMoTa());
        coAoService.update(coAo);
        model.addAttribute("updateSuccess", true);
        return "admin/collar/collar-list";
    }



    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<CoAo> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = coAoService.pageOfCoAo(pageable);
        } else {
            page = coAoRepository.searchCA(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
