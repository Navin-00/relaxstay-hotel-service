package com.relaxstay.hotel_service.repository;

import com.relaxstay.hotel_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelManagementRepository extends JpaRepository<Hotel, Integer> {

    Optional<Hotel> findByPlace(String place);

    @Query("""
    SELECT h FROM Hotel h
    WHERE (:place IS NULL OR LOWER(h.place) LIKE LOWER(CONCAT('%', :place, '%')))
      AND (
            :fromAvailableDate IS NULL
            OR (
                h.fromAvailableDate <= :fromAvailableDate
                AND h.toAvailableDate >= :fromAvailableDate
            )
          )
      AND (
            :toAvailableDate IS NULL
            OR h.toAvailableDate >= :toAvailableDate
          )
""")
    List<Hotel> searchHotels(
            @Param("place") String place,
            @Param("fromAvailableDate") LocalDate fromAvailableDate,
            @Param("toAvailableDate") LocalDate toAvailableDate
    );
}
