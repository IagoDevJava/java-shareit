package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    /**
     * Конвертация Booking в BookingDto
     */
    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .itemId(booking.getItemId())
                .bookerId(booking.getBookerId())
                .status(booking.getStatus())
                .build();
    }

    /**
     * Конвертация BookingDto в Booking
     */
    public static Booking toBooking(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .itemId(bookingDto.getItemId())
                .bookerId(bookingDto.getBookerId())
                .status(bookingDto.getStatus())
                .build();
    }

    /**
     * Конвертация списка Booking в BookingDto
     */
    public static List<BookingDto> mapToBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> result = new ArrayList<>();

        for (Booking booking : bookings) {
            result.add(toBookingDto(booking));
        }

        return result;
    }
}