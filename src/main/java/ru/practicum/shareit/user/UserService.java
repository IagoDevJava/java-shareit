package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Создание пользователя
     */
    public UserDto addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * Обновление пользователя
     */
    public UserDto updateUser(Long userId, User user) {
        return userDao.updateUser(userId, user);
    }

    /**
     * Получение пользователя по id
     */
    public UserDto findUserById(Long userId) {
        return userDao.getUserDtoById(userId);
    }

    /**
     * Получение списка пользователей
     */
    public List<UserDto> getUsers() {
        return userDao.getUsers();
    }

    /**
     * Удаление всех пользователей
     */
    public void deleteUsers() {
        userDao.deleteUsers();
    }

    /**
     * Удаление пользователя по id
     */
    public void deleteUserById(Long userId) {
        userDao.deleteUserById(userId);
    }
}
