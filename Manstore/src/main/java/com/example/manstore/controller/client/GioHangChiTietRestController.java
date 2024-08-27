package com.example.manstore.controller.client;


import com.example.manstore.CustomModel.ResponseCustom;
import com.example.manstore.CustomModel.ResponseMessage;
import com.example.manstore.CustomModel.ResponseProduct;
import com.example.manstore.dto.custom.ChiTietSanPhamDTO;

import com.example.manstore.dto.custom.SanPhamChiTietDTO;
import com.example.manstore.dto.respone.GioHangChiTietResponse;
import com.example.manstore.entity.*;
import com.example.manstore.service.ChiTietSanPhamService;
import com.example.manstore.service.GioHangChiTietService;
import com.example.manstore.service.GioHangService;
import com.example.manstore.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/client/cart_detail", "/api/cart_detail"})
public class GioHangChiTietRestController {

    @Autowired
    GioHangChiTietService service;
//    @Autowired
//    DonHangService donHangservice;
//    @Autowired
//    DonHangChiTietService donHangChiTietservice;
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    ChiTietSanPhamService ctspService;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;

    @RequestMapping(value = "/page/{pageNumber}/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> pagination(@PathVariable("pageNumber") int pageNumber, @PathVariable("id") String id) {
        Pageable pageable = PageRequest.of(pageNumber, 4, Sort.by("ngaySua").descending());
        GioHang gioHang = gioHangService.findByIdKH(Integer.valueOf(id));
        Page<GioHangChiTiet> page = service.getByIdGH(String.valueOf(gioHang.getId()), pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@PathVariable("id") String id) {
        try {
            // Tìm giỏ hàng theo ID khách hàng
            GioHang gh = gioHangService.findByIdKH(Integer.parseInt(id));

            // Nếu không tìm thấy, tạo giỏ hàng mới
            if (gh == null) {
                GioHang cart = new GioHang();
                KhachHang kh = khachHangService.getByID(Integer.parseInt(id));
                cart.setNgayTao(LocalDate.now());
                cart.setIdKhachHang(kh);
                gioHangService.save(cart);
                gh = gioHangService.findByIdKH(Integer.parseInt(id));
            }

            // Lấy danh sách chi tiết giỏ hàng
            List<GioHangChiTiet> list = service.getByIdGHList(String.valueOf(gh.getId()));

            // Trả về danh sách chi tiết giỏ hàng
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (Exception e) {
            // Ghi log lỗi và trả về lỗi 500 nếu có lỗi xảy ra
            System.err.println("Error occurred while retrieving cart details: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    private ResponseEntity<?> add(@RequestBody SanPhamChiTietDTO dto, @PathVariable("id") String id) {
        ChiTietSanPham sanPhamChiTiet = ctspService.findIdProductByColorAndSize(dto.getIdSP(), dto.getMs(), dto.getSize());
        GioHang gioHang = gioHangService.findByIdKH(Integer.parseInt(id));

        if (sanPhamChiTiet.getIdSanPham().getTrangThai() == 0) {
            return new ResponseEntity<>("discontinued product", HttpStatus.OK);
        }

        if (gioHang == null) {
            GioHang cart = new GioHang();
            KhachHang kh = khachHangService.getByID(Integer.parseInt(id));
            cart.setIdKhachHang(kh);
            gioHangService.save(cart);
            gioHang = gioHangService.findByIdKH(Integer.parseInt(id));
        }

        boolean isCheck = false;
        GioHangChiTiet ghctTemp = null;
        for (GioHangChiTiet ghct : gioHangChiTietService.getByIdGHList(String.valueOf(gioHang.getId()))) {
            if (ghct.getIdSanPhamChiTiet().getId() == sanPhamChiTiet.getId()) {
                isCheck = true;
                ghctTemp = ghct;
                break;
            }
        }
        if (!isCheck) {
            if (sanPhamChiTiet.getSoluong() == 0) {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setIdGioHang(gioHang);
            gioHangChiTiet.setSoLuong(1);
            gioHangChiTiet.setNgaySua(LocalDate.now());
            gioHangChiTiet.setIdSanPhamChiTiet(sanPhamChiTiet);
            System.out.println("if check = false");
            service.save(gioHangChiTiet);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            if (sanPhamChiTiet.getSoluong() == 0) {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
            if (ghctTemp.getIdSanPhamChiTiet().getSoluong() <= ghctTemp.getSoLuong()) {
                ghctTemp.setSoLuong(ghctTemp.getIdSanPhamChiTiet().getSoluong());
                service.save(ghctTemp);
                return new ResponseEntity<>(ghctTemp.getSoLuong() + "", HttpStatus.OK);
            }
            ghctTemp.setSoLuong(ghctTemp.getSoLuong() + 1);
            System.out.println("if check = true");
            service.save(ghctTemp);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/add-to-cart/{id_product}/{id_user}", method = RequestMethod.POST)
    private ResponseEntity<?> addByProductDetailPage(@PathVariable("id_product") Integer idProduct,
                                                     @PathVariable("id_user") Integer idUser,
                                                     @RequestBody SanPhamChiTietDTO dto) {
        ChiTietSanPham chiTietSanPham = ctspService.findIdProductByColorAndSize(String.valueOf(idProduct), dto.getMs(), dto.getSize());
        System.out.println("Sản Phẩm Chi Tiết " + chiTietSanPham.toString());
        GioHang gioHang = gioHangService.findByIdKH(idUser);
        boolean isCheck = false;

        GioHangChiTiet ghctAssign = null;

        for (GioHangChiTiet ghct : gioHangChiTietService.getByIdGHList(String.valueOf(gioHang.getId()))) {
            System.out.println("Giỏ Hàng : " + gioHang.toString());
            if (ghct.getIdSanPhamChiTiet().getId() == chiTietSanPham.getId()) {
                isCheck = true;
                ghctAssign = ghct;
                break;
            }
        }
        if (isCheck == false) {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setIdGioHang(gioHang);
            gioHangChiTiet.setSoLuong(Integer.valueOf(dto.getSl()));
            gioHangChiTiet.setNgaySua(LocalDate.now());
            gioHangChiTiet.setIdSanPhamChiTiet(chiTietSanPham);
            service.save(gioHangChiTiet);
        } else {
            ghctAssign.setSoLuong(ghctAssign.getSoLuong() + Integer.valueOf(dto.getSl()));
            service.save(ghctAssign);
        }
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (service.getById(String.valueOf(id)) != null) {
            service.delete(String.valueOf(id));
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("failure", HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-a-lot/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteALot(@RequestBody List<String> list, @PathVariable("userId") String userId) {
        try {
            if (list.size() == 0) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                for (String id : list
                ) {
                    GioHang gh = gioHangService.findByIdKH(Integer.parseInt(userId));
                    String idGhct = null;
                    if (gioHangChiTietService.getByIdGHList(String.valueOf(gh.getId())).size() == 0) {
                        System.out.println("ghct null");
                    }
                    for (GioHangChiTiet ghct : gioHangChiTietService.getByIdGHList(String.valueOf(gh.getId()))) {
                        if (String.valueOf(ghct.getIdSanPhamChiTiet().getId()).equalsIgnoreCase(id)) {
                            idGhct = String.valueOf(ghct.getId());
                            break;
                        }
                    }
                    System.out.println("idGhct" + idGhct);
                    if (idGhct != null) {
                        service.delete(idGhct);
                        System.out.println("is Delete");
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete items", e);
        }
    }

    @RequestMapping(value = "/minus-quantity/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> minusQuantity(@PathVariable("id") String id) {
        GioHangChiTiet ghct = gioHangChiTietService.getById(id);
        ResponseCustom response = new ResponseCustom();
        if (ghct.getSoLuong() <= 1) {
            response.setStatusText("success");
            response.setMessage("The product has been removed from the cart!");
            gioHangChiTietService.delete(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ghct.setSoLuong(ghct.getSoLuong() - 1);
            gioHangChiTietService.save(ghct);
            response.setStatusText("success");
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/add-quantity/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseCustom> addQuantity(@PathVariable("id") String id) {
        ResponseCustom response = new ResponseCustom();

        // Kiểm tra và xử lý nếu GioHangChiTiet không tồn tại
        Optional<GioHangChiTiet> optionalGioHangChiTiet = Optional.ofNullable(gioHangChiTietService.getById(id));
        if (!optionalGioHangChiTiet.isPresent()) {
            response.setStatusText("failure");
            response.setMessage("Không tìm thấy sản phẩm trong giỏ hàng.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        GioHangChiTiet ghct = optionalGioHangChiTiet.get();
        ChiTietSanPham spct = ctspService.getCTSPById(ghct.getIdSanPhamChiTiet().getId());

        // Kiểm tra trạng thái của sản phẩm
        if (spct.getSoluong() == 0) {
            response.setStatusText("failure");
            response.setMessage("Sản phẩm đã hết hàng.");
            gioHangChiTietService.delete(id); // Xóa sản phẩm khỏi giỏ hàng nếu hết hàng
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Kiểm tra nếu số lượng yêu cầu vượt quá số lượng còn lại
        if (ghct.getSoLuong() >= spct.getSoluong()) {
            ghct.setSoLuong(spct.getSoluong());
            gioHangChiTietService.save(ghct);
            response.setStatusText("failure");
            response.setMessage("Số lượng sản phẩm chỉ còn lại " + spct.getSoluong() + ".");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Tăng số lượng sản phẩm trong giỏ hàng
        ghct.setSoLuong(ghct.getSoLuong() + 1);
        gioHangChiTietService.save(ghct);
        response.setStatusText("success");
        response.setMessage("Số lượng sản phẩm đã được tăng lên.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/edit-quantity/{id}/{quantity}", method = RequestMethod.GET)
    public ResponseEntity<?> editQuantity(@PathVariable("id") String id, @PathVariable("quantity") String quantity) {
        GioHangChiTiet ghct = gioHangChiTietService.getById(id);
        ChiTietSanPham spct = ctspService.getCTSPById(ghct.getIdSanPhamChiTiet().getId());
        String regexQuantity = "\\d+";
        ResponseCustom response = new ResponseCustom();
        if (spct.getSoluong() > 0) {
            if (quantity.matches(regexQuantity)) {
                int quantityInteger = Integer.parseInt(quantity);
                if (quantityInteger < 1) {
                    response.setStatusText("failure");
                    response.setMessage("Quantity must be greater than 0");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    if (quantityInteger > spct.getSoluong()) {
                        response.setStatusText("failure");
                        response.setMessage(spct.getSoluong() + "");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        ghct.setSoLuong(quantityInteger);
                        response.setStatusText("success");
                        response.setMessage("success");
                        gioHangChiTietService.save(ghct);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }
            } else {
                response.setStatusText("failure");
                response.setMessage("Format error");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            response.setStatusText("failure");
            response.setMessage("The product is out of stock");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update-quantity-is-fail-order/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateQuantity(@PathVariable("id") String id, @RequestBody List<String> listId) {
        GioHang gh = gioHangService.getById(id);
        List<GioHangChiTiet> list = new ArrayList<>();
        for (GioHangChiTiet ghct : service.getByIdGHList(String.valueOf(gh.getId()))
        ) {
            String idSPCT = String.valueOf(ghct.getIdSanPhamChiTiet().getId());
            ChiTietSanPham spct = ctspService.getCTSPById(Integer.parseInt(idSPCT));
            for (String idGHCT : listId
            ) {
                if (ghct.getId() == Integer.parseInt(idGHCT)) {
                    if (ghct.getSoLuong() >= spct.getSoluong()) {
                        ghct.setSoLuong(spct.getSoluong() - 1);
                        gioHangChiTietService.save(ghct);
                    }
                    list.add(ghct);
                }
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-to-invoice/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addToInvoice(@RequestBody List<String> listProduct, @PathVariable("id") String idUser) {
        ResponseProduct responseProduct = new ResponseProduct();
        List<GioHangChiTiet> listGHCT = new ArrayList<>();
        List<ResponseMessage> listMessage = new ArrayList<>();
        GioHang gioHang = gioHangService.findByIdKH(Integer.parseInt(idUser));
        List<GioHangChiTiet> listAll = gioHangChiTietService.getByIdGHList(String.valueOf(gioHang.getId()));
        if (listProduct.size() == 0) {
            return new ResponseEntity<>("null", HttpStatus.OK);
        } else {
            for (String idProduct : listProduct) {
                ChiTietSanPham spct = ctspService.getCTSPById(Integer.valueOf(idProduct));
                if (spct.getSoluong() <= 0) {
                    for (GioHangChiTiet ghct : listAll) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() || spct.getIdSanPham().getTrangThai() == 0) {
                            gioHangChiTietService.delete(String.valueOf(ghct.getId()));
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
                    for (GioHangChiTiet ghct : listAll) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() || spct.getIdSanPham().getTrangThai() == 0) {
                            gioHangChiTietService.delete(String.valueOf(ghct.getId()));
                        }
                    }
                    ResponseMessage response = new ResponseMessage();
                    response.setTen(spct.getIdSanPham().getTen());
                    response.setMs_size(spct.getIdSize().getTen() + " & " + spct.getIdMauSac().getTen());
                    response.setSl_ton(spct.getSoluong() + "");
                    response.setSport(spct.getIdSanPham().getIdDanhMuc().getId() + "");
                    response.setTrangThai(false);
                    listMessage.add(response);
                } else {
                    for (GioHangChiTiet ghct : listAll) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() && ghct.getSoLuong() > 20) {
                            return new ResponseEntity<>("max quantity", HttpStatus.OK);
                        }
                    }
                    for (GioHangChiTiet ghct : listAll) {
                        if (spct.getId() == ghct.getIdSanPhamChiTiet().getId() && ghct.getSoLuong() > spct.getSoluong()) {
                            ghct.setSoLuong(spct.getSoluong());
                            gioHangChiTietService.save(ghct);
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
            if (listMessage.size() > 0) {
                responseProduct.setListCart(listGHCT);
                responseProduct.setListMessage(listMessage);
                return new ResponseEntity<>(responseProduct, HttpStatus.OK);
            }
            for (String id : listProduct
            ) {
                GioHang gh = gioHangService.findByIdKH(Integer.parseInt(idUser));

                if (gioHangChiTietService.getByIdGHList(String.valueOf(gh.getId())).size() == 0) {
                    System.out.println("Cart detail null");
                } else {
                    for (GioHangChiTiet ghct : gioHangChiTietService.getByIdGHList(String.valueOf(gh.getId()))) {
                        if (String.valueOf(ghct.getIdSanPhamChiTiet().getId()).equalsIgnoreCase(id)) {
                            listGHCT.add(ghct);
                            break;
                        }
                    }
                }

            }
        }
        responseProduct.setListCart(listGHCT);
        responseProduct.setListMessage(listMessage);
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

}
