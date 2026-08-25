package com.relaxstay.hotel_service.service;

import com.relaxstay.hotel_service.entity.Hotel;
import com.relaxstay.hotel_service.exception.HotelNotFoundException;
import com.relaxstay.hotel_service.model.HotelRequest;
import com.relaxstay.hotel_service.model.HotelResponse;
import com.relaxstay.hotel_service.repository.HotelManagementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@AllArgsConstructor
public class HotelManagementService implements HotelManagementInterface {
    public final HotelManagementRepository hotelManagementRepository;

    @Override
    public List<HotelResponse> getAllHotelDetails() {
        List<Hotel> hotelsLists = hotelManagementRepository.findAll();
        return hotelsLists.stream()
                .map(this::getHotelResponse)
                .toList();
    }

    @Override
    public List<HotelResponse> searchHotels(String place,LocalDate fromAvailableDate,LocalDate toAvailableDate) {
        List<Hotel> hotels = hotelManagementRepository.searchHotels(place, fromAvailableDate, toAvailableDate);
        return hotels.stream()
                .map(hotel -> getHotelResponse(hotel))
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse addHotel(HotelRequest hotelRequest) {

        Hotel hotelDetails = Hotel.builder()
                .name(hotelRequest.getName())
                .place(hotelRequest.getPlace())
                .price(hotelRequest.getPrice())
                .noOfRoomsAvailable(hotelRequest.getNoOfRoomsAvailable())
                .fromAvailableDate(LocalDate.parse(hotelRequest.getFromAvailableDate()))
                .toAvailableDate(LocalDate.parse(hotelRequest.getToAvailableDate()))
                .build();
        Hotel savedHotelDetail = hotelManagementRepository.save(hotelDetails);
        return getHotelResponse(savedHotelDetail);
    }

    @Override
    public HotelResponse updateHotel(Integer id, HotelRequest hotelRequest) {
        Hotel hotelDetail = hotelManagementRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel is not Listed"));
        hotelDetail = Hotel.builder()
                .name(hotelRequest.getName() == null ? hotelDetail.getName() : hotelRequest.getName())
                .id(hotelDetail.getId())
                .price(hotelRequest.getPrice() == null ? hotelDetail.getPrice() : hotelRequest.getPrice())
                .place(hotelRequest.getPlace() == null ? hotelDetail.getPlace() : hotelRequest.getPlace())
                .fromAvailableDate(hotelRequest.getFromAvailableDate() == null ? hotelDetail.getFromAvailableDate() : LocalDate.parse(hotelRequest.getFromAvailableDate()))
                .toAvailableDate(hotelRequest.getToAvailableDate() == null ? hotelDetail.getToAvailableDate() : LocalDate.parse(hotelRequest.getToAvailableDate()))
                .noOfRoomsAvailable(hotelRequest.getNoOfRoomsAvailable() == null ? hotelDetail.getNoOfRoomsAvailable() : hotelRequest.getNoOfRoomsAvailable())
                .build();
        hotelManagementRepository.save(hotelDetail);
        return getHotelResponse(hotelDetail);
    }

    @Override
    public HotelResponse deleteHotel(Integer id) {
        Hotel hotelDetail=hotelManagementRepository.findById(id).orElseThrow(()->new HotelNotFoundException("Hotel is not listed"));
        hotelManagementRepository.deleteById(id);
        return getHotelResponse(hotelDetail);
    }

    public HotelResponse findHotelById(Integer id){
        Hotel hotelDetail=hotelManagementRepository.findById(id).orElseThrow(()->new HotelNotFoundException("Hotel is not listed"));
        return getHotelResponse(hotelDetail);
    }

    public void restoreRooms(Long hotelId, Integer numberOfRooms) {
        log.info("reached restore rooms");
        Hotel hotel = hotelManagementRepository
                .findById(Math.toIntExact(hotelId))
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found")
                );

        hotel.setNoOfRoomsAvailable(
                hotel.getNoOfRoomsAvailable() + numberOfRooms
        );

        hotelManagementRepository.save(hotel);
        log.info("rooms restored");
        System.out.println(
                "Rooms restored successfully for hotel: " + hotelId
        );
    }

    public HotelResponse getHotelResponse(Hotel savedLists) {
        return HotelResponse.builder()
                .id(savedLists.getId())
                .name(savedLists.getName())
                .place(savedLists.getPlace())
                .price(savedLists.getPrice())
                .fromAvailableDate(savedLists.getFromAvailableDate())
                .toAvailableDate(savedLists.getToAvailableDate())
                .noOfRoomsAvailable((savedLists.getNoOfRoomsAvailable()))
                .build();
    }
}
