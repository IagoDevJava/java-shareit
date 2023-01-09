package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.OwnerNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class ItemRepositoryImpl {
    private final ItemRepository repository;

    @Autowired
    public ItemRepositoryImpl(@Lazy ItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Редактирование вещи в БД
     * Подтверждение или отклонение запроса на бронирование. Может быть выполнено только владельцем вещи.
     */
    public ItemDto update(Long userId, Long itemId, Item item) {
        Optional<Item> itemById = repository.findById(itemId);
        if (itemById.isPresent()) {
            Item saveItem = itemById.get();
            saveItem.setName(item.getName());
            saveItem.setDescription(item.getDescription());
            saveItem.setAvailable(item.getAvailable());
            saveItem.setRequestId(item.getRequestId());
            if (Objects.equals(saveItem.getOwnerId(), userId)) {
                saveItem.setOwnerId(userId);
                repository.save(saveItem);
                log.info("Вещь № {} обновлена", item.getId());
                return ItemMapper.toItemDto(itemById.get());
            } else {
                log.warn("Пользователь {} не является владельцем вещи", itemById.get().getOwnerId());
                throw new OwnerNotFoundException("Пользователь не является владельцем вещи");
            }
        } else {
            log.warn("Вещь не найдена");
            throw new ItemNotFoundException("Вещь не найдена");
        }


    }
}