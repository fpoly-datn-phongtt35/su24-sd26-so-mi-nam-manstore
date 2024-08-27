package com.example.manstore.service.Impl;

import com.example.manstore.dto.respone.*;
import com.example.manstore.entity.GioHang;
import com.example.manstore.entity.GioHangChiTiet;
import com.example.manstore.repository.GioHangChiTietRepository;
import com.example.manstore.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository rp;

    @Override
    public Page<GioHangChiTiet> pagination(Pageable pageable) {
        return rp.findAll(pageable);
    }

    @Override
    public Page<GioHangChiTiet> getByIdGH(String id, Pageable pageable) {
        return rp.getByIdGH(id, pageable);
    }

    @Override
    public void save(GioHangChiTiet gioHangChiTiet) {
        rp.save(gioHangChiTiet);
    }

    @Override
    public List<GioHangChiTietResponse> getAllByIdGioHangOrderByNgaySuaDesc(String idGioHang) {
        List<GioHangChiTiet> gioHangChiTiets = rp.getByIdGHList(idGioHang);
        List<GioHangChiTietResponse> gioHangChiTietResponses = new ArrayList<>();
        gioHangChiTiets.forEach(gioHangChiTiet -> {
            System.out.println(gioHangChiTiet.toString());

            ThuongHieuResponse thuongHieuResponse = new ThuongHieuResponse();
            thuongHieuResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdThuongHieu().getId());
            thuongHieuResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdThuongHieu().getMa());
            thuongHieuResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdThuongHieu().getTen());
            thuongHieuResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdThuongHieu().getMoTa());

            //-----------------------------------

            DanhMucResponse danhMucResponse = new DanhMucResponse();
            danhMucResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDanhMuc().getId());
            danhMucResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDanhMuc().getMa());
            danhMucResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDanhMuc().getTen());
            danhMucResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDanhMuc().getMoTa());

            //-----------------------------------

            DuoiAoResponse duoiAoResponse = new DuoiAoResponse();
            duoiAoResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDuoiAo().getId());
            duoiAoResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDuoiAo().getMa());
            duoiAoResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDuoiAo().getTen());
            duoiAoResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdDuoiAo().getMoTa());

            //-----------------------------------

            KieuDangResponse kieuDangResponse = new KieuDangResponse();
            kieuDangResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdKieuDang().getId());
            kieuDangResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdKieuDang().getMa());
            kieuDangResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdKieuDang().getTen());
            kieuDangResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdKieuDang().getMoTa());

            //-----------------------------------

            ChatLieuResponse chatLieuResponse = new ChatLieuResponse();
            chatLieuResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdChatLieu().getId());
            chatLieuResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdChatLieu().getMa());
            chatLieuResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdChatLieu().getTen());
            chatLieuResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdChatLieu().getMoTa());

            //-----------------------------------

            CoAoResponse coAoResponse = new CoAoResponse();
            coAoResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdCoAo().getId());
            coAoResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdCoAo().getMa());
            coAoResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdCoAo().getTen());
            coAoResponse.setMoTa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getIdCoAo().getMoTa());


            SanPham1Respone sanPhanResponse = new SanPham1Respone();
            sanPhanResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getId());
            sanPhanResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getTen());
            sanPhanResponse.setGia(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getGia());
            sanPhanResponse.setSoLuong(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getSoLuong());
            sanPhanResponse.setNgayTao(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getNgayTao());
            sanPhanResponse.setDuongDan(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getDuongDan());
            sanPhanResponse.setTrangThai(gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham().getTrangThai());
            sanPhanResponse.setCoAoResponse(coAoResponse);
            sanPhanResponse.setChatLieuResponse(chatLieuResponse);
            sanPhanResponse.setKieuDangResponse(kieuDangResponse);
            sanPhanResponse.setDuoiAoResponse(duoiAoResponse);
            sanPhanResponse.setDanhMucResponse(danhMucResponse);
            sanPhanResponse.setThuongHieuResponse(thuongHieuResponse);

            //-----------------------------------

            MauSacResponse mauSacResponse = new MauSacResponse();
            mauSacResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId());
            mauSacResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getMa());
            mauSacResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getTen());


            //-----------------------------------

            SizeResponse sizeResponse = new SizeResponse();
            sizeResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getIdSize().getId());
            sizeResponse.setMa(gioHangChiTiet.getIdSanPhamChiTiet().getIdSize().getMa());
            sizeResponse.setTen(gioHangChiTiet.getIdSanPhamChiTiet().getIdSize().getTen());

            //-----------------------------------

            SanPhamChiTietResponse sanPhamChiTietResponse = new SanPhamChiTietResponse();
            sanPhamChiTietResponse.setId(gioHangChiTiet.getIdSanPhamChiTiet().getId());
            sanPhamChiTietResponse.setSanPham(sanPhanResponse);
            sanPhamChiTietResponse.setMauSac(mauSacResponse);
            sanPhamChiTietResponse.setSize(sizeResponse);
            sanPhamChiTietResponse.setSoluong(gioHangChiTiet.getIdSanPhamChiTiet().getSoluong().toString());
            sanPhamChiTietResponse.setDuongDan(gioHangChiTiet.getIdSanPhamChiTiet().getDuongDan());
            sanPhamChiTietResponse.setTrangThai(gioHangChiTiet.getIdSanPhamChiTiet().getTrangThai());

            //-----------------------------------
            GioHangResponse gioHangResponse = new GioHangResponse();
            gioHangResponse.setId(gioHangChiTiet.getIdGioHang().getId());
//            gioHangResponse.setNgayTao(gioHangChiTiet.getIdGioHang().getNgayTao()? "": gioHangChiTiet.getIdGioHang().getNgayTao().toString());

            //-----------------------------------



            GioHangChiTietResponse gioHangChiTietResponse = new GioHangChiTietResponse();
            gioHangChiTietResponse.setId(gioHangChiTiet.getId());
            gioHangChiTietResponse.setSoLuong(gioHangChiTiet.getSoLuong());
            gioHangChiTietResponse.setNgaySua(gioHangChiTiet.getNgaySua().toString());
            gioHangChiTietResponse.setSanPhamChiTiet(sanPhamChiTietResponse);
            gioHangChiTietResponse.setGioHang(gioHangResponse);


            gioHangChiTietResponses.add(gioHangChiTietResponse);
        });




        return gioHangChiTietResponses;
    }

    @Override
    public GioHangChiTiet getById(String id) {
        return rp.findById(Integer.parseInt(id)).orElse(null);
    }

    @Override
    public void delete(String id) {
        rp.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<GioHangChiTiet> getByIdGHList(String id) {
        return rp.getByIdGHList(id);
    }

    @Override
    public void deleteAll() {
        rp.deleteAll();
    }

}
