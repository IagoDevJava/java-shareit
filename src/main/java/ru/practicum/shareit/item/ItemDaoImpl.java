package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.OwnerNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ItemDaoImpl implements ItemDao {
    private final UserRepository userRepository;
    private final List<Item> items = new ArrayList<>();
    private long idItem = 0;

    @Autowired
    public ItemDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Добавление вещи в БД
     */
    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        UserValidator.isValidIdUsers(userId);
        userRepository.findById(userId);
        if (itemDto != null && userId != 0) {
            ItemValidator.isValidCreateItem(itemDto);
            long thisId = generatedId();
            itemDto.setId(thisId);
            Item item = ItemMapper.toItem(itemDto, userId);
            item.setOwnerId(userId);
            items.add(item);
            log.info("Вещь № {} добавлена", itemDto.getId());
            return itemDto;
        } else {
            log.warn("Данные вещи или пользователя не переданы");
            throw new ValidationException("Данные вещи или пользователя не переданы");
        }
    }

    /**
     * Редактирование вещи в БД
     */
    @Override
    public ItemDto editItem(Long userId, Long itemId, ItemDto itemDto) {
        Item itemById = findItemById(itemId);
        if (itemById.getOwnerId() == userId) {
            ItemDto editItemDto = ItemDto.builder()
                    .id(itemId)
                    .name(Optional.ofNullable(itemDto.getName()).orElse(itemById.getName()))
                    .description(Optional.ofNullable(itemDto.getDescription()).orElse(itemById.getDescription()))
                    .available(Optional.ofNullable(itemDto.getAvailable()).orElse(itemById.getAvailable()))
                    .request(Optional.ofNullable(itemDto.getRequest()).orElse(itemById.getRequest()))
                    .build();
            Item item = ItemMapper.toItem(editItemDto, userId);
            item.setOwnerId(userId);
            items.remove(itemById);
            items.add(item);
            log.info("Вещь № {} обновлена", itemDto.getId());
            return editItemDto;
        } else {
            log.warn("Пользователь не является владельцем вещи");
            throw new OwnerNotFoundException("Пользователь не является владельцем вещи");
        }
    }

    /**
     * Получение информации о вещи в БД
     */
    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        Item itemById = findItemById(itemId);
        log.info("Получили вещь № {}", itemId);
        return ItemMapper.toItemDto(itemById);
    }

    /**
     * Получение владельцем списка его вещей в БД
     */
    @Override
    public List<ItemDto> getItems(Long userId) {
        List<ItemDto> itemsOfUser = new ArrayList<>();
        for (Item item : items) {
            if (item.getOwnerId() == userId) {
                itemsOfUser.add(ItemMapper.toItemDto(item));
            }
        }
        log.info("Получили список вещей пользователя № {} ", userId);
        return itemsOfUser;
    }

    /**
     * Поиск вещи потенциальным арендатором
     */
    @Override
    public List<ItemDto> getItemsByRequest(Long userId, String text) {
        List<ItemDto> foundItems = new ArrayList<>();
        for (Item item : items) {
            String s1 = item.getName() + " " + item.getDescription();
            if (s1.toLowerCase().contains(text.toLowerCase()) && item.getAvailable() && !text.isEmpty()) {
                foundItems.add(ItemMapper.toItemDto(item));
            }
        }
        log.info("Получили список вещей по запросу {}", text);
        return foundItems;
    }

    /**
     * Получение вещи из БД
     */
    private Item findItemById(long id) {
        Item findItem = null;
        for (Item item : items) {
            if (item.getId() == id) {
                findItem = item;
            }
        }
        if (findItem != null) {
            return findItem;
        } else {
            log.warn("Вещи с id %d в базе нет");
            throw new ItemNotFoundException(String.format("Вещи с id %d в базе нет", id));
        }
    }

    /**
     * Генерирование id
     */
    private Long generatedId() {
        return ++idItem;
    }
}
