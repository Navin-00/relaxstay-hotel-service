package com.relaxstay.hotel_service.consumer;

import com.relaxstay.hotel_service.entity.Booking;
import com.relaxstay.hotel_service.service.HotelManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.relaxstay.hotel_service.enumeration.BookingStatus.CANCELLED;
import static com.relaxstay.hotel_service.enumeration.BookingStatus.CONFIRMED;
@Slf4j
@Component
@RequiredArgsConstructor
public class HotelUpdateRequestConsumer {

    private final HotelManagementService hotelManagementService;

    @KafkaListener(
            topics = "booking-topic",
            groupId = "hotel-service-group"
    )
    public void consumeBooking(Booking booking) {

        log.info("Booking received from Kafka:");
        log.info(booking.toString());

        if (booking.getStatus() == null) {
            return;
        }

        switch (booking.getStatus()) {
            case CANCELLED -> {
               log.info(
                        "Cancelled booking received. Updating hotel rooms..."
                );

                hotelManagementService.restoreRooms(booking.getHotelId(), booking.getNumberOfRooms());
            }

            case CONFIRMED -> {
                log.info(
                        "Confirmed booking received."
                );
            }
        }
    }
}
