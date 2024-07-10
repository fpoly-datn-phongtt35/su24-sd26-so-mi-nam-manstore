package com.example.manstore.controller;


import com.example.manstore.dto.custom.ResponseCustom;
import com.example.manstore.dto.request.SanPhamRequest;
import com.example.manstore.dto.respone.SanPhanResponse;
import com.example.manstore.entity.*;
import com.example.manstore.repository.*;
import com.example.manstore.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin/product")
public class SanPhamController {

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private CoAoServiceImpl coAoService;

    @Autowired
    private CoAoRepository coAoRepository;

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ChatLieuServiceImpl chatLieuService;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private DuoiAoServiceImpl duoiAoService;

    @Autowired
    private DuoiAoRepository duoiAoRepository;

    @Autowired
    private KieuDangServiceImpl kieuDangService;

    @Autowired
    private KieuDangRepository kieuDangRepository;


    @RequestMapping("/getAll")
    public String getAllSanPham(Model model) {
        model.addAttribute("sanPhams", sanPhamService.getAllSanPham());
        return "product_list";
    }

    @GetMapping("/collar/getAll")
    @ResponseBody
    public List<CoAo> getAllCoAo() {
        return coAoService.getAllCoAo();
    }

    @GetMapping("/trademark/getAll")
    @ResponseBody
    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuService.getAllThuongHieu();
    }

    @GetMapping("/category/getAll")
    @ResponseBody
    public List<DanhMuc> getAllDanhMuc() {
        return danhMucService.getAllDanhMuc();
    }

    @RequestMapping("material/getAll")
    @ResponseBody
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuService.getAllChatLieu();
    }

    @RequestMapping("designs/getAll")
    @ResponseBody
    public List<KieuDang> getAllKieuDang() {
        return kieuDangService.getAllKieuDang();
    }

    @RequestMapping("shirtTail/getAll")
    @ResponseBody
    public List<DuoiAo> getAllDuoiAo() {
        return duoiAoService.getAllDuoiAo();
    }


    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllSP(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllSP(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    private String viewProductDetail() {
        return "admin/products/product-detailed";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detailProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(sanPhamService.getSanPhamById(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String update(Model model) {
        model.addAttribute("sp", new SanPham());
        return "admin/products/product-create";
    }


    @RequestMapping(value = "/save_product", method = RequestMethod.POST)
    private ResponseEntity<?> saveProduct(@RequestBody SanPhamRequest dto) {


        System.out.println("Received SanPhamRequest: " + dto.toString());
        System.out.println("Gia: " + dto.getGia());
        System.out.println("Gia Sale: " + dto.getGiaSale());

        List<ResponseCustom> listResponse = new ArrayList<>();

        String regexName = "^[a-zA-ZÀ-Ỹà-ỹ][a-zA-Z0-9À-Ỹà-ỹ ]{3,50}$";
        Pattern patternName = Pattern.compile(regexName);
        Matcher matcherName = null;

        if (dto.getTen() != null) {
            matcherName = patternName.matcher(dto.getTen());
        }

        SanPham sp = new SanPham();

        List<SanPham> list = sanPhamService.getAllSanPham();
        List<Integer> integerList = new ArrayList<>();
        if (list.size() == 0) {
            sp.setMa("SP1");
        } else {
            for (SanPham sanPham : list) {
                String ma = sanPham.getMa();
                int index = 0;
                if (ma.length() > 2) {
                    index = Integer.parseInt(ma.substring(2));
                }
                integerList.add(index);
            }
            Optional<Integer> maxNumber = integerList.stream().max(Integer::compare);
            maxNumber.ifPresent(integer -> sp.setMa("SP" + (integer + 1)));
        }

        boolean isValid = true;
        sp.setNgayTao(LocalDate.now());
        if (dto.getDanhMuc() == -1) {
            isValid = false;
        }
        if (dto.getTen().isEmpty()) {
            isValid = false;
        }
        if (matcherName != null && !matcherName.matches()) {
            isValid = false;
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("errorFormatName");
            listResponse.add(responseCustom);
        }
        if (dto.getSoLuong() == -1) {
            isValid = false;
        }
        if (dto.getDanhMuc() == null || danhMucRepository.findById(dto.getDanhMuc()).isEmpty()) {
            isValid = false;
        }
        if (dto.getThuongHieu() == null || thuongHieuRepository.findById(dto.getThuongHieu()).isEmpty()) {
            isValid = false;
        }
        if (dto.getCoAo() == null || coAoRepository.findById(dto.getCoAo()).isEmpty()) {
            isValid = false;
        }
        if (dto.getDuoiAo() == null || duoiAoRepository.findById(dto.getDuoiAo()).isEmpty()) {
            isValid = false;
        }
        if (dto.getKieuDang() == null || kieuDangRepository.findById(dto.getKieuDang()).isEmpty()) {
            isValid = false;
        }
        if (dto.getChatLieu() == null || chatLieuRepository.findById(dto.getChatLieu()).isEmpty()) {
            isValid = false;
        }

        if (dto.getGia() == null) {
            isValid = false;
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("errorPriceFormat");
            listResponse.add(responseCustom);
        } else {
            BigDecimal gia = new BigDecimal(String.valueOf(dto.getGia()));
            if (gia.compareTo(new BigDecimal("70000")) < 0) {
                isValid = false;
                ResponseCustom responseCustom = new ResponseCustom();
                responseCustom.setStatusText("failure");
                responseCustom.setMessage("errorPriceLessThan");
                listResponse.add(responseCustom);
            }

            if (dto.getGiaSale() != null) {
                BigDecimal giaSale = new BigDecimal(String.valueOf(dto.getGiaSale()));
                if (giaSale.compareTo(gia) >= 0) {
                    isValid = false;
                    ResponseCustom responseCustom = new ResponseCustom();
                    responseCustom.setStatusText("failure");
                    responseCustom.setMessage("errorFormatSalePrice");
                    listResponse.add(responseCustom);
                }
            }
        }

        if (isValid) {
            try {
                sp.setTen(dto.getTen());
                sp.setSoLuong(dto.getSoLuong());
                sp.setGia(dto.getGia());
                sp.setGiaSale(dto.getGiaSale());
                sp.setMoTa(dto.getMoTa());
                sp.setTrangThai(dto.getTrangThai());
                sp.setIdDanhMuc(danhMucRepository.findById(dto.getDanhMuc()).get());
                sp.setIdThuongHieu(thuongHieuRepository.findById(dto.getThuongHieu()).get());
                sp.setIdCoAo(coAoRepository.findById(dto.getCoAo()).get());
                sp.setIdDuoiAo(duoiAoRepository.findById(dto.getDuoiAo()).get());
                sp.setIdKieuDang(kieuDangRepository.findById(dto.getKieuDang()).get());
                sp.setIdChatLieu(chatLieuRepository.findById(dto.getChatLieu()).get());
                sp.setDuongDan(dto.getDuongDan());
                sanPhamService.save(sp);
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.ok(listResponse);
        }
    }






}
