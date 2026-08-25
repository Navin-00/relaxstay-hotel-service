package com.relaxstay.hotel_service.entity;

import com.relaxstay.hotel_service.enumeration.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;
    private Long userId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfRooms;
    private Integer totalAmount;
    private BookingStatus status;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfRooms=" + numberOfRooms +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}

