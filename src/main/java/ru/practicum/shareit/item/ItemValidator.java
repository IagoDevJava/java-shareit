package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

@Slf4j
public class ItemValidator {
    /**
     * Валидация вещи при создании
     */
    public static void isValidCreateItem(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getAvailable() == null || itemDto.getDescription() == null
                || itemDto.getDescription().isEmpty() || itemDto.getName().isEmpty()) {
            log.warn("Имя, описания и статус - обязательны к заполнению");
            throw new ValidationException("Имя, описания и статус - обязательны к заполнению");
        }

    }
}
