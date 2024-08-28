package com.example.manstore.controller.admin;

import com.example.manstore.CustomModel.ResponseCustom;
import com.example.manstore.CustomModel.ResponseMessage;
import com.example.manstore.entity.*;
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

//    @GetMapping("/calculate/{id}")
//    private ResponseEntity<?> calculate(@PathVariable("id") String id,
//                                        @RequestParam(value = "shipping_fee") String shippingFee,
//                                        @RequestParam(value = "shipping_fee_before") String shippingFee_Before) {
//        if (serviceInvoice.findById(Integer.parseInt(id)).isPresent()) {
//            HoaDon dh = serviceInvoice.findById(Integer.parseInt(id)).get();
//            String regex = "^(?:[1-9]\\d{3,5}|1000000|0)$";
//            if (!shippingFee.matches(regex)) {
//                return new ResponseEntity<>("failure", HttpStatus.OK);
//            }
//            BigDecimal ship = new BigDecimal(shippingFee);
//            dh.setPhiVanChuyen(ship);
//            if (shippingFee_Before.equalsIgnoreCase("null")) {
//                dh.setTongTien(dh.getTongTien().add(ship));
//            } else {
//                BigDecimal shippingFeeBefore = new BigDecimal(shippingFee_Before);
//                BigDecimal total = dh.getTongTien().add(ship);
//                dh.setTongTien(total.subtract(shippingFeeBefore));
//            }
//            donHangService.save(dh);
//            return new ResponseEntity<>("success", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("failure", HttpStatus.OK);
//    }

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

