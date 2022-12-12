package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemDao {
    /**
     * Добавление вещи в БД
     */
    ItemDto addItem(Long userId, ItemDto itemDto);

    /**
     * Редактирование вещи в БД
     */
    ItemDto editItem(Long userId, Long itemId, ItemDto itemDto);

    /**
     * Получение информации о вещи в БД
     */
    ItemDto getItemById(Long userId, Long itemId);

    /**
     * Получение владельцем списка его вещей в БД
     */
    List<ItemDto> getItems(Long userId);

    /**
     * Поиск вещи потенциальным арендатором
     */
    List<ItemDto> getItemsByRequest(Long userId, String text);
}
