package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.OwnerNotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.Optional;

@Slf4j
@Component
public class BookingRepositoryImpl {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public BookingRepositoryImpl(BookingRepository bookingRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Подтверждение или отклонение запроса на бронирование
     */
    public BookingDto update(Long userId, Long bookingId, Boolean approved) {
        Optional<Booking> bookingById = bookingRepository.findById(bookingId);
        //Понимаю, что 3 if-а много, но не придумал, чтьо с этим сделать
        if (bookingById.isPresent()) {
            Optional<Item> itemById = itemRepository.findById(bookingById.get().getItemId());
            if (itemById.isPresent() && itemById.get().getOwnerId().equals(userId)) {
                if (approved) {
                    bookingById.get().setStatus(Status.APPROVED);
                } else {
                    bookingById.get().setStatus(Status.REJECTED);
                }

//            bookingRepository.save(bookingById.get());

            } else {
                log.warn("Пользователь не является владельцем вещи");
                throw new OwnerNotFoundException("Пользователь не является владельцем вещи");
            }
            log.info("Статус бронирования № {} обновлен", bookingId);
            return BookingMapper.toBookingDto(bookingById.get());
        } else {
            throw new BookingNotFoundException("Бронирование не найдено");
        }
    }
}