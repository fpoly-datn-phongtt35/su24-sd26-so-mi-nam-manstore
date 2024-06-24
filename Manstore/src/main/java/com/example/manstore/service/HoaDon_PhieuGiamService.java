package com.example.manstore.service;

import com.example.manstore.dto.request.HoaDon_PhieuGiamRequest;
import com.example.manstore.entity.HoadonPhieugiam;

import java.util.List;


public interface HoaDon_PhieuGiamService {

    List<HoaDon_PhieuGiamRequest> getAllPhieuGiam(Integer page);

    HoaDon_PhieuGiamRequest createPhieuGiam(HoadonPhieugiam hdPhieuGiam);

//    HoaDon_PhieuGiamRequest updatePhieuGiam(HoadonPhieugiam hdPhieuGiam, Integer id);

}
