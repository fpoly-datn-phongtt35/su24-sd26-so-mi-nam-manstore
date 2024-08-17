package com.example.manstore.repository;

import com.example.manstore.entity.ChiTietHoaDon;
import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;
        import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<ChiTietHoaDon, Integer> {

    @Override
    <S extends ChiTietHoaDon> S save(S entity);

    @Override
    <S extends ChiTietHoaDon> S saveAndFlush(S entity);

    @Override
    Optional<ChiTietHoaDon> findById(Integer aLong);

    @Override
    boolean existsById(Integer aLong);

    @Override
    void deleteById(Integer aLong);

    @Override
    Page<ChiTietHoaDon> findAll(Pageable pageable);

    @Query("select dhct from ChiTietHoaDon dhct where dhct.idHoaDon.id = :id")
    Page<ChiTietHoaDon> getByIdDH(@Param("id") String id, Pageable pageable);

    @Query("select dhct from ChiTietHoaDon dhct where dhct.idHoaDon.id = :id")
    List<ChiTietHoaDon> getByIdDHList(@Param("id") String id);

    @Query("select dhct from ChiTietHoaDon dhct where dhct.idHoaDon.id = :id")
    Page<ChiTietHoaDon> getPaginationByIdDHList(@Param("id") String id, Pageable pageable);

    @Query("select sum(d.soLuong) from ChiTietHoaDon d")
    Integer countSp();
}
