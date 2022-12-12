package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Добавление пользователя в БД
     */
    @PostMapping()
    public UserDto addUser(@RequestBody User user) {
        log.info("Добавляем пользователя");
        return userService.addUser(user);
    }

    /**
     * Обновление пользователя
     */
    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody User user) {
        log.info("Обновляем пользователя");
        return userService.updateUser(userId, user);
    }

    /**
     * Получение списка пользователей
     */
    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Получаем список пользователей");
        return userService.getUsers();
    }

    /**
     * Получение пользователя по id
     */
    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        log.info("ПОлучаем пользователя по id");
        return userService.findUserById(userId);
    }

    /**
     * Удаление всех пользователей
     */
    @DeleteMapping
    public void deleteUsers() {
        log.info("Удаляем всех пользователей");
        userService.deleteUsers();
    }

    /**
     * Удаление пользователя по id
     */
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.info("Удаляем пользователя по id");
        userService.deleteUserById(userId);
    }

}
