package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Добавление пользователя в БД
     */
    @PostMapping()
    public UserDto saveUser(@RequestBody User user) {
        log.info("Добавляем пользователя");
        return userServiceImpl.saveUser(user);
    }

    /**
     * Обновление пользователя
     */
    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody User user) {
        log.info("Обновляем пользователя");
        return userServiceImpl.updateUser(userId, user);
    }

    /**
     * Получение списка пользователей
     */
    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Получаем список пользователей");
        return userServiceImpl.getUsers();
    }

    /**
     * Получение пользователя по id
     */
    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        log.info("ПОлучаем пользователя по id");
        return userServiceImpl.findUserById(userId);
    }

    /**
     * Удаление всех пользователей
     */
    @DeleteMapping
    public void deleteUsers() {
        log.info("Удаляем всех пользователей");
        userServiceImpl.deleteUsers();
    }

    /**
     * Удаление пользователя по id
     */
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.info("Удаляем пользователя по id");
        userServiceImpl.deleteUserById(userId);
    }

}