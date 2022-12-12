package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public class ItemService {
    private final ItemDao itemDao;

    @Autowired
    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * Добавление вещи в БД
     */
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        return itemDao.addItem(userId, itemDto);
    }

    /**
     * Редактирование вещи в БД
     */
    public ItemDto editItem(Long userId, Long itemId, ItemDto itemDto) {
        return itemDao.editItem(userId, itemId, itemDto);
    }

    /**
     * Получение информации о вещи в БД
     */
    public ItemDto getItemById(Long userId, Long itemId) {
        return itemDao.getItemById(userId, itemId);
    }

    /**
     * Получение владельцем списка его вещей в БД
     */
    public List<ItemDto> getItems(Long userId) {
        return itemDao.getItems(userId);
    }

    /**
     * Поиск вещи потенциальным арендатором
     */
    public List<ItemDto> getItemsByRequest(Long userId, String text) {
        return itemDao.getItemsByRequest(userId, text);
    }
}
