package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

@Slf4j
@Component
public class UserRepositoryImpl {
    private final UserRepository repository;

    @Autowired
    public UserRepositoryImpl(@Lazy UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Обновление пользователя
     */
    public UserDto update(Long userId, User user) {
        if (user.getName() != null && user.getEmail() == null && repository.findById(userId).isPresent()) {
            repository.findById(userId).get().setName(user.getName());
        } else if (user.getName() != null && user.getEmail() != null) {
            UserValidator.isValidEmailUser(user);
            repository.findById(userId).get().setName(user.getName());
            repository.findById(userId).get().setEmail(user.getEmail());
        } else {
            UserValidator.isValidEmailUser(user);
            repository.findById(userId).get().setEmail(user.getEmail());
        }

        User saveUser = repository.findById(userId).get();
        repository.save(saveUser);

        log.info("Обновили пользователя с id {}", userId);
        return UserMapper.toUserDto(repository.findById(userId).get());
    }
}