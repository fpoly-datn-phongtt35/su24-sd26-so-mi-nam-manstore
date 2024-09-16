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

    @PostMapping("/invoice/save/{id}/{idAddress}")
    private ResponseEntity<?> saveInvoice(@RequestBody List<GioHangChiTietDTO> list,
                                          @PathVariable("id") Integer id,
                                          @PathVariable("idAddress") String idAddress,
                                          @RequestParam(value = "idPromotion", required = false) String idPromotion) {
        List<ResponseMessage> listMessage = new ArrayList<>();
        GioHang gioHang = gioHangService.findByIdKH(id);
        List<GioHangChiTiet> listAllGH = ghService.getByIdGHList(String.valueOf(gioHang.getId()));

        if (list.isEmpty()) {
            return new ResponseEntity<>("null", HttpStatus.OK);
        }

        // Kiểm tra tồn kho và trạng thái sản phẩm
        for (GioHangChiTietDTO gioHangChiTietDTO : list) {
            ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(gioHangChiTietDTO.getIdSanPhamChiTiet()));
            boolean isDeleted = false;

            if (spct.getSoluong() <= 0 || spct.getIdSanPham().getTrangThai() == 0) {
                for (GioHangChiTiet ghct : listAllGH) {
                    if (spct.getId() == ghct.getIdSanPhamChiTiet().getId()) {
                        ghService.delete(String.valueOf(ghct.getId()));
                        isDeleted = true;
                    }
                }
                ResponseMessage response = new ResponseMessage();
                response.setTen(spct.getIdSanPham().getTen());
                response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                response.setSl_ton(spct.getSoluong() + "");
                response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                listMessage.add(response);
            } else if (!isDeleted) {
                for (GioHangChiTiet ghct : listAllGH) {
                    if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() && ghct.getSoLuong() > spct.getSoluong()) {
                        ghct.setSoLuong(spct.getSoluong());
                        ghService.save(ghct);
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

        if (!listMessage.isEmpty()) {
            return new ResponseEntity<>(listMessage, HttpStatus.OK);
        }

        // Cập nhật số lượng sản phẩm
        for (GioHangChiTietDTO ghct : list) {
            ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(ghct.getIdSanPhamChiTiet()));
            spct.setSoluong(spct.getSoluong() - Integer.parseInt(ghct.getSoLuong()));
            spService.save(spct);
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
        if (idPromotion != null && !idPromotion.equals("null")) {
            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).orElse(null);
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

        thongBao.setNgayGui(LocalDateTime.now());

        thongBao.setIdKhachHang(khachHangService.getByID(id));

        thongBaoService.save(thongBao);

        // tạo hoá đơn chi tiết
        BigDecimal totalInvoiceAmount = new BigDecimal("0");
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
            dhct.setTongTien(tongTien);

            totalInvoiceAmount = totalInvoiceAmount.add(tongTien);

            donHangCTService.save(dhct);

            for (GioHangChiTiet gioHangChiTiet : ghService.getByIdGHList(ghct.getIdGioHang())) {
                if (Integer.parseInt(ghct.getIdSanPhamChiTiet()) == gioHangChiTiet.getIdSanPhamChiTiet().getId()) {
                    ghService.delete(String.valueOf(gioHangChiTiet.getId()));
                    break;
                }
            }
        }
        donHangNew.setTongTien(totalInvoiceAmount);
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
        List<ResponseMessage> listMessage = new ArrayList<>();

        // Kiểm tra số lượng sản phẩm
        ChiTietSanPham spct = spService.getCTSPById(Integer.valueOf(dtoSP.getId()));
        if (spct.getSoluong() <= 0 || spct.getSoluong() < dtoSP.getSoLuong() || spct.getIdSanPham().getTrangThai() == 0) {
            response.setTen(spct.getIdSanPham().getTen());
            response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
            response.setSl_ton(spct.getSoluong() + "");
            response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
            response.setTrangThai(spct.getIdSanPham().getTrangThai() != 0);
            listMessage.add(response);
            return new ResponseEntity<>(listMessage, HttpStatus.OK);
        }

        // Tạo thông tin vận chuyển
        ThongTinVanChuyen ttvc = new ThongTinVanChuyen();
        DiaChi diaChi = diaChiService.getById(idAddress);
        ttvc.setTinhThanhpho(diaChi.getTinhTp());
        ttvc.setQuanHuyen(diaChi.getQuanHuyen());
        ttvc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        ttvc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        ttvc.setSdt(diaChi.getSdt());
        ttvc.setTenNguoiNhan(diaChi.getIdKhachHang().getTen());
        ttvcService.save(ttvc);

        // Tạo hóa đơn
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
//        if (idPromotion != null) {
//            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).orElse(null);
//            if (km != null) {
//                dh.setIdDotGiamGia(km);
//            }
//        }
        if (idPromotion != null && !idPromotion.equals("null")) {
            DotGiamGia km = dotGiamGiaService.findById(Integer.parseInt(idPromotion)).orElse(null);
            if (km != null) {
                dh.setIdDotGiamGia(km);
            }
        }
        donHangService.save(dh);

        HoaDon donHangNew = donHangService.findByHD(dh.getMa());

        // Tạo thông báo
        ThongBao thongBao = new ThongBao();
        thongBao.setIdHoaDon(donHangNew);
        thongBao.setTrangThaiDonHang(1);
        thongBao.setNoiDung("Đặt Hàng Thành Công");
        thongBao.setNgayGui(LocalDateTime.now());
        thongBao.setIdKhachHang(khachHangService.getByID(Integer.parseInt(id)));
        thongBaoService.save(thongBao);

        // Tạo hóa đơn chi tiết và kiểm tra số lượng sản phẩm
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ChiTietHoaDon dhct = new ChiTietHoaDon();
            BigDecimal donGia = new BigDecimal(dtoSP.getDonGia());
            dhct.setIdChiTietSanPham(spService.getCTSPById(Integer.valueOf(dtoSP.getId())));
            dhct.setSoLuong(dtoSP.getSoLuong());
            dhct.setIdHoaDon(donHangNew);
            dhct.setNgayTao(LocalDateTime.now());
            dhct.setDonGia(donGia);
            dhct.setGiaThoiDiemMua(donGia);
            BigDecimal tongTien = BigDecimal.valueOf(dtoSP.getSoLuong()).multiply(donGia);
            dhct.setTongTien(tongTien);
            donHangCTService.save(dhct);
            donHangNew.setTongTien(tongTien);
            donHangService.save(donHangNew);

            list.add(dhct);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kiểm tra số lượng sản phẩm sau khi tạo hóa đơn chi tiết
        for (ChiTietHoaDon dhct : list) {
            ChiTietSanPham spctCheck = spService.getCTSPById(dhct.getIdChiTietSanPham().getId());
            if (spctCheck.getSoluong() < dhct.getSoLuong() || spctCheck.getSoluong() <= 0) {
                ResponseMessage responseMessage = new ResponseMessage();
                responseMessage.setTen(spctCheck.getIdSanPham().getTen());
                responseMessage.setMs_size(spctCheck.getIdSize().getTen() + " & " + spctCheck.getIdMauSac().getTen());
                responseMessage.setSl_ton(spctCheck.getSoluong() + "");
                responseMessage.setSport(spctCheck.getIdSanPham().getIdDanhMuc().getId() + "");
                responseMessage.setTrangThai(spctCheck.getIdSanPham().getTrangThai() != 0);
                listMessage.add(responseMessage);
            }
        }

        if (listMessage.size() > 0) {
            return new ResponseEntity<>(listMessage, HttpStatus.OK);
        }

        // Cập nhật số lượng sản phẩm trong kho
        for (ChiTietHoaDon dhct : list) {
            ChiTietSanPham spctToUpdate = dhct.getIdChiTietSanPham();
            int count_of_product = spctToUpdate.getSoluong();
            int count_of_invoice = dhct.getSoLuong();
            spctToUpdate.setSoluong(count_of_product - count_of_invoice);
            spService.save(spctToUpdate);
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

        thongBao.setNgayGui(LocalDateTime.now());

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

