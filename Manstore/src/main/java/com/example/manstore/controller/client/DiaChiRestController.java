package com.example.manstore.controller.client;

import com.example.manstore.CustomModel.ResponseCustom;
import com.example.manstore.dto.respone.DiaChiResponse;
import com.example.manstore.entity.DiaChi;
import com.example.manstore.service.Impl.DiaChiServiceImpl;
import com.example.manstore.service.Impl.KhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/client/address")
public class DiaChiRestController {

    @Autowired
    private DiaChiServiceImpl service;

    @Autowired
    private KhachHangServiceImpl Customerservice;

    @RequestMapping(value = "/get-by-customer/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getByCustomer(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.getByIdKH(String.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/save/{id}")
    private ResponseEntity<?> save(@PathVariable("id") String id, @RequestBody DiaChiResponse diaChi) {
        DiaChi dc = new DiaChi();
        dc.setIdKhachHang(Customerservice.getByID(Integer.parseInt(id)));
        dc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        List<DiaChi> list = service.getByIdKH(id);
        boolean isDefault = true;
        for (DiaChi address : list
        ) {
            if (address.isDefault()) {
                isDefault = false;
                break;
            }
        }
        dc.setDefault(isDefault);
        dc.setSdt(diaChi.getSdt());
        dc.setTinhTp(diaChi.getTinhTp());
        dc.setQuanHuyen(diaChi.getQuanHuyen());
        dc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        dc.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        service.save(dc);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody DiaChiResponse diaChi) {
        DiaChi dc = service.getById(id);
        System.out.println(diaChi.toString());
        dc.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        dc.setTinhTp(diaChi.getTinhTp());
        dc.setQuanHuyen(diaChi.getQuanHuyen());
        dc.setSdt(diaChi.getSdt());
        dc.setXaPhuongThitran(diaChi.getXaPhuongThitran());
        dc.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        service.save(dc);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/validation/{id}")
    private ResponseEntity<?> validation(@PathVariable("id") String id, @RequestBody DiaChiResponse diaChi) {
        List<DiaChi> list = service.getByIdKH(id);
        System.out.println("Dia chi" + diaChi.toString());
        List<ResponseCustom> listResponse = new ArrayList<>();
        String regexPhoneNumber = "^(\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[0-9]|9[0-9])\\d{7,8}$";
        String regexAddressDetail = "^(?=.*[\\p{L}])[\\p{L}\\p{N}][\\p{L}\\p{N}\\p{P}\\p{Z}]{3,253}[\\p{L}\\p{N}\\p{P}]$";
        String regexName = "^[a-zA-ZÀ-Ỹà-ỹ][a-zA-Z0-9À-Ỹà-ỹ ]{5,50}$";
        boolean isValid = false;
        if (!diaChi.getSdt().matches(regexPhoneNumber)) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("failure");
            response.setMessage("Error Number Phone");
            listResponse.add(response);
            isValid = true;
        }
        if (!diaChi.getDiaChiCuThe().matches(regexAddressDetail)) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("failure");
            response.setMessage("Error Address Detail");
            listResponse.add(response);
            isValid = true;
        }
        if (!diaChi.getTenNguoiNhan().matches(regexName)) {
            if(diaChi.getTenNguoiNhan().length() >= 50) {
                ResponseCustom response = new ResponseCustom();
                response.setStatusText("failure");
                response.setMessage("Error Name Length");
                listResponse.add(response);
            } else {
                ResponseCustom response = new ResponseCustom();
                response.setStatusText("failure");
                response.setMessage("Error Name Format");
                listResponse.add(response);
            }
            isValid = true;
        }
        for (DiaChi dc : list
        ) {
            if (diaChi.getTinhTp().equalsIgnoreCase(dc.getTinhTp())) {
                if (diaChi.getQuanHuyen().equalsIgnoreCase(dc.getQuanHuyen())) {
                    if (diaChi.getXaPhuongThitran().equalsIgnoreCase(dc.getXaPhuongThitran())) {
                        if (diaChi.getDiaChiCuThe().equalsIgnoreCase(dc.getDiaChiCuThe()) &&
                                diaChi.getSdt().equalsIgnoreCase(dc.getSdt())) {
                            if (diaChi.getSdt().equalsIgnoreCase(dc.getSdt())) {
                                ResponseCustom response = new ResponseCustom();
                                response.setMessage("Duplicate address");
                                response.setStatusText("failure");
                                listResponse.add(response);
                                isValid = true;
                            }
                        }
                    }
                }
            }
        }
        if (!isValid) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("success");
            response.setMessage("No Error");
            listResponse.add(response);
        }
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    @PostMapping("/validation-update/{id}/{idAddress}")
    private ResponseEntity<?> validationUpdate(@PathVariable("id") String id,
                                               @PathVariable("idAddress") String idAddress,
                                               @RequestBody DiaChiResponse diaChi) {
        List<DiaChi> list = service.getByIdKH(id);
        DiaChi diaChiUpdate = service.getById(idAddress);
        list.remove(diaChiUpdate);
        List<ResponseCustom> listResponse = new ArrayList<>();
        String regexPhoneNumber = "^(\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[0-9]|9[0-9])\\d{7}$";
        String regexAddressDetail = "^(?=.*[\\p{L}])[\\p{L}\\p{N}][\\p{L}\\p{N}\\p{P}\\p{Z}]{3,253}[\\p{L}\\p{N}\\p{P}]$";
        String regexName = "^[a-zA-ZÀ-Ỹà-ỹ][a-zA-Z0-9À-Ỹà-ỹ ]{5,50}$";
        boolean isValid = false;
        if (!diaChi.getSdt().matches(regexPhoneNumber)) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("failure");
            response.setMessage("Error Number Phone");
            listResponse.add(response);
            isValid = true;
        }
        if (!diaChi.getDiaChiCuThe().matches(regexAddressDetail)) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("failure");
            response.setMessage("Error Address Detail");
            listResponse.add(response);
            isValid = true;
        }
        if (!diaChi.getTenNguoiNhan().matches(regexName)) {
            if(diaChi.getTenNguoiNhan().length() >= 50) {
                ResponseCustom response = new ResponseCustom();
                response.setStatusText("failure");
                response.setMessage("Error Name Length");
                listResponse.add(response);
            } else {
                ResponseCustom response = new ResponseCustom();
                response.setStatusText("failure");
                response.setMessage("Error Name Format");
                listResponse.add(response);
            }
            isValid = true;
        }
        for (DiaChi dc : list
        ) {
            if (diaChi.getTinhTp().equalsIgnoreCase(dc.getTinhTp())) {
                if (diaChi.getQuanHuyen().equalsIgnoreCase(dc.getQuanHuyen())) {
                    if (diaChi.getXaPhuongThitran().equalsIgnoreCase(dc.getXaPhuongThitran())) {
                        if (diaChi.getDiaChiCuThe().equalsIgnoreCase(dc.getDiaChiCuThe()) &&
                                diaChi.getSdt().equalsIgnoreCase(dc.getSdt())) {
                            if (diaChi.getSdt().equalsIgnoreCase(dc.getSdt())) {
                                ResponseCustom response = new ResponseCustom();
                                response.setMessage("Duplicate address");
                                response.setStatusText("failure");
                                listResponse.add(response);
                                isValid = true;
                            }
                        }
                    }
                }
            }
        }
        if (!isValid) {
            ResponseCustom response = new ResponseCustom();
            response.setStatusText("success");
            response.setMessage("No Error");
            listResponse.add(response);
        }
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }
}
