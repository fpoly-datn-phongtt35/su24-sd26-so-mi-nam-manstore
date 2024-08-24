package com.example.manstore.controller.admin;

import com.example.manstore.entity.ChiTietHoaDon;
import com.example.manstore.entity.ChiTietSanPham;
import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.HoaDonChiTietService;
import com.example.manstore.service.HoaDonService;
import com.example.manstore.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/invoice")
public class RestControllerHoaDon {
    @Autowired
    HoaDonServiceImpl serviceInvoice;

    @Autowired
    HoaDonChiTietServiceImpl serviceDetailInvoce;

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @Autowired
    private ChiTietSanPhamImpl spService;

    @Autowired
    private HoaDonService donHangService;

    @Autowired
    private HoaDonChiTietService donHangCTService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @Autowired
    private DotGiamGiaServiceImpl dotGiamGiaService;

    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;

    @GetMapping("/index/{pageNumber}")
    private ResponseEntity<?> index(@PathVariable("pageNumber") int pageNumber,
                                    @RequestParam(value = "status", required = false) String status,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate
    ) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("id").descending());
        Page<HoaDon> page = serviceInvoice.page(pageable);
        if (status != null && keyword == null && startDate == null & endDate == null) {
            page = serviceInvoice.filterByStatus(pageable, Integer.parseInt(status));
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword != null && startDate == null & endDate == null) {
            page = serviceInvoice.search(pageable, keyword);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword != null && startDate == null & endDate == null) {
            page = serviceInvoice.searchAndFilter(pageable, keyword, Integer.parseInt(status));
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword == null && startDate != null & endDate != null) {
            page = serviceInvoice.filterByDate(pageable, startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword == null && startDate != null & endDate != null) {
            page = serviceInvoice.filterByAll(pageable, Integer.parseInt(status), startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status == null && keyword != null && startDate != null & endDate != null) {
            page = serviceInvoice.searchAndFilterByDate(pageable, keyword, startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        if (status != null && keyword != null && startDate != null & endDate != null) {
            page = serviceInvoice.searchAndFilterByAll(pageable, keyword, Integer.parseInt(status), startDate, endDate);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/invoice-detail/{id}")
    private ResponseEntity<?> getInvoiceDetailById(@PathVariable("id") String id) {

        List<ChiTietHoaDon> listInvoiceDetail = serviceDetailInvoce.findByIdHD(id);

        return new ResponseEntity<>(listInvoiceDetail, HttpStatus.OK);
    }

    @GetMapping("/calculate/{id}")
    private ResponseEntity<?> calculate(@PathVariable("id") String id,
                                        @RequestParam(value = "shipping_fee") String shippingFee,
                                        @RequestParam(value = "shipping_fee_before") String shippingFee_Before) {
        if (serviceInvoice.findById(Integer.parseInt(id)).isPresent()) {
            HoaDon dh = serviceInvoice.findById(Integer.parseInt(id)).get();
            String regex = "^(?:[1-9]\\d{3,5}|1000000|0)$";
            if (!shippingFee.matches(regex)) {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
            BigDecimal ship = new BigDecimal(shippingFee);
            dh.setPhiVanChuyen(ship);
            if (shippingFee_Before.equalsIgnoreCase("null")) {
                dh.setTongTien(dh.getTongTien().add(ship));
            } else {
                BigDecimal shippingFeeBefore = new BigDecimal(shippingFee_Before);
                BigDecimal total = dh.getTongTien().add(ship);
                dh.setTongTien(total.subtract(shippingFeeBefore));
            }
            donHangService.save(dh);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("failure", HttpStatus.OK);
    }

    @GetMapping("/add-quantity/{id}")
    private ResponseEntity<?> addQuantity(@PathVariable("id") String id, @RequestParam(value = "promotion", required = false) Integer promotion) {
        ChiTietHoaDon dhct = donHangCTService.getById2(id);
        ChiTietSanPham spct = dhct.getIdChiTietSanPham();
        System.out.println("Promotion " + promotion);
        DotGiamGia km = null;
        if (promotion != null) {
            km = dotGiamGiaService.findById(promotion).isPresent()
                    ? dotGiamGiaService.findById(promotion).get() : null;
            System.out.println(km != null ? km.toString() : "not exist promotion");
        }

        HoaDon dh = dhct.getIdHoaDon();
        if (dhct.getIdHoaDon().getTrangThai() == 1) {
            if (spct.getSoluong() >= 1) {
                if (dhct.getSoLuong() < spct.getSoluong()) {
                    dhct.setSoLuong(dhct.getSoLuong() + 1);
                    donHangCTService.save(dhct);
                    dh.setTongTien(dh.getTongTien().add(dhct.getGiaThoiDiemMua()));
                    if (km != null) {
                        int total = dh.getTongTien().intValue();
                        System.out.println("Total " + total);
                        if (total >= km.getGiaTriDonHang()) {
                            dh.setIdDotGiamGia(km);
                        }
                    }
                    donHangService.save(dh);
                    return new ResponseEntity<>("success", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("out of quantity", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
        } else if (dhct.getIdHoaDon().getTrangThai() == 2) {
            if (spct.getSoluong() >= 1) {
                dhct.setSoLuong(dhct.getSoLuong() + 1);
                donHangCTService.save(dhct);
                spct.setSoluong(spct.getSoluong() - 1);
                spService.save(spct);
                dh.setTongTien(dh.getTongTien().add(dhct.getGiaThoiDiemMua()));
                if (km != null) {
                    int total = dh.getTongTien().intValue();
                    System.out.println("Total " + total);
                    if (total >= km.getGiaTriDonHang()) {
                        dh.setIdDotGiamGia(km);
                    }
                }
                donHangService.save(dh);
                return new ResponseEntity<>("success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("no-status", HttpStatus.OK);
        }
    }


    @GetMapping("/minus-quantity/{id}")
    private ResponseEntity<?> minusQuantity(@PathVariable("id") String id, @RequestParam(value = "promotion", required = false) Integer promotion) {
        ChiTietHoaDon dhct = donHangCTService.getById2(id);
        if (dhct == null) {
            return new ResponseEntity<>("not exist", HttpStatus.OK);
        }
        ChiTietSanPham spct = dhct.getIdChiTietSanPham();
        HoaDon dh = dhct.getIdHoaDon();
        List<ChiTietHoaDon> list = donHangCTService.findByIdHD(String.valueOf(dh.getId()));
        System.out.println("Promotion " + promotion);
        DotGiamGia km = null;
        if (promotion != null) {
            km = dotGiamGiaService.findById(promotion).isPresent()
                    ? dotGiamGiaService.findById(promotion).get() : null;
            System.out.println(km != null ? km.toString() : "not exist promotion");
        }
        if (dhct.getSoLuong() == 1) {
            if (dhct.getIdHoaDon().getTrangThai() == 1 || dhct.getIdHoaDon().getTrangThai() == 2) {
                if (list.size() <= 1) {
                    return new ResponseEntity<>("cannot be deleted", HttpStatus.OK);
                }
                donHangCTService.deleteById(String.valueOf(dhct.getId()));
                if (dhct.getIdHoaDon().getTrangThai() == 2) {
                    spct.setSoluong(spct.getSoluong() + 1);
                    spService.save(spct);
                }
                dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua()));
                if (km != null) {
                    int total = dh.getTongTien().intValue();
                    System.out.println("Total " + total);
                    if (total < km.getGiaTriDonHang()) {
                        dh.setIdDotGiamGia(null);
                    }
                }
                donHangService.save(dh);
                return new ResponseEntity<>("deletion all success", HttpStatus.OK);
            }
        }
        if (dhct.getIdHoaDon().getTrangThai() == 1 || dhct.getIdHoaDon().getTrangThai() == 2) {
            dhct.setSoLuong(dhct.getSoLuong() - 1);
            donHangCTService.save(dhct);
            if (dhct.getIdHoaDon().getTrangThai() == 2) {
                spct.setSoluong(spct.getSoluong() + 1);
                spService.save(spct);
            }
            dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua()));
            if (km != null) {
                int total = dh.getTongTien().intValue();
                System.out.println("Total " + total);
                if (total < km.getGiaTriDonHang()) {
                    dh.setIdDotGiamGia(null);
                }
            }
            donHangService.save(dh);
        } else {
            return new ResponseEntity<>("no-status", HttpStatus.OK);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/product-detail/pagination/{page}")
    public ResponseEntity<?> searchAndFilterProductDetail(@PathVariable(value = "page") int page,
                                                          @RequestParam(value = "keyword", required = false) String keyword,
                                                          @RequestParam(value = "color", required = false) String color,
                                                          @RequestParam(value = "size", required = false) String size) {
        Page<ChiTietSanPham> result = spService.searchAndFilter(page, keyword, color, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add-to-invoice-detail/{id_invoice}/{id_product}")
    private ResponseEntity<?> addInvoiceDetailById(@PathVariable("id_invoice") String idInvoice,
                                                   @PathVariable("id_product") String idProduct) {
        if (donHangService.findById(Integer.parseInt(idInvoice)).isPresent()) {
            HoaDon dh = donHangService.findById(Integer.parseInt(idInvoice)).get();
            ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(idProduct));
            ChiTietHoaDon dhct = new ChiTietHoaDon();
            List<ChiTietHoaDon> list = donHangCTService.findByIdHD(idInvoice);
            if (spct.getSoluong() == 0) {
                return new ResponseEntity<>("quantity-zero", HttpStatus.OK);
            }
            for (ChiTietHoaDon item : list
            ) {
                if (item.getIdChiTietSanPham().getId() == spct.getId()) {
                    return new ResponseEntity<>("duplicate", HttpStatus.OK);
                }
            }
            dhct.setIdChiTietSanPham(spct);
            dhct.setSoLuong(1);
            dhct.setNgayTao(LocalDateTime.now());
            dhct.setGiaThoiDiemMua(spct.getIdSanPham().getGia());
            dhct.setDonGia(spct.getIdSanPham().getGia());
            dhct.setTongTien(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(1)));
            dhct.setIdHoaDon(dh);
            dh.setTongTien(dh.getTongTien().add(dhct.getGiaThoiDiemMua()));
//            if (dh.getIdDotGiamGia() == null) {
//                List<DotGiamGia> List = dotGiamGiaRepository.getByCustomer(dh.getIdKhachHang().getId(), LocalDate.now(), true);
//                List.removeIf(km -> km.getNgayKetThuc().isBefore(LocalDate.now()));
//                System.out.println("List " + List.toString());
//                int total = dh.getTongTien().intValue();
//                if (List.size() == 0) {
//                    List<DotGiamGia> listPromotionAll = dotGiamGiaRepository.getPromotionAll(LocalDate.now(), true);
//                    listPromotionAll.removeIf(km -> km.getNgayKetThuc().isBefore(LocalDate.now()));
//                    System.out.println("List " + listPromotionAll.toString());
//                    if (listPromotionAll.size() > 0) {
//                        if (total >= listPromotionAll.get(0).getGiaTriDonHang()) {
//                            dh.setIdDotGiamGia(listPromotionAll.get(0));
//                        }
//                    }
//                } else {
//                    if (total >= List.get(0).getGiaTriDonHang()) {
//                        dh.setIdDotGiamGia(List.get(0));
//                    }
//                }
//            }
            if (dh.getIdDotGiamGia() == null) {
                List<DotGiamGia> listPromotionAll = dotGiamGiaRepository.getPromotionAll(LocalDate.now(), true);
                listPromotionAll.removeIf(km -> km.getNgayKetThuc().isBefore(LocalDate.now()));
                System.out.println("List " + listPromotionAll.toString());
                int total = dh.getTongTien().intValue();
                if (listPromotionAll.size() > 0) {
                    if (total >= listPromotionAll.get(0).getGiaTriDonHang()) {
                        dh.setIdDotGiamGia(listPromotionAll.get(0));
                    }
                }
            }

            if (dhct.getIdHoaDon().getTrangThai() == 1 || dhct.getIdHoaDon().getTrangThai() == 2) {
                if (dhct.getIdHoaDon().getTrangThai() == 2) {
                    spct.setSoluong(spct.getSoluong() - 1);
                    spService.save(spct);
                } else {
                    System.out.println("Chờ Xác Nhận!");
                }
            } else {
                return new ResponseEntity<>("no-status", HttpStatus.OK);
            }
            donHangService.save(dh);
            donHangCTService.save(dhct);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", HttpStatus.OK);
        }
    }


}
