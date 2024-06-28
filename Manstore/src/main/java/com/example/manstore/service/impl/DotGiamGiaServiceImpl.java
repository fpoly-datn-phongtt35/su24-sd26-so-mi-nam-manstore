package com.example.manstore.service.impl;

import com.example.manstore.entity.DotGiamGia;
import com.example.manstore.repository.DotGiamGiaRepository;
import com.example.manstore.service.DotGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {

    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepo;

    @Override
    public Page<DotGiamGia> findAll(LocalDate start, LocalDate end, String typePromotion, Pageable pageable) {
        Page<DotGiamGia> page;
        if (start == null || end == null) {
            if (typePromotion.equalsIgnoreCase("null")) {
            page = dotGiamGiaRepo.findByDate(LocalDate.of(2000, 1, 1),
                    LocalDate.of(2200, 1, 1), pageable);
            return page;
            } else if (!typePromotion.equalsIgnoreCase("null")) {
                boolean type;
                type = typePromotion.equalsIgnoreCase("true");
                page = dotGiamGiaRepo.findByPromotionType(type, pageable);
                return page;
            } else{
                boolean type;
                type = typePromotion.equalsIgnoreCase("true");
                page = dotGiamGiaRepo.findByPromotionType(type, pageable);
                return page;
            }
        } else {
            LocalDate dateStart = start.atStartOfDay().toLocalDate();
            LocalDate dateEnd = end.atStartOfDay().toLocalDate();
            LocalDate fisrtTime = LocalDate.of(dateStart.getYear(), dateStart.getMonth(),
                    dateStart.getDayOfMonth());
            LocalDate lastTime = LocalDate.of(dateEnd.getYear(), dateEnd.getMonth(),
                    dateEnd.getDayOfMonth());
            if (typePromotion.equalsIgnoreCase("null")) {
                page = dotGiamGiaRepo.findByDate(fisrtTime,
                        lastTime, pageable);
                return page;
            } else if (!typePromotion.equalsIgnoreCase("null")) {
                boolean type;
                type = typePromotion.equalsIgnoreCase("true");
                page = dotGiamGiaRepo.findByDateAndPromotionType(fisrtTime,
                        lastTime, type, pageable);
                return page;
            } else {
            boolean type;
            type = typePromotion.equalsIgnoreCase("true");
            page = dotGiamGiaRepo.findByAll(fisrtTime,
                    lastTime,  type, pageable);
            return page;
        }
    }
    }


    @Override
    public List<DotGiamGia> getAll() {
        return dotGiamGiaRepo.findAll();
    }

    @Override
    public Optional<DotGiamGia> findById(Integer id) {
        return dotGiamGiaRepo.findById(id);
    }

    @Override
    public DotGiamGia create(DotGiamGia dotGiamGia) {
        return dotGiamGiaRepo.save(dotGiamGia);
    }

}
