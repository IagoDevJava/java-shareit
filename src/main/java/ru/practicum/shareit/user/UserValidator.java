package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.UserDuplicateException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

@Slf4j
public class UserValidator {
    /**
     * Валидация пользователей при создании
     */
    public static void isValidCreateUser(User user) throws ValidationException {
        isValidUserToNull(user);
        isValidEmailUser(user);
        isValidIdUsers(user.getId());
    }

    /**
     * Валидация пользователей при обновлении
     */
    public static void isValidUpdateUser(User user) throws ValidationException {
        if (user.getEmail() == null && user.getName() == null) {
            log.warn("Ошибка в email: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "и email и name не могут быть = Null");
        }
        isValidIdUsers(user.getId());
    }

    /**
     * Валидация id пользователей
     */
    public static void isValidIdUsers(long id) {
        if (id < 0) {
            log.warn("Id пользователя %d отрицательный");
            throw new UserNotFoundException(String.format("Id пользователя %d отрицательный", id));
        }
    }

    /**
     * Проверка дубликатов
     */
    public static void isDuplicateEmail(List<User> users, User user) {
        for (User checkUser : users) {
            if (user.getEmail().equals(checkUser.getEmail())) {
                log.warn("Такой email уже существует");
                throw new UserDuplicateException(String.format("Такой email уже существует %s", user.getEmail()));
            }
        }
    }

    /**
     * Проверка существования пользователя в базе
     */
    public static void isUserInUsers(List<UserDto> users, long userId) {
        long check = 0;
        for (UserDto checkUser : users) {
            if (checkUser.getId() == userId) {
                check++;
            }
        }
        if (check == 0){
            log.warn("Такого пользователя нет в базе");
            throw new ValidationException("Такого пользователя нет в базе");
        }
    }

    /**
     * Валидация email пользователей при создании
     */
    public static void isValidEmailUser(User user) throws ValidationException {
        if (user.getEmail().isBlank()) {
            log.warn("Ошибка в email: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "email не должен быть пустым");
        }
        if (!user.getEmail().contains("@")) {
            log.warn("Ошибка в email: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "email должен содержать - \"@\"");
        }
    }

    /**
     * Проверка на null имени и email пользователей при создании
     */
    private static void isValidUserToNull(User user) {
        if (user.getEmail() == null) {
            log.warn("Ошибка в email: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "email должен быть");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("Имя пусто - заменено логином: {}", user);
            throw new ValidationException("Пользователь не соответствует условиям: " +
                    "name не должен быть пустым");
        }
    }
}
