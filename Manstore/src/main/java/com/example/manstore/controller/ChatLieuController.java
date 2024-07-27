package com.example.manstore.controller;


import com.example.manstore.dto.respone.ChatLieuResponse;
import com.example.manstore.entity.ChatLieu;
import com.example.manstore.repository.ChatLieuRepository;
import com.example.manstore.service.Impl.ChatLieuServiceImpl;
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
@RequestMapping("/admin/material")
public class ChatLieuController {

    @Autowired
    private ChatLieuServiceImpl chatLieuService;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @GetMapping("/getAll")
    public String getAllChatLieu(Model model) {
        model.addAttribute("chatLieus", chatLieuService.getAllChatLieu());
        return "material_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllTH(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ChatLieuResponse> pageResult = chatLieuRepository.findAllChatLieu(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(chatLieuService.getChatLieuById(id));
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("cl", new ChatLieu());
        return "admin/material/material-create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("cl")ChatLieu chatLieu) {
        boolean isValid = false;
        for (ChatLieu cl : chatLieuService.getAllChatLieu()) {
            if (chatLieu.getTen().equalsIgnoreCase(cl.getTen())) {
                isValid = true;
                model.addAttribute("errorName", "Tên chất liệu đã tồn tại!");
            }
        }
        if (chatLieu.getTen().isBlank()) {
            isValid = true;
            model.addAttribute("errorName", "Hãy nhập tên chất liệu!");
        }
        //vòng for check trùng mã
        for (ChatLieu cl : chatLieuService.getAllChatLieu()) {
            if (chatLieu.getMa().equalsIgnoreCase(cl.getMa())) {
                isValid = true;
                model.addAttribute("errorMa", "Mã chất liệu đã tồn tại!");
            }
        }

        if (chatLieu.getMa().isBlank()) {
            isValid = true;
            model.addAttribute("errorMa", "Hãy nhập mã chất liệu!");
        }

        if (chatLieu.getMoTa().isEmpty()) {
            isValid = true;
            model.addAttribute("errorMoTa", "Hãy nhập mô tả!");
        } else if (chatLieu.getMoTa().length() > 255) {
            isValid = true;
            model.addAttribute("errorMoTa", "Tối đa 255 kí tự!");
        }

        if (!isValid) {
            chatLieuService.save(chatLieu);
            model.addAttribute("message", true);
            return "admin/material/material-create";
        } else {
            model.addAttribute("message", false);
            return "admin/material/material-create";
        }
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute("chatLieus") ChatLieu chatLieu, Model model) {
        ChatLieu cl = chatLieuService.getChatLieuById(id);
        cl.setTen(chatLieu.getTen());
        cl.setMoTa(chatLieu.getMoTa());
        chatLieuService.save(chatLieu);
        model.addAttribute("updateSuccess", true);
        return "admin/material/material-list";
    }



    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> getPageSearchAndFilter(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<ChatLieu> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = chatLieuService.pageOfCL(pageable);
        } else {
            page = chatLieuRepository.searchCL(keyWord, pageable);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
