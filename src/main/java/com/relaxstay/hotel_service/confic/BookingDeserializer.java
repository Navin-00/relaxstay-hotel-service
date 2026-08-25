package com.relaxstay.hotel_service.confic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.relaxstay.hotel_service.entity.Booking;
import org.apache.kafka.common.serialization.Deserializer;

public class BookingDeserializer implements Deserializer<Booking> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookingDeserializer() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Booking deserialize(String topic, byte[] data) {

        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, Booking.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Booking", e);
        }
    }
}
