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
    private final UserRepository repository;
    private final UserRepositoryImpl userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserRepositoryImpl userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    /**
     * Сохранение пользователя
     */
    @Override
    public UserDto saveUser(User user) {
        User saveUser = repository.save(user);
        log.info("Сохранили пользователя {}", user.getId());
        return UserMapper.toUserDto(saveUser);
    }

    /**
     * Обновление пользователя
     */
    @Override
    public UserDto updateUser(Long userId, User user) {
        return userRepository.update(userId, user);
    }

    /**
     * Получение списка пользователей
     */
    @Override
    public List<UserDto> getUsers() {
        List<User> allUser = repository.findAll();
        log.info("Вернули список из {} пользователей", allUser.size());
        return UserMapper.mapToUserDto(allUser);
    }

    /**
     * Получение пользователя по id
     */
    @Override
    public UserDto findUserById(Long userId) {
        Optional<User> optUserById = repository.findById(userId);
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
        repository.deleteAll();
    }

    /**
     * Удаление пользователя по id
     */
    @Override
    public void deleteUserById(Long userId) {
        log.info("Удалили пользователя по id {}", userId);
        repository.deleteById(userId);
    }
}