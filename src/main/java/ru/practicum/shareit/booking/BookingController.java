package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Добавление нового бронирования.
     */
    @PostMapping
    public BookingDto addNewBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @Valid @RequestBody Booking booking) {
        return bookingService.addNewBooking(userId, booking);
    }

    /**
     * Подтверждение или отклонение запроса на бронирование.
     */
    @PatchMapping("/{bookingId}?approved={approved}")
    public BookingDto updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long bookingId,
                                    @PathVariable Boolean approved) {
        return bookingService.updateBooking(userId, bookingId, approved);
    }

    /**
     * Получение данных о конкретном бронировании (включая его статус)
     */
    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    /**
     * Получение списка всех бронирований текущего пользователя
     */
    @GetMapping("?state={state}")
    public List<BookingDto> getBookingsByBookerId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @PathVariable(required = false) String state) {
        return bookingService.getBookingsByBookerId(userId, Objects.requireNonNullElse(state, "ALL"));
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     */
    @GetMapping("/owner?state={state}")
    public List<BookingDto> getBookingAllItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                        @PathVariable(required = false) String state) {
        return bookingService.getBookingAllItemsByOwnerId(userId, Objects.requireNonNullElse(state, "ALL"));
    }
}
