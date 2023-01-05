package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Service
public interface BookingService {
    /**
     * Добавление нового бронирования.
     */
    BookingDto addNewBooking(Long userId, Booking booking);

    /**
     * Подтверждение или отклонение запроса на бронирование.
     */
    BookingDto updateBooking(Long userId, Long bookingId, Boolean approved);

    /**
     * Получение данных о конкретном бронировании (включая его статус)
     */
    BookingDto getBookingById(Long userId, Long bookingId);

    /**
     * Получение списка всех бронирований текущего пользователя
     */
    List<BookingDto> getBookingsByBookerId(Long userId, String state);

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     */
    List<BookingDto> getBookingAllItemsByOwnerId(Long userId, String all);
}