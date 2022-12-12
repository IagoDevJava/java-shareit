package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Добавление вещи в БД
     */
    @PostMapping()
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Добавляем вещь № {}", itemDto.getId());
        return itemService.addItem(userId, itemDto);
    }

    /**
     * Редактирование вещи в БД
     */
    @PatchMapping("/{itemId}")
    public ItemDto editItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                            @RequestBody ItemDto itemDto) {
        log.info("Обновляем вещь № {}", itemDto.getId());
        return itemService.editItem(userId, itemId, itemDto);
    }

    /**
     * Получение информации о вещи в БД
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        log.info("Получаем вещь № {}", itemId);
        return itemService.getItemById(userId, itemId);
    }

    /**
     * Получение владельцем списка его вещей в БД
     */
    @GetMapping()
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получаем список вещей пользователя № {} ", userId);
        return itemService.getItems(userId);
    }

    /**
     * Поиск вещи потенциальным арендатором
     */
    @GetMapping("/search")
    public List<ItemDto> getItemsByRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text) {
        log.info("Запрашиваем список вещей по запросу {}", text);
        return itemService.getItemsByRequest(userId, text);
    }
}
