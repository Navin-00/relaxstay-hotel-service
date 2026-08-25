package com.relaxstay.hotel_service.controller;

import com.relaxstay.hotel_service.model.HotelRequest;
import com.relaxstay.hotel_service.model.HotelResponse;
import com.relaxstay.hotel_service.service.HotelManagementInterface;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HotelManagementController {
    public final HotelManagementInterface hotelManagementInterface;

    @GetMapping("/hotel/hotelLists")
    public List<HotelResponse> getAllHotelDetails(){
        return hotelManagementInterface.getAllHotelDetails();
    }

    @GetMapping("/hotel/search")
    public List<HotelResponse> searchHotel(
             @RequestParam(required = false) String place,
             @RequestParam(required = false) LocalDate fromAvailableDate,
             @RequestParam(required = false) LocalDate toAvailableDate)
    {
        return hotelManagementInterface.searchHotels(place,fromAvailableDate,toAvailableDate);
    }

    @PostMapping("/hotel/add")
    public HotelResponse addHotel(@RequestBody HotelRequest hotelRequest){
        return hotelManagementInterface.addHotel(hotelRequest);
    }
    @PutMapping("/hotel/update/{id}")
    public HotelResponse updateHotel(@PathVariable Integer id, @RequestBody HotelRequest hotelRequest){
        return hotelManagementInterface.updateHotel(id,hotelRequest);
    }
    @DeleteMapping("/hotel/delete/{id}")
    public HotelResponse deleteHotel(@PathVariable Integer id){
        return hotelManagementInterface.deleteHotel(id);
    }
    @GetMapping("/hotel/findById/{id}")
    public HotelResponse findHotelById(@PathVariable Integer id){
        return hotelManagementInterface.findHotelById(id);
    }
}
