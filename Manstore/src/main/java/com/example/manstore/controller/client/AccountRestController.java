package com.example.manstore.controller.client;

import com.example.manstore.entity.*;
import com.example.manstore.service.DiaChiService;
import com.example.manstore.service.HoaDonChiTietService;
import com.example.manstore.service.HoaDonService;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.HoaDonChiTietServiceImpl;
import com.example.manstore.service.Impl.ThongBaoServiceImpl;
import com.example.manstore.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/client/account")
public class AccountRestController {
    @Autowired
    KhachHangService khachHangService;

    @Autowired
    HoaDonService donHangService;

    @Autowired
    HoaDonChiTietServiceImpl donHangChiTietService;

    @Autowired
    DiaChiService diaChiService;

    @Autowired
    ThongBaoServiceImpl thongBaoService;

    @Autowired
    private ChiTietSanPhamImpl spService;

    @Autowired
    private HoaDonChiTietService donHangCTService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> account(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(khachHangService.getByID(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/updateAddress/{idAddress}", method = RequestMethod.POST)
    public ResponseEntity<?> updateAddress(@PathVariable("id") String id, @PathVariable("idAddress") String idAddress) {
        for (DiaChi diaChi : diaChiService.getByIdKH(id)) {
            diaChi.setDefault(diaChi.getId() == Integer.parseInt(idAddress));
            diaChiService.save(diaChi);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{idkh}/{status}/page/{pageNumber}", method = RequestMethod.GET)
    private ResponseEntity<?> getOrderByIdKH(@PathVariable("pageNumber") String pageNumber, @PathVariable("idkh") String id, @PathVariable("status") String status) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), 3, Sort.by("ngayTao").descending());
        return new ResponseEntity<>(donHangService.findByIdKHAndStatus(pageable, id, status), HttpStatus.OK);
    }

    @RequestMapping(value = "/order/track-order/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> trackOrder(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(donHangService.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/orderDetail/{idGH}", method = RequestMethod.GET)
    private ResponseEntity<?> getOrderDetailByIdKH(@PathVariable("idGH") String idGH) {
        return new ResponseEntity<>(donHangChiTietService.findByIdHD(idGH), HttpStatus.OK);
    }

    @RequestMapping(value = "/statusDate/{idDH}", method = RequestMethod.GET)
    private ResponseEntity<?> statusDate(@PathVariable("idDH") String idDH) {
        return new ResponseEntity<>(thongBaoService.findByInvoice(idDH), HttpStatus.OK);
    }

//    @RequestMapping(value = "/{id}/cancelOrder/{invoice_Id}", method = RequestMethod.POST)
//    public ResponseEntity<?> cancelOrder(@PathVariable(value = "id") String userId
//            , @PathVariable(value = "invoice_Id") String invoice_Id, @RequestBody String reason) {
//        System.out.println(reason);
//        if (userId != null) {
//            HoaDon updateHoaDon = donHangService.findById(Integer.parseInt(invoice_Id)).isPresent()
//                    ? donHangService.findById(Integer.parseInt(invoice_Id)).get() : null;
//            if (updateHoaDon != null && updateHoaDon.getTrangThai() == 1) {
//                updateHoaDon.setGhiChu(reason);
//                updateHoaDon.setTrangThai(6);
//                donHangService.save(updateHoaDon);
//                ThongBao thongBao = new ThongBao();
//
//                thongBao.setIdHoaDon(updateHoaDon);
//
//                thongBao.setTrangThaiDonHang(6);
//
//                thongBao.setNoiDung(reason);
//
//                thongBao.setNgayGui(LocalDateTime.now());
//
//                thongBao.setIdKhachHang(updateHoaDon.getIdKhachHang());
//
//                thongBaoService.save(thongBao);
//
//                return new ResponseEntity<>("success", HttpStatus.OK);
//            }
//            return new ResponseEntity<>("failure", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("error", HttpStatus.OK);
//    }

    @RequestMapping(value = "/{id}/cancelOrder/{invoice_Id}", method = RequestMethod.POST)
    public ResponseEntity<?> cancelOrder(@PathVariable(value = "id") String userId,
                                         @PathVariable(value = "invoice_Id") String invoice_Id,
                                         @RequestBody String reason) {
        System.out.println(reason);
        List<ChiTietHoaDon> list = donHangChiTietService.findByIdHD(invoice_Id); // Chỉnh sửa tham số từ userId thành invoice_Id
        if (userId != null) {
            HoaDon updateHoaDon = donHangService.findById(Integer.parseInt(invoice_Id)).isPresent()
                    ? donHangService.findById(Integer.parseInt(invoice_Id)).get() : null;
            if (updateHoaDon != null && updateHoaDon.getTrangThai() == 1) {
                // Cập nhật trạng thái đơn hàng và ghi chú
                updateHoaDon.setGhiChu(reason);
                updateHoaDon.setTrangThai(6);

                // Hoàn lại số lượng sản phẩm trong kho
                for (ChiTietHoaDon dhct : list) {
                    ChiTietSanPham spct = dhct.getIdChiTietSanPham();
                    int soLuongDaDat = dhct.getSoLuong(); // Lấy số lượng đã đặt
                    spct.setSoluong(spct.getSoluong() + soLuongDaDat); // Hoàn lại số lượng đúng với số lượng đã đặt
                    spService.save(spct);
                }

                donHangService.save(updateHoaDon);

                // Tạo thông báo
                ThongBao thongBao = new ThongBao();
                thongBao.setIdHoaDon(updateHoaDon);
                thongBao.setTrangThaiDonHang(6);
                thongBao.setNoiDung(reason);
                thongBao.setNgayGui(LocalDateTime.now());
                thongBao.setIdKhachHang(updateHoaDon.getIdKhachHang());
                thongBaoService.save(thongBao);

                return new ResponseEntity<>("success", HttpStatus.OK);
            }
            return new ResponseEntity<>("failure", HttpStatus.OK);
        }
        return new ResponseEntity<>("error", HttpStatus.OK);
    }



}