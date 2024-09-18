package com.example.manstore.controller.admin;


import com.example.manstore.dto.custom.ResponseCustom;
import com.example.manstore.dto.request.ChiTietSanPhamRequest;
import com.example.manstore.dto.request.ChiTietSanPhamValidationRequest;
import com.example.manstore.dto.request.SanPhamRequest;
import com.example.manstore.dto.respone.SanPhanResponse;
import com.example.manstore.entity.*;
import com.example.manstore.repository.*;
import com.example.manstore.service.Impl.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/product")
public class SanPhamController {

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiTietSanPhamImpl chiTietSanPhamService;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private MauSacServiceImpl mauSacService;

    @Autowired
    private SizeServiceImpl sizeService;

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


    @GetMapping("/color/getAll")
    @ResponseBody
    public ResponseEntity<List<MauSac>> getAllColor() {
        List<MauSac> colors = mauSacService.getAllMauSac();
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    @GetMapping("/size/getAll")
    @ResponseBody
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = sizeService.getAllSize();
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }


    //    @GetMapping("/list")
//    @ResponseBody
//    public ResponseEntity<?> getAllSP(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllSP(pageable);
//
//        return new ResponseEntity<>(pageResult, HttpStatus.OK);
//    }
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getAllSP(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "ngayTao");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllSP(pageable);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @GetMapping(value = "/page/search/{pageNumber}/{keyWord}")
    public ResponseEntity<?> searchSPByNameOrCode(
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("keyWord") String keyWord) {
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("id").descending());
        Page<SanPham> page;

