package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
public interface UserService {
    /**
     * Сохранение пользователя
     */
    UserDto saveUser(User user);

    /**
     * Обновление пользователя
     */
    UserDto updateUser(Long userId, User user);

    /**
     * Получение списка пользователей
     */
    List<UserDto> getUsers();

    /**
     * Получение пользователя по id
     */
    UserDto findUserById(Long userId);

    /**
     * Удаление всех пользователей
     */
    void deleteUsers();

    /**
     * Удаление пользователя по id
     */
    void deleteUserById(Long userId);
}
