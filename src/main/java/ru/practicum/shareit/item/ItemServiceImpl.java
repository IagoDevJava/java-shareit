package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ItemRepositoryImpl itemRepository;
//    private final BookingRepository bookingRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository repository, ItemRepositoryImpl itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    /**
     * Добавление вещи в БД
     */
    @Override
    public ItemDto addItem(Long userId, Item item) {
        UserValidator.isValidIdUsers(userId);
        Item saveItem = repository.save(item);
        log.info("Вещь № {} добавлена", item.getId());
        return ItemMapper.toItemDto(saveItem);
    }

    /**
     * Редактирование вещи в БД
     */
    @Override
    public ItemDto editItem(Long userId, Long itemId, Item item) {
        UserValidator.isValidIdUsers(userId);
        return itemRepository.update(userId, itemId, item);
    }

    /**
     * Получение информации о вещи в БД
     */
    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        Optional<Item> itemById = repository.findById(itemId);
        if (itemById.isPresent()) {
            ItemDto itemDto = ItemMapper.toItemDto(itemById.get());
//            List<Booking> bookingByItemId = bookingRepository.findBookingByItemId(itemId);
//            for (Booking booking : bookingByItemId) {
//                if (booking.getStatus().equals(Status.APPROVED)) {
//                    itemDto.setStart(booking.getStart());
//                    itemDto.setEnd(booking.getEnd());
//                }
//            }
            log.info("Получили вещь № {}", itemId);
            return itemDto;
        } else {
            throw new ItemNotFoundException("Такой вещи не существует");
        }
    }

    /**
     * Получение владельцем списка его вещей в БД
     */
    @Override
    public List<ItemDto> getItems(Long userId) { //TODO Допилить даты
        List<Item> itemByOwnerId = repository.findItemByOwnerId(userId);
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemByOwnerId) {
//            List<Booking> bookingByItemId = bookingRepository.findBookingByItemId(item.getId());
//            ItemDto itemDto = ItemMapper.toItemDto(item);
//            for (Booking booking : bookingByItemId) {
//                itemDto.setStart(booking.getStart());
//                itemDto.setEnd(booking.getEnd());
//            }
//            result.add(itemDto);
        }
        log.info("Пользователь {} получил список своих вещей {}", userId, itemByOwnerId.size());
        return result;
    }

    /**
     * Поиск вещи потенциальным арендатором
     */
    @Override
    public List<ItemDto> getItemsByRequest(Long userId, String text) {
        List<Item> itemsByRequest = repository.findItemsByRequest(userId, text);
        return ItemMapper.mapToItemDto(itemsByRequest);
    }
}