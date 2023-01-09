package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;

@Slf4j
public class ItemValidator {
    /**
     * Валидация вещи при создании
     */
    public static void isValidCreateItem(Item item) {
        if (item.getName() == null || item.getAvailable() == null || item.getDescription() == null
                || item.getDescription().isEmpty() || item.getName().isEmpty()) {
            log.warn("Имя, описание и статус - обязательны к заполнению");
            throw new ValidationException("Имя, описания и статус - обязательны к заполнению");
        }

    }
}
