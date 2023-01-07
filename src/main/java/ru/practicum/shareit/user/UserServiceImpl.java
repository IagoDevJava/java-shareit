package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepositoryImpl userRepositoryImpl) {
        this.userRepository = userRepository;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    /**
     * Сохранение пользователя
     */
    @Override
    public UserDto saveUser(User user) {
        UserValidator.isValidUserToNull(user);
        UserValidator.isValidEmailUser(user);
        User saveUser = userRepository.save(user);
        log.info("Сохранили пользователя {}", saveUser.getName());
        return UserMapper.toUserDto(saveUser);
    }

    /**
     * Обновление пользователя
     */
    @Override
    public UserDto updateUser(Long userId, User user) {
        return userRepositoryImpl.update(userId, user);
    }

    /**
     * Получение списка пользователей
     */
    @Override
    public List<UserDto> getUsers() {
        List<User> allUser = userRepository.findAll();
        log.info("Вернули список из {} пользователей", allUser.size());
        return UserMapper.mapToUserDto(allUser);
    }

    /**
     * Получение пользователя по id
     */
    @Override
    public UserDto findUserById(Long userId) {
        Optional<User> optUserById = userRepository.findById(userId);
        if (optUserById.isPresent()) {
            User user = optUserById.get();
            log.info("Получили пользователя по id {}", userId);
            return UserMapper.toUserDto(user);
        } else {
            log.warn("User not found");
            throw new UserNotFoundException(String.format("Пользователя с %d не существует", userId));
        }
    }

    /**
     * Удаление всех пользователей
     */
    @Override
    public void deleteUsers() {
        log.info("Удалили всех пользователей");
        userRepository.deleteAll();
    }

    /**
     * Удаление пользователя по id
     */
    @Override
    public void deleteUserById(Long userId) {
        log.info("Удалили пользователя по id {}", userId);
        userRepository.deleteById(userId);
    }
}