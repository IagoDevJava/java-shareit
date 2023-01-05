package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingRepositoryImpl repositoryImp;
    private final ItemRepository itemRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, BookingRepositoryImpl repositoryImp, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.repositoryImp = repositoryImp;
        this.itemRepository = itemRepository;
    }

    /**
     * Добавление нового бронирования
     */
    @Override
    public BookingDto addNewBooking(Long userId, Booking booking) {
        booking.setStatus(Status.WAITING);
        Booking saveBooking = bookingRepository.save(booking);
        log.info("Создали новое бронирование {}", booking.getId());
        return BookingMapper.toBookingDto(saveBooking);
    }

    /**
     * Подтверждение или отклонение запроса на бронирование.
     */
    @Override
    public BookingDto updateBooking(Long userId, Long itemId, Boolean approved) {
        return repositoryImp.update(userId, itemId, approved);
    }

    /**
     * Получение данных о конкретном бронировании (включая его статус)
     */
    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        BookingDto bookingDto;
        Optional<Booking> bookingById = bookingRepository.findById(bookingId);
        if (bookingById.isPresent()) {
            Optional<Item> itemById = itemRepository.findById(bookingById.get().getItemId());
            if (itemById.isPresent()) {
                if (Objects.equals(bookingById.get().getBookerId(), userId)
                        && itemById.get().getOwnerId().equals(userId)) {
                    bookingDto = BookingMapper.toBookingDto(bookingById.get());
                    log.info("Получили бронирование №{}", bookingId);
                    return bookingDto;
                } else {
                    throw new UserNotFoundException("Пользователь не является автором бронирования или владельцем вещи");
                }
            } else {
                throw new ItemNotFoundException("Вещь не найдена");
            }
        } else {
            throw new BookingNotFoundException("Бронирование не найдено");
        }
    }

    /**
     * Получение списка всех бронирований текущего пользователя.
     */
    @Override
    public List<BookingDto> getBookingsByBookerId(Long userId, String state) {
        List<Booking> bookingsByBookerId = bookingRepository.findBookingByBookerId(userId);
        List<Booking> result = getBookingsByState(state, bookingsByBookerId);
        Collections.sort(result);
        Collections.reverse(result);
        return BookingMapper.mapToBookingDto(result);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     */
    @Override
    public List<BookingDto> getBookingAllItemsByOwnerId(Long userId, String state) {
        if (!itemRepository.findItemByOwnerId(userId).isEmpty()) {
            List<Item> itemsByOwnerId = itemRepository.findItemByOwnerId(userId);
            List<Booking> allBookingsOwnerId = new ArrayList<>();
            for (Item item : itemsByOwnerId) {
                allBookingsOwnerId.addAll(bookingRepository.findBookingByItemId(item.getId()));
            }
            List<Booking> result = getBookingsByState(state, allBookingsOwnerId);
            Collections.sort(result);
            Collections.reverse(result);
            return BookingMapper.mapToBookingDto(result);
        } else {
            throw new ItemNotFoundException(String.format("У пользователя №%d нет вещей", userId));
        }
    }

    private List<Booking> getBookingsByState(String state, List<Booking> bookings) {
        List<Booking> result = new ArrayList<>();
        switch (state) {
            case ("CURRENT"):
                for (Booking booking : bookings) {
                    if (booking.getStart().isBefore(LocalDateTime.now())
                            && booking.getEnd().isAfter(LocalDateTime.now())) {
                        result.add(booking);
                    }
                }
                log.info("Получили бронирования со статусом {}", state);
                break;
            case ("PAST"):
                for (Booking booking : bookings) {
                    if (booking.getEnd().isBefore(LocalDateTime.now())) {
                        result.add(booking);
                    }
                }
                log.info("Получили бронирования со статусом {}", state);
                break;
            case ("FUTURE"):
                for (Booking booking : bookings) {
                    if (booking.getStart().isAfter(LocalDateTime.now())) {
                        result.add(booking);
                    }
                }
                log.info("Получили бронирования со статусом {}", state);
                break;
            case ("WAITING"):
                for (Booking booking : bookings) {
                    if (booking.getStatus().equals(Status.WAITING)) {
                        result.add(booking);
                    }
                }
                log.info("Получили бронирования со статусом {}", state);
                break;
            case ("REJECTED"):
                for (Booking booking : bookings) {
                    if (booking.getStatus().equals(Status.REJECTED)) {
                        result.add(booking);
                    }
                }
                log.info("Получили бронирования со статусом {}", state);
                break;
            default:
                result.addAll(bookings);
                log.info("Получили бронирования со статусом {}", state);
        }
        return result;
    }
}