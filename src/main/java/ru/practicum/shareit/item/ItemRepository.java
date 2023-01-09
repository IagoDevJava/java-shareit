package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Получение владельцем списка его вещей в БД
     */
    List<Item> findItemByOwnerId(Long id);

    /**
     * Поиск вещи потенциальным арендатором
     */
    @Query(value = "select * " +
            "from ITEMS as it " +
            "where POSITION(?2 IN concat(NAME, ' ', DESCRIPTION)) > 0", nativeQuery = true)
    List<Item> findItemsByRequest(Long id, String text);
}