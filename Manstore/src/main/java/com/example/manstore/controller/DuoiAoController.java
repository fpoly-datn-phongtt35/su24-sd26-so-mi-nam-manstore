package com.example.manstore.controller;


import com.example.manstore.dto.respone.DuoiAoResponse;
import com.example.manstore.dto.respone.ThuongHieuResponse;
import com.example.manstore.entity.DuoiAo;
import com.example.manstore.entity.ThuongHieu;
import com.example.manstore.repository.DuoiAoRepository;
import com.example.manstore.service.Impl.DuoiAoServiceImpl;
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
@RequestMapping("/admin/shirtTail")
@CrossOrigin("*")
public class DuoiAoController {

    @Autowired
    private DuoiAoServiceImpl duoiAoService;

    @Autowired
    private DuoiAoRepository duoiAoRepository;

    @GetMapping("/getAll")
    public String getAllDuoiAo(Model model) {
        model.addAttribute("duoiAos", duoiAoService.getAllDuoiAo());
        return "shirtTail_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllTH(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DuoiAoResponse> pageResult = duoiAoRepository.findAllDA(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(duoiAoService.getDuoiAoById(id));
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("da", new DuoiAo());
        return "admin/shirtTail/shirtTail-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("da") DuoiAo duoiAo) {
        boolean isValid = false;
        for (DuoiAo da : duoiAoService.getAllDuoiAo()) {
            if (duoiAo.getTen().equalsIgnoreCase(da.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên đuôi áo đã tồn tại!");
            }
        }
        if (duoiAo.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Hãy nhập tên đuôi áo!");
        }
        //vòng for check trùng mã
        for (DuoiAo da : duoiAoService.getAllDuoiAo()) {
            if (duoiAo.getMa().equalsIgnoreCase(da.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã đuôi áo đã tồn tại!");
            }
        }

        if (duoiAo.getMa().isBlank()) {
            isValid = true;
            model.addAttribute("errorMa", "Hãy nhập mã!");
        }

        if (duoiAo.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (duoiAo.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }

        if (!isValid) {
            duoiAoService.save(duoiAo);
            model.addAttribute("message", true);
            return "admin/shirtTail/shirtTail-create";
        } else {
            model.addAttribute("message", false);
            return "admin/shirtTail/shirtTail-create";
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("duoiAos") DuoiAo duoiAo, Model model) {
        DuoiAo da = duoiAoService.getDuoiAoById(id);
        da.setTen(duoiAo.getTen());
        da.setMoTa(duoiAo.getMoTa());
        duoiAoService.update(duoiAo);
        model.addAttribute("updateSuccess", true);
        return "admin/shirtTail/shirtTail-list";
    }



    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<DuoiAo> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = duoiAoService.pageOfDuoiAo(pageable);
        } else {
            page = duoiAoRepository.searchDA(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
