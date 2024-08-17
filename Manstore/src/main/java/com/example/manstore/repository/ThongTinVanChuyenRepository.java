package com.example.manstore.repository;
import com.example.manstore.entity.ThongTinVanChuyen;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThongTinVanChuyenRepository extends JpaRepository<ThongTinVanChuyen,Integer> {


    @Override
    <S extends ThongTinVanChuyen> S saveAndFlush(S entity);

    @Override
    List<ThongTinVanChuyen> findAll();

    @Override
    <S extends ThongTinVanChuyen> S save(S entity);

    @Override
    Optional<ThongTinVanChuyen> findById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    List<ThongTinVanChuyen> findAll(Sort sort);

}