//    @GetMapping("/change-status/{id}")
//    private ResponseEntity<?> confirmOrder(@PathVariable("id") String id,
//                                           @RequestParam("status") int status,
//                                           @RequestParam("idStaff") Integer idStaff,
//                                           @RequestParam(value = "reason", required = false) String reason) {
//        NhanVien nv = nhanVienService.findById(idStaff).isPresent() ? nhanVienService.findById(idStaff).get() : null;
//        ResponseCustom responseCustom = new ResponseCustom();
//        List<ResponseMessage> listMessage = new ArrayList<>();
//        HoaDon dh = donHangService.findById(Integer.parseInt(id)).get();
//        ThongBao thongBao = new ThongBao();
//        List<ChiTietHoaDon> list = donHangCTService.findByIdHD(id);
//        if (status == dh.getTrangThai()) {
//            responseCustom.setStatusText("failure");
//            responseCustom.setMessage("The status has been switched before");
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 1 && status == 2) {
//            for (ChiTietHoaDon dhct : list
//            ) {
//                for (ChiTietSanPham spct : spService.getAllCTSP()) {
//                    if (spct.getId() == dhct.getIdChiTietSanPham().getId()) {
//                        if (dhct.getSoLuong() > spct.getSoluong() || spct.getSoluong() <= 0) {
//                            ResponseMessage response = new ResponseMessage();
//                            response.setTen(spct.getIdSanPham().getTen());
//                            response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
//                            response.setSl_ton(spct.getSoluong() + "");
//                            response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
//                            listMessage.add(response);
//                        }
//                    }
//                }
//            }
//            if (listMessage.size() > 0) {
//                return new ResponseEntity<>(listMessage, HttpStatus.OK);
//            }
//            for (ChiTietHoaDon dhct : list
//            ) {
//                int count_of_product = dhct.getIdChiTietSanPham().getSoluong();
//                int count_of_invoice = dhct.getSoLuong();
//                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
//                spct.setSoluong(count_of_product - count_of_invoice);
//                spService.save(spct);
//            }
//            dh.setIdNhanVien(nv);
//            dh.setTrangThai(status);
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);
//            thongBaoService.save(thongBao);
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 2 && status == 3) {
//            if (dh.getPhiVanChuyen() == null) {
//                responseCustom.setStatusText("failure");
//                responseCustom.setMessage("Shipping fee is null");
//                return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//            }
//            dh.setTrangThai(status);
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);
//            thongBaoService.save(thongBao);
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 3 && status == 4) {
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);;
//            thongBaoService.save(thongBao);
//            dh.setTrangThai(status);
//            dh.setTongTien(donHangService.calculateTotal(id));
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 3 && status == 5) {
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);
//            thongBao.setNoiDung(reason);
//            thongBaoService.save(thongBao);
//            dh.setTrangThai(status);
//            dh.setGhiChu(reason);
//            for (ChiTietHoaDon dhct : list
//            ) {
//                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
//                spct.setSoluong(spct.getSoluong() + dhct.getSoLuong());
//                spService.save(spct);
//            }
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 1 && status == 6) {
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);
//            thongBao.setNoiDung(reason);
//            thongBaoService.save(thongBao);
//            dh.setTrangThai(status);
//            dh.setGhiChu(reason);
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        if (dh.getTrangThai() == 2 && status == 6) {
//            thongBao.setIdKhachHang(dh.getIdKhachHang());
//            thongBao.setTrangThaiDonHang(status);
//            thongBao.setIdNhanVien(nv);
//            thongBao.setNgayGui(LocalDateTime.now());
//            thongBao.setIdHoaDon(dh);
//            thongBao.setNoiDung(reason);
//            thongBaoService.save(thongBao);
//            dh.setTrangThai(status);
//            dh.setGhiChu(reason);
//            for (ChiTietHoaDon dhct : list
//            ) {
//                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
//                spct.setSoluong(spct.getSoluong() + dhct.getSoLuong());
//                spService.save(spct);
//            }
//            responseCustom.setStatusText("success");
//            responseCustom.setMessage("success");
//            donHangService.save(dh);
//            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//        }
//        responseCustom.setStatusText("failure");
//        responseCustom.setMessage("Cannot change status");
//        return new ResponseEntity<>(responseCustom, HttpStatus.OK);
//    }

    @GetMapping("/edit-quantity/{id}")
    private ResponseEntity<?> editQuantity(@PathVariable("id") String id,
                                           @RequestParam("quantity") int count) {

        ChiTietHoaDon dhct = donHangCTService.getById2(id);
        ChiTietSanPham spct = dhct.getIdChiTietSanPham();
        HoaDon dh = dhct.getIdHoaDon();
        if (count < 1) {
            return new ResponseEntity<>("Số lượng phải lớn hơn 0!", HttpStatus.OK);
        } else {
            if (dhct.getIdHoaDon().getTrangThai() == 1) {
                if (count <= spct.getSoluong()) {
                    int quantity = dhct.getSoLuong() - count;
                    dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(quantity))));
                    if (dh.getIdDotGiamGia() != null) {
                        int total = dh.getTongTien().intValue();
                        System.out.println("Total " + total);
                        if (total < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                            dh.setIdDotGiamGia(null);
                        }

                    }
                    donHangService.save(dh);
                    dhct.setSoLuong(count);
                    donHangCTService.save(dhct);
                    return new ResponseEntity<>("success", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Sản phẩm này chỉ còn lại " + spct.getSoluong() + "!", HttpStatus.OK);
                }
            } else if (dhct.getIdHoaDon().getTrangThai() == 2) {
                if (count <= (dhct.getSoLuong() + spct.getSoluong())) {
                    int afterCount;
                    if (count <= dhct.getSoLuong()) {
                        if (count <= spct.getSoluong()) {
                            afterCount = spct.getSoluong() + (dhct.getSoLuong() - count);
                            spct.setSoluong(afterCount);
                            spService.save(spct);
                            int quantity = dhct.getSoLuong() - count;
                            dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(quantity))));
                            if (dh.getIdDotGiamGia() != null) {
                                int total = dh.getTongTien().intValue();
                                System.out.println("Total " + total);
                                if (total < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                                    dh.setIdDotGiamGia(null);
                                }
                            }
                            donHangService.save(dh);
                            dhct.setSoLuong(count);
                            donHangCTService.save(dhct);
                            return new ResponseEntity<>("success", HttpStatus.OK);
                        } else {
                            afterCount = spct.getSoluong() + (dhct.getSoLuong() - count);
                            spct.setSoluong(afterCount);
                            spService.save(spct);
                            int quantity = dhct.getSoLuong() - count;
                            dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(quantity))));
                            if (dh.getIdDotGiamGia() != null) {
                                int total = dh.getTongTien().intValue();
                                System.out.println("Total " + total);
                                if (total  < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                                    dh.setIdDotGiamGia(null);
                                }
                            }
                            donHangService.save(dh);
                            dhct.setSoLuong(count);
                            donHangCTService.save(dhct);
                            return new ResponseEntity<>("success", HttpStatus.OK);
                        }
                    } else {
                        if (count <= (dhct.getSoLuong() + spct.getSoluong())) {
                            if (count <= dhct.getSoLuong()) {
                                System.out.println("case 2 " + count + " " + spct.getSoluong() + (dhct.getSoLuong() - count));
                                afterCount = spct.getSoluong() + (dhct.getSoLuong() - count);
                                spct.setSoluong(afterCount);
                                spService.save(spct);
                                int quantity = dhct.getSoLuong() - count;
                                dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(quantity))));
                                if (dh.getIdDotGiamGia() != null) {
                                    int total = dh.getTongTien().intValue();
                                    System.out.println("Total " + total);
                                    if (total < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                                        dh.setIdDotGiamGia(null);
                                    }
                                }
                                donHangService.save(dh);
                                dhct.setSoLuong(count);
                                donHangCTService.save(dhct);
                                return new ResponseEntity<>("success", HttpStatus.OK);
                            } else {
                                System.out.println("case 3 " + count + " " + (spct.getSoluong() - (count - dhct.getSoLuong())));
                                afterCount = spct.getSoluong() - (count - dhct.getSoLuong());
                                spct.setSoluong(afterCount);
                                int quantity = count - dhct.getSoLuong();
                                dh.setTongTien(dh.getTongTien().add(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(quantity))));
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
                                donHangService.save(dh);
                                dhct.setSoLuong(count);
                                donHangCTService.save(dhct);
                                return new ResponseEntity<>("success", HttpStatus.OK);
                            }
                        } else {
                            return new ResponseEntity<>("Sản Phẩm Chỉ Còn Lại " +
                                    dhct.getSoLuong() + spct.getSoluong() + "!", HttpStatus.OK);
                        }
                    }
                } else {
                    return new ResponseEntity<>("Sản Phẩm Chỉ Còn Lại " +
                            (dhct.getSoLuong() + spct.getSoluong()) + "!", HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>("Không thể thay đổi số lượng sản phẩm cho đơn hàng này!", HttpStatus.OK);
            }
        }
    }

    @GetMapping("/delete-invoice-detail/{id}")
    private ResponseEntity<?> deleteInvoiceDetailById(@PathVariable("id") String id) {
        if (donHangCTService.getById2(id) != null) {
            ChiTietHoaDon dhct = donHangCTService.getById2(id);
            HoaDon dh = dhct.getIdHoaDon();
            ChiTietSanPham spct = dhct.getIdChiTietSanPham();
            List<ChiTietHoaDon> list = donHangCTService.findByIdHD(String.valueOf(dh.getId()));
            if (dhct.getIdHoaDon().getTrangThai() == 1 || dhct.getIdHoaDon().getTrangThai() == 2) {
                if (dhct.getIdHoaDon().getTrangThai() == 1) {
                    if (list.size() <= 1) {
                        return new ResponseEntity<>("error", HttpStatus.OK);
                    }
                    if (dh.getIdDotGiamGia() != null) {
                        dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(dhct.getSoLuong()))));
                        int total = dh.getTongTien().intValue();
                        System.out.println("Total " + total);
                        if (total < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                            dh.setIdDotGiamGia(null);
                        }

                    }
                    BigDecimal total_before = dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(dhct.getSoLuong())));
                    dh.setTongTien(total_before);
                    donHangService.save(dh);
                    donHangCTService.deleteById(id);
                } else if (dhct.getIdHoaDon().getTrangThai() == 2) {
                    if (list.size() <= 1) {
                        return new ResponseEntity<>("error", HttpStatus.OK);
                    }
                    if (dh.getIdDotGiamGia() != null) {
                        dh.setTongTien(dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(dhct.getSoLuong()))));
                        int total = dh.getTongTien().intValue();
                        System.out.println("Total " + total);
                        if (total < dh.getIdDotGiamGia().getGiaTriDonHang()) {
                            dh.setIdDotGiamGia(null);
                        }

                    }
                    BigDecimal total_before = dh.getTongTien().subtract(dhct.getGiaThoiDiemMua().multiply(new BigDecimal(dhct.getSoLuong())));
                    dh.setTongTien(total_before);
                    donHangService.save(dh);
                    donHangCTService.deleteById(id);
                    spct.setSoluong(spct.getSoluong() + dhct.getSoLuong());
                    spService.save(spct);
                }
            } else {
                return new ResponseEntity<>("no-status", HttpStatus.OK);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", HttpStatus.OK);
        }
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

            // Giữ nguyên tổng tiền mà không thay đổi
            if (!shippingFee_Before.equalsIgnoreCase("null")) {
                BigDecimal shippingFeeBefore = new BigDecimal(shippingFee_Before);
                BigDecimal total = dh.getTongTien();
                dh.setTongTien(total.add(ship).subtract(shippingFeeBefore));
            }

            donHangService.save(dh);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("failure", HttpStatus.OK);
    }

    @GetMapping("/change-status/{id}")
    private ResponseEntity<?> confirmOrder(@PathVariable("id") String id,
                                           @RequestParam("status") int status,
                                           @RequestParam("idStaff") Integer idStaff,
                                           @RequestParam(value = "reason", required = false) String reason) {
        NhanVien nv = nhanVienService.findById(idStaff).isPresent() ? nhanVienService.findById(idStaff).get() : null;
        ResponseCustom responseCustom = new ResponseCustom();
        List<ResponseMessage> listMessage = new ArrayList<>();
        HoaDon dh = donHangService.findById(Integer.parseInt(id)).get();
        ThongBao thongBao = new ThongBao();
        List<ChiTietHoaDon> list = donHangCTService.findByIdHD(id);

        if (status == dh.getTrangThai()) {
            responseCustom.setStatusText("failure");
            responseCustom.setMessage("The status has been switched before");
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 1 && status == 2) {
            // Kiểm tra số lượng hàng hóa
            for (ChiTietHoaDon dhct : list) {
                for (ChiTietSanPham spct : spService.getAllCTSP()) {
                    if (spct.getId() == dhct.getIdChiTietSanPham().getId()) {
                        if (dhct.getSoLuong() > spct.getSoluong() || spct.getSoluong() <= 0) {
                            ResponseMessage response = new ResponseMessage();
                            response.setTen(spct.getIdSanPham().getTen());
                            response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                            response.setSl_ton(spct.getSoluong() + "");
                            response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                            listMessage.add(response);
                        }
                    }
                }
            }
            if (listMessage.size() > 0) {
                return new ResponseEntity<>(listMessage, HttpStatus.OK);
            }
            for (ChiTietHoaDon dhct : list) {
                int count_of_product = dhct.getIdChiTietSanPham().getSoluong();
                int count_of_invoice = dhct.getSoLuong();
                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
                spct.setSoluong(count_of_product - count_of_invoice);
                spService.save(spct);
            }
            dh.setIdNhanVien(nv);
            dh.setTrangThai(status);
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBaoService.save(thongBao);
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 2 && status == 3) {
            if (dh.getPhiVanChuyen() == null) {
                responseCustom.setStatusText("failure");
                responseCustom.setMessage("Shipping fee is null");
                return new ResponseEntity<>(responseCustom, HttpStatus.OK);
            }
            dh.setTrangThai(status);
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBaoService.save(thongBao);
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 3 && status == 4) {
            dh.setTongTien(donHangService.calculateTotal(id)); // Cập nhật tổng tiền ở đây
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBaoService.save(thongBao);
            dh.setTrangThai(status);
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 3 && status == 5) {
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBao.setNoiDung(reason);
            thongBaoService.save(thongBao);
            dh.setTrangThai(status);
            dh.setGhiChu(reason);
            for (ChiTietHoaDon dhct : list) {
                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
                spct.setSoluong(spct.getSoluong() + dhct.getSoLuong());
                spService.save(spct);
            }
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 1 && status == 6) {
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBao.setNoiDung(reason);
            thongBaoService.save(thongBao);
            dh.setTrangThai(status);
            dh.setGhiChu(reason);
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        if (dh.getTrangThai() == 2 && status == 6) {
            thongBao.setIdKhachHang(dh.getIdKhachHang());
            thongBao.setTrangThaiDonHang(status);
            thongBao.setIdNhanVien(nv);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setIdHoaDon(dh);
            thongBao.setNoiDung(reason);
            thongBaoService.save(thongBao);
            dh.setTrangThai(status);
            dh.setGhiChu(reason);
            for (ChiTietHoaDon dhct : list) {
                ChiTietSanPham spct = dhct.getIdChiTietSanPham();
                spct.setSoluong(spct.getSoluong() + dhct.getSoLuong());
                spService.save(spct);
            }
            responseCustom.setStatusText("success");
            responseCustom.setMessage("success");
            donHangService.save(dh);
            return new ResponseEntity<>(responseCustom, HttpStatus.OK);
        }

        responseCustom.setStatusText("failure");
        responseCustom.setMessage("Cannot change status");
        return new ResponseEntity<>(responseCustom, HttpStatus.OK);
    }

}