        if (keyWord.equalsIgnoreCase("null")) {
            page = sanPhamService.pageOfSP(pageable);
        } else {
            page = sanPhamService.SearchSPByNameOrCode(keyWord, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    @ResponseBody
    public ResponseEntity<?> sorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ngayTao,desc") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllSP(pageable);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/status")
    @ResponseBody
    public ResponseEntity<?> getProductsByStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam int trangThai) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllByTrangThai(trangThai, pageable);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/categoryFilter")
    @ResponseBody
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam int idDanhMuc) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllByDanhMuc(idDanhMuc, pageable);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/trademarkFilter")
    @ResponseBody
    public ResponseEntity<?> getProductsByTrademark(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam int idThuongHieu) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhanResponse> pageResult = sanPhamRepository.findAllByThuongHieu(idThuongHieu, pageable);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    private String viewProductDetail() {
        return "admin/products/product-detailed";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> detailProduct(@PathVariable("id") Integer id) {
        Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(id);

        if (sanPhamOpt.isPresent()) {
            return ResponseEntity.ok().body(sanPhamOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
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
            if (gia.compareTo(BigDecimal.ZERO) < 0) {
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
                sp.setGia(dto.getGia());
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


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    private ResponseEntity<?> updateProduct(@RequestBody SanPhamRequest dto, @PathVariable("id") String id) {

        if (id == null || id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID cannot be null or empty");
        }

        String regexName = "^[a-zA-ZÀ-Ỹà-ỹ][a-zA-Z0-9À-Ỹà-ỹ ]{3,50}$";
        Pattern patternName = Pattern.compile(regexName);

        List<ResponseCustom> listResponse = new ArrayList<>();
        Optional<SanPham> optionalSanPham = sanPhamService.getSanPhamById(Integer.parseInt(id));
        if (!optionalSanPham.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        SanPham sp = optionalSanPham.get();
        boolean isValid = true;

        List<SanPham> getAllProduct = sanPhamService.getAllSanPham();
        getAllProduct.remove(sp);

        for (SanPham product : getAllProduct) {
            if (product.getTen().equals(dto.getTen())) {
                isValid = false;
                ResponseCustom response = new ResponseCustom();
                response.setMessage("errorDuplicateName");
                response.setStatusText("failure");
                listResponse.add(response);
                break;
            }
        }

        if (dto.getTen() == null || dto.getTen().isEmpty()) {
            isValid = false;
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("errorEmptyName");
            listResponse.add(responseCustom);
        } else {
            Matcher matcherName = patternName.matcher(dto.getTen());
            if (!matcherName.matches()) {
                isValid = false;
                ResponseCustom responseCustom = new ResponseCustom();
                responseCustom.setStatusText("failure");
                responseCustom.setMessage("errorFormatName");
                listResponse.add(responseCustom);
            }
        }

        if (dto.getGia() == null) {
            isValid = false;
            ResponseCustom responseCustom = new ResponseCustom();
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("errorPriceFormat");
            listResponse.add(responseCustom);
        } else {
            BigDecimal gia = new BigDecimal(String.valueOf(dto.getGia()));
            if (gia.compareTo(BigDecimal.ZERO) < 0) {
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
                sp.setGia(dto.getGia());
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


    @GetMapping("/product_detail/{id}/{pageNumber}")
    public ResponseEntity<?> getProductDetail(@PathVariable("id") String id,
                                              @PathVariable("pageNumber") int pageNumber,
                                              @RequestParam(value = "color", required = false, defaultValue = "0") String color,
                                              @RequestParam(value = "size", required = false, defaultValue = "0") String size) {
        Page<ChiTietSanPham> page = chiTietSanPhamService.Filter(pageNumber, color, size, id);
        return ResponseEntity.ok().body(page);
    }

    @RequestMapping(value = "/save_product_detail/validation/{id}", method = RequestMethod.POST)
    private ResponseEntity<?> validationProductDetail(@RequestBody() List<ChiTietSanPhamRequest> listSPCT, @PathVariable("id") String id) {

        List<ChiTietSanPhamValidationRequest> arrayProductValidation = new ArrayList<>();

        if (sanPhamService.getSanPhamById(Integer.parseInt(id)).isPresent()) {
            for (ChiTietSanPhamRequest dto : listSPCT) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                SanPham sp = sanPhamService.getSanPhamById(Integer.parseInt(id)).get();
                chiTietSanPham.setIdSanPham(sp);
                chiTietSanPham.setNgayTao(LocalDate.now());
                chiTietSanPham.setSoluong(Integer.parseInt(dto.getSoluong()));
                chiTietSanPham.setIdMauSac(mauSacService.getMauSacById(Integer.parseInt(dto.getMauSac())));
                chiTietSanPham.setIdSize(sizeService.getSizeById(Integer.parseInt(dto.getSize())));
                chiTietSanPham.setDuongDan(dto.getDuongDan());
                chiTietSanPham.setTrangThai(dto.getTrangThai());

                //Tạo 1 object để trả về lỗi cho giao diện xử lý
                ChiTietSanPhamValidationRequest productValidation = new ChiTietSanPhamValidationRequest();
                productValidation.setMauSac(dto.getMauSac());
                productValidation.setSize(dto.getSize());
                productValidation.setSoluong(dto.getSoluong());
                productValidation.setDuongDan(dto.getDuongDan());
                productValidation.setTrangThai(dto.getTrangThai());

                for (ChiTietSanPham ctspLoopFor : chiTietSanPhamService.getListCTSPById(id)) {
                    boolean isValidMauSac = ctspLoopFor.getIdMauSac().getId() == chiTietSanPham.getIdMauSac().getId();
                    boolean isValidSize = ctspLoopFor.getIdSize().getId() == chiTietSanPham.getIdSize().getId();
                    if (isValidMauSac && isValidSize) {
                        productValidation.setValid(true);
                        arrayProductValidation.add(productValidation);
                        break;
                    } else {
                        productValidation.setValid(false);
                        arrayProductValidation.add(productValidation);
                    }
                }
            }
        } else {
            return ResponseEntity.ok("failure");
        }
        List<ChiTietSanPhamValidationRequest> arrayProductValidationWithOutDuplicate = arrayProductValidation.stream().distinct().collect(Collectors.toList());
        System.out.println(arrayProductValidationWithOutDuplicate.toString());
        return ResponseEntity.ok(arrayProductValidationWithOutDuplicate);
    }

    @RequestMapping(value = "/save_product_detail/save/{id}", method = RequestMethod.POST)
    private ResponseEntity<?> saveProductDetail(@RequestBody() List<ChiTietSanPhamValidationRequest> listSPCT
            , @PathVariable("id") String id) {
        if (sanPhamService.getSanPhamById(Integer.parseInt(id)).isPresent()) {
            for (ChiTietSanPhamValidationRequest dto : listSPCT) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                SanPham sp = sanPhamService.getSanPhamById(Integer.parseInt(id)).get();
                chiTietSanPham.setIdSanPham(sp);
                chiTietSanPham.setNgayTao(LocalDate.now());
                chiTietSanPham.setSoluong(Integer.parseInt(dto.getSoluong()));
                chiTietSanPham.setIdMauSac(mauSacService.getMauSacById(Integer.parseInt(dto.getMauSac())));
                chiTietSanPham.setIdSize(sizeService.getSizeById(Integer.parseInt(dto.getSize())));
                chiTietSanPham.setDuongDan(dto.getDuongDan());
                chiTietSanPham.setTrangThai(dto.getTrangThai());
                chiTietSanPhamService.save(chiTietSanPham);
            }
        }
        return ResponseEntity.ok("success");
    }


}
