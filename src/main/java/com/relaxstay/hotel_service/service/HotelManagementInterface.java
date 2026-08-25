package com.relaxstay.hotel_service.service;

import com.relaxstay.hotel_service.model.HotelRequest;
import com.relaxstay.hotel_service.model.HotelResponse;

import java.time.LocalDate;
import java.util.List;

public interface HotelManagementInterface {

    List<HotelResponse> getAllHotelDetails();
    List<HotelResponse> searchHotels(String place, LocalDate fromAvailableDate, LocalDate toAvailableDate);
    HotelResponse addHotel(HotelRequest hotelRequest);
    HotelResponse updateHotel(Integer id,HotelRequest hotelRequest);
    HotelResponse deleteHotel(Integer id);
    HotelResponse findHotelById(Integer id);
}
