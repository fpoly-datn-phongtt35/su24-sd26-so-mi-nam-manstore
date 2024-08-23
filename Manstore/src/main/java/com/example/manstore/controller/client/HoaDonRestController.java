package com.example.manstore.controller.client;

import com.example.manstore.CustomModel.ResponseMessage;
import com.example.manstore.dto.respone.DonHangChiTietDTO;
import com.example.manstore.dto.respone.GioHangChiTietDTO;
import com.example.manstore.dto.respone.SPDTO;
import com.example.manstore.entity.*;
import com.example.manstore.service.*;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.KhachHangServiceImpl;
import com.example.manstore.service.Impl.PhuongThucTTServiceImpl;
import com.example.manstore.service.Impl.ThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/order/")
public class HoaDonRestController {
    @Autowired
    private KhachHangServiceImpl khachHangService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;


    @Autowired
    private PhuongThucTTServiceImpl ptttService;

    @Autowired
    private ChiTietSanPhamImpl spService;

    @Autowired
    private HoaDonService donHangService;

    @Autowired
    private HoaDonChiTietService donHangCTService;

    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private TTVCService ttvcService;

    @Autowired
    private GioHangChiTietService ghService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    // đơn hàng được xác nhận -> chỉnh sửa số lượng sản phẩm
    @PostMapping("/invoice/save/{id}/{idAddress}")
    private ResponseEntity<?> saveInvoice(@RequestBody List<GioHangChiTietDTO> list,
                                          @PathVariable("id") Integer id,
                                          @PathVariable("idAddress") String idAddress,
                                          @RequestParam(value = "idPromotion", required = false) String idPromotion) {
        List<ResponseMessage> listMessage = new ArrayList<>();
        GioHang gioHang = gioHangService.findByIdKH((id));
        List<GioHangChiTiet> listAllGH = ghService.getByIdGHList(String.valueOf(gioHang.getId()));
        if (list.size() == 0) {
            return new ResponseEntity<>("null", HttpStatus.OK);
        } else {
            for (GioHangChiTietDTO gioHangChiTietDTO : list) {
                ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(gioHangChiTietDTO.getIdSanPhamChiTiet()));
                if (spct.getSoluong() <= 0) {
                    for (GioHangChiTiet ghct : listAllGH) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId()) {
                            ghService.delete(String.valueOf(ghct.getId()));
                        }
                    }
                    ResponseMessage response = new ResponseMessage();
                    response.setTen(spct.getIdSanPham().getTen());
                    response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                    response.setSl_ton(spct.getSoluong() + "");
                    response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                    response.setTrangThai(spct.getIdSanPham().getTrangThai() != 0);
                    listMessage.add(response);
                } else if (spct.getIdSanPham().getTrangThai() == 0) {
                    ResponseMessage response = new ResponseMessage();
                    response.setTen(spct.getIdSanPham().getTen());
                    response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                    response.setSl_ton(spct.getSoluong() + "");
                    response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                    response.setTrangThai(false);
                    listMessage.add(response);
                } else {
                    for (GioHangChiTiet ghct : listAllGH) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() && ghct.getSoLuong() > spct.getSoluong()) {
                            ghct.setSoLuong(spct.getSoluong());
                            ghService.save(ghct);
                            ResponseMessage response = new ResponseMessage();
                            response.setTen(spct.getIdSanPham().getTen());
                            response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                            response.setSl_ton(spct.getSoluong() + "");
                            response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                            response.setTrangThai(spct.getIdSanPham().getTrangThai() != 0);
                            listMessage.add(response);
                        }
                    }
                }
            }
        }

        if (listMessage.size() > 0) {
            return new ResponseEntity<>(listMessage, HttpStatus.OK);
        }

        ThongTinVanChuyen ttvc = new ThongTinVanChuyen();
        DiaChi diaChi = diaChiService.getById(idAddress);
        ttvc.setTinhThanhpho(diaChi.getTinhTp());
        ttvc.setQuanHuyen(diaChi.getQuanHuyen());
        ttvc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        ttvc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        ttvc.setSdt(diaChi.getSdt());
        ttvc.setTenNguoiNhan(diaChi.getIdKhachHang().getTen());
        ttvcService.save(ttvc);

        // tạo đơn hàng
        HoaDon dh = new HoaDon();
        dh.setIdThongTinVanChuyen(ttvc);
        List<HoaDon> listAll = donHangService.getAll();
        List<Integer> listId = new ArrayList<>();
        if (listAll.size() == 0) {
            dh.setMa("DH1");
        } else {
            for (HoaDon donHang : listAll) {
                int index = Integer.parseInt(donHang.getMa().substring(2));
                listId.add(index);
            }
            Optional<Integer> maxNumber = listId.stream().max(Integer::compareTo);
            maxNumber.ifPresent(integer -> dh.setMa("DH" + (integer + 1)));
        }
        dh.setIdKhachHang(khachHangService.getByID(id));
        dh.setIdPhuongThucThanhToan(ptttService.getById("1"));
        dh.setTrangThai(1);
        dh.setNgayTao(LocalDateTime.now());
        if (idPromotion != null) {
            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).isPresent()
                    ? dotGiamGiaService.findById(Integer.parseInt(idPromotion)).get() : null;
            if (km != null) {
                dh.setIdDotGiamGia(km);
            }
        }

        donHangService.save(dh);

        HoaDon donHangNew = donHangService.findByHD(dh.getMa());

        ThongBao thongBao = new ThongBao();

        thongBao.setIdHoaDon(donHangNew);

        thongBao.setTrangThaiDonHang(1);

        thongBao.setNoiDung("Đặt Hàng Thành Công");

        thongBao.setNgayGui(LocalDate.now());

        thongBao.setIdKhachHang(khachHangService.getByID(id));

        thongBaoService.save(thongBao);

        // tạo hoá đơn chi tiết
        for (GioHangChiTietDTO ghct : list) {
            ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(ghct.getIdSanPhamChiTiet()));
            ChiTietHoaDon dhct = new ChiTietHoaDon();
            dhct.setIdChiTietSanPham(spct);
            dhct.setSoLuong(Integer.parseInt(ghct.getSoLuong()));
            dhct.setIdHoaDon(donHangNew);
            dhct.setNgayTao(LocalDateTime.now());
            dhct.setDonGia(spService.getCTSPById(Integer.valueOf(ghct.getIdSanPhamChiTiet())).getIdSanPham().getGia());
            dhct.setGiaThoiDiemMua(spService.getCTSPById(Integer.valueOf(ghct.getIdSanPhamChiTiet())).getIdSanPham().getGia());
            BigDecimal tongTien = BigDecimal.valueOf(Integer.parseInt(ghct.getSoLuong()))
                    .multiply(spService.getCTSPById(Integer.valueOf(ghct.getIdSanPhamChiTiet())).getIdSanPham().getGia());
            dhct.setTongTien(tongTien.add(tongTien.multiply(BigDecimal.valueOf(0.1))));
            donHangCTService.save(dhct);
            for (GioHangChiTiet gioHangChiTiet : ghService.getByIdGHList(ghct.getIdGioHang())) {
                if (Integer.parseInt(ghct.getIdSanPhamChiTiet()) == gioHangChiTiet.getIdSanPhamChiTiet().getId()) {
                    ghService.delete(String.valueOf(gioHangChiTiet.getId()));
                    break;
                }
            }
        }

        BigDecimal tongGiaTri = new BigDecimal("0");
        for (ChiTietHoaDon donHangChiTiet :
                donHangCTService.findByIdHD(String.valueOf(donHangNew.getId()))
        ) {
            tongGiaTri = tongGiaTri.add(donHangChiTiet.getTongTien());
        }

        donHangNew.setTongTien(tongGiaTri);
        donHangService.save(donHangNew);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/invoice/buy-now/{id}/{idAddress}")
    private ResponseEntity<?> saveInvoiceBuyNow(@RequestBody SPDTO dtoSP,
                                                @PathVariable("id") String id,
                                                @PathVariable("idAddress") String idAddress,
                                                @RequestParam(value = "idPromotion", required = false) String idPromotion) {

        System.out.println("Received SPDTO: " + dtoSP);
        ResponseMessage response = new ResponseMessage();
        ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(dtoSP.getId()));
        if (spct.getSoluong() <= 0 || spct.getSoluong() < dtoSP.getSoLuong() || spct.getIdSanPham().getTrangThai() == 0) {
            response.setTen(spct.getIdSanPham().getTen());
            response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
            response.setSl_ton(spct.getSoluong() + "");
            response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
            response.setTrangThai(spct.getIdSanPham().getTrangThai() != 0);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ThongTinVanChuyen ttvc = new ThongTinVanChuyen();
        DiaChi diaChi = diaChiService.getById(idAddress);
        ttvc.setTinhThanhpho(diaChi.getTinhTp());
        ttvc.setQuanHuyen(diaChi.getQuanHuyen());
        ttvc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        ttvc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        ttvc.setSdt(diaChi.getSdt());
        ttvc.setTenNguoiNhan(diaChi.getIdKhachHang().getTen());
        ttvcService.save(ttvc);

        // tạo đơn hàng
        HoaDon dh = new HoaDon();
        dh.setIdThongTinVanChuyen(ttvc);
        List<HoaDon> listAll = donHangService.getAll();
        List<Integer> listId = new ArrayList<>();
        if (listAll.size() == 0) {
            dh.setMa("DH1");
        } else {
            for (HoaDon donHang : listAll) {
                int index = Integer.parseInt(donHang.getMa().substring(2));
                listId.add(index);
            }
            Optional<Integer> maxNumber = listId.stream().max(Integer::compareTo);
            maxNumber.ifPresent(integer -> dh.setMa("DH" + (integer + 1)));
        }
        dh.setIdKhachHang(khachHangService.getByID(Integer.parseInt(id)));
        dh.setIdPhuongThucThanhToan(ptttService.getById("1"));
        dh.setTrangThai(1);
        dh.setNgayTao(LocalDateTime.now());
        if (idPromotion != null) {
            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).isPresent()
                    ? dotGiamGiaService.findById(Integer.parseInt(idPromotion)).get() : null;
            if (km != null) {
                dh.setIdDotGiamGia(km);
            }
        }
        donHangService.save(dh);

        HoaDon donHangNew = donHangService.findByHD(dh.getMa());

        ThongBao thongBao = new ThongBao();

        thongBao.setIdHoaDon(donHangNew);

        thongBao.setTrangThaiDonHang(1);

        thongBao.setNoiDung("Đặt Hàng Thành Công");

        thongBao.setNgayGui(LocalDate.now());

        thongBao.setIdKhachHang(khachHangService.getByID(Integer.valueOf(id)));

        thongBaoService.save(thongBao);

        //tạo hoá đơn chi tiết
       try {
           System.out.println("0000000000000");
           ChiTietHoaDon dhct = new ChiTietHoaDon();
           System.out.println("1111111111111");
           BigDecimal donGia = new BigDecimal(dtoSP.getDonGia());
           System.out.println("22222222222222");
           dhct.setIdChiTietSanPham(spService.getCTSPById(Integer.valueOf(dtoSP.getId())));
           System.out.println("33333333333333");
           dhct.setSoLuong(dtoSP.getSoLuong());
           System.out.println("44444444444444");
           dhct.setIdHoaDon(donHangNew);
           System.out.println("55555555555555");
           dhct.setNgayTao(LocalDateTime.now());
           System.out.println("66666666666666");
           dhct.setDonGia(donGia);
           System.out.println("77777777777777");
           dhct.setGiaThoiDiemMua(donGia);
           System.out.println("888888888888888");
           BigDecimal tongTien = BigDecimal.valueOf(dtoSP.getSoLuong())
                   .multiply(donGia);
           System.out.println("999999999999999");
           dhct.setTongTien(tongTien);
           System.out.println("1010101010101");
           donHangCTService.save(dhct);
           System.out.println("1111111111111"+ dhct);
           donHangNew.setTongTien(tongTien);
           donHangService.save(donHangNew);

       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/invoice/repurchase/{id}/{idAddress}")
    private ResponseEntity<?> repurchase(@RequestBody List<DonHangChiTietDTO> list,
                                         @PathVariable("id") String id,
                                         @PathVariable("idAddress") String idAddress,
                                         @RequestParam(value = "idPromotion", required = false) String idPromotion) {
        List<ResponseMessage> listMessage = new ArrayList<>();
        System.out.println("Log : " + list.toString());
        if (list.size() == 0) {
            return new ResponseEntity<>("null", HttpStatus.OK);
        } else {
            for (DonHangChiTietDTO donHangChiTietDTO : list) {
                ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(donHangChiTietDTO.getIdSanPhamChiTiet()));
                if (spct.getSoluong() <= 0 || Integer.parseInt(donHangChiTietDTO.getSoLuong()) > spct.getSoluong() || spct.getIdSanPham().getTrangThai() == 0) {
                    ResponseMessage response = new ResponseMessage();
                    response.setTen(spct.getIdSanPham().getTen());
                    response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                    response.setSl_ton(spct.getSoluong() + "");
                    response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                    response.setTrangThai(spct.getIdSanPham().getTrangThai() != 0);
                    listMessage.add(response);
                }

            }
        }

        if (listMessage.size() > 0) {
            return new ResponseEntity<>(listMessage, HttpStatus.OK);
        }

        ThongTinVanChuyen ttvc = new ThongTinVanChuyen();
        DiaChi diaChi = diaChiService.getById(idAddress);
        ttvc.setTinhThanhpho(diaChi.getTinhTp());
        ttvc.setQuanHuyen(diaChi.getQuanHuyen());
        ttvc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        ttvc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        ttvc.setSdt(diaChi.getSdt());
        ttvc.setTenNguoiNhan(diaChi.getIdKhachHang().getTen());
        ttvcService.save(ttvc);

        // tạo đơn hàng
        HoaDon dh = new HoaDon();
        dh.setIdThongTinVanChuyen(ttvc);
        List<HoaDon> listAll = donHangService.getAll();
        List<Integer> listId = new ArrayList<>();
        if (listAll.size() == 0) {
            dh.setMa("DH1");
        } else {
            for (HoaDon donHang : listAll) {
                int index = Integer.parseInt(donHang.getMa().substring(2));
                listId.add(index);
            }
            Optional<Integer> maxNumber = listId.stream().max(Integer::compareTo);
            maxNumber.ifPresent(integer -> dh.setMa("DH" + (integer + 1)));
        }
        dh.setIdKhachHang(khachHangService.getByID(Integer.valueOf(id)));
        dh.setIdPhuongThucThanhToan(ptttService.getById("1"));
        dh.setTrangThai(1);
        dh.setNgayTao(LocalDateTime.now());
        if (idPromotion != null) {
            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).isPresent()
                    ? dotGiamGiaService.findById(Integer.parseInt(idPromotion)).get() : null;
            if (km != null) {
                dh.setIdDotGiamGia(km);
            }
        }

        donHangService.save(dh);

        HoaDon donHangNew = donHangService.findByHD(dh.getMa());

        ThongBao thongBao = new ThongBao();

        thongBao.setIdHoaDon(donHangNew);

        thongBao.setTrangThaiDonHang(1);

        thongBao.setNoiDung("Đặt Hàng Thành Công");

        thongBao.setNgayGui(LocalDate.now());

        thongBao.setIdKhachHang(khachHangService.getByID(Integer.parseInt(id)));

        thongBaoService.save(thongBao);

        // tạo hoá đơn chi tiết
        for (DonHangChiTietDTO dto : list) {
            ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(dto.getIdSanPhamChiTiet()));
            ChiTietHoaDon dhct = new ChiTietHoaDon();
            int quantity = Integer.parseInt(dto.getSoLuong());
            dhct.setIdChiTietSanPham(spct);
            dhct.setSoLuong(quantity);
            dhct.setIdHoaDon(donHangNew);
            dhct.setNgayTao(LocalDateTime.now());
            dhct.setDonGia(spct.getIdSanPham().getGia());
            dhct.setGiaThoiDiemMua(spct.getIdSanPham().getGia());
            BigDecimal tongTien = spct.getIdSanPham().getGia().multiply(new BigDecimal(quantity));
            dhct.setTongTien(tongTien.add(tongTien.multiply(BigDecimal.valueOf(0.1))));
            donHangCTService.save(dhct);
        }

        BigDecimal tongGiaTri = new BigDecimal("0");
        for (ChiTietHoaDon donHangChiTiet :
                donHangCTService.findByIdHD(String.valueOf(donHangNew.getId()))
        ) {
            tongGiaTri = tongGiaTri.add(donHangChiTiet.getTongTien());
        }

        donHangNew.setTongTien(tongGiaTri);
        donHangService.save(donHangNew);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }
}

