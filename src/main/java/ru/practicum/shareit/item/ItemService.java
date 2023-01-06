package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public interface ItemService {
    /**
     * Добавление вещи в БД
     */
    ItemDto addItem(Long userId, Item item);

    /**
     * Редактирование вещи в БД
     */
    ItemDto editItem(Long userId, Long itemId, Item item);

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

    /**
     * Добавление отзыва на вещь
     */
    ItemDto addCommentToItem(Long userId, Long itemId, Comment comment);
}