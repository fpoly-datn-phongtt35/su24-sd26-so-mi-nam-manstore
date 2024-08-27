package com.example.manstore.controller.client;

import com.example.manstore.entity.DiaChi;
import com.example.manstore.entity.HoaDon;
import com.example.manstore.entity.ThongBao;
import com.example.manstore.service.DiaChiService;
import com.example.manstore.service.HoaDonService;
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

    @RequestMapping(value = "/{id}/cancelOrder/{invoice_Id}", method = RequestMethod.POST)
    public ResponseEntity<?> cancelOrder(@PathVariable(value = "id") String userId
            , @PathVariable(value = "invoice_Id") String invoice_Id, @RequestBody String reason) {
        System.out.println(reason);
        if (userId != null) {
            HoaDon updateHoaDon = donHangService.findById(Integer.parseInt(invoice_Id)).isPresent()
                    ? donHangService.findById(Integer.parseInt(invoice_Id)).get() : null;
            if (updateHoaDon != null && updateHoaDon.getTrangThai() == 1) {
                updateHoaDon.setGhiChu(reason);
                updateHoaDon.setTrangThai(6);
                donHangService.save(updateHoaDon);
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