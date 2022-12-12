package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Создание пользователя
     */
    UserDto addUser(User user);

    /**
     * Обновление пользователя
     */
    UserDto updateUser(Long userId, User user);

    /**
     * Получение пользователя по id
     */
    UserDto getUserDtoById(Long userId);

    /**
     * Получение списка пользователей
     */
    List<UserDto> getUsers();

    /**
     * Удаление всех пользователей
     */
    void deleteUsers();

    /**
     * Удаление пользователя по id
     */
    void deleteUserById(Long userId);
}
