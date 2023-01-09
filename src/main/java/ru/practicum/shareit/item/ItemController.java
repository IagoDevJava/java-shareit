package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemServiceImpl itemServiceImpl;

    @Autowired
    public ItemController(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }

    /**
     * Добавление вещи в БД
     */
    @PostMapping()
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody Item item) {
        log.info("Добавляем вещь № {}", item.getId());
        return itemServiceImpl.addItem(userId, item);
    }

    /**
     * Редактирование вещи в БД
     */
    @PatchMapping("/{itemId}")
    public ItemDto editItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId, @RequestBody Item item) {
        log.info("Обновляем вещь № {}", item.getId());
        return itemServiceImpl.editItem(userId, itemId, item);
    }

    /**
     * Получение информации о вещи в БД
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        log.info("Получаем вещь № {}", itemId);
        return itemServiceImpl.getItemById(userId, itemId);
    }

    /**
     * Получение владельцем списка его вещей в БД
     */
    @GetMapping()
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получаем список вещей пользователя № {} ", userId);
        return itemServiceImpl.getItems(userId);
    }

    /**
     * Поиск вещи потенциальным арендатором
     */
    @GetMapping("/search")
    public List<ItemDto> getItemsByRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text) {
        log.info("Запрашиваем список вещей по запросу {}", text);
        return itemServiceImpl.getItemsByRequest(userId, text);
    }

    /**
     * Добавление отзыва на вещь
     */
    @PostMapping("/items/{itemId}/comment")
    public ItemDto addCommentToItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long itemId,
                                    @RequestBody Comment comment) {
        return itemServiceImpl.addCommentToItem(userId, itemId, comment);
    }
}