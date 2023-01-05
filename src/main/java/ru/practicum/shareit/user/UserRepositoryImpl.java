package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.UserNotFoundException;
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
        if (user != null) {
            UserValidator.isValidUpdateUser(user);
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

//            User saveUser = repository.findById(userId).get();
//            repository.save(saveUser);

            log.info("Обновили пользователя с id {}", user.getId());
            return UserMapper.toUserDto(repository.findById(userId).get());
        } else {
            log.warn("User not found");
            throw new UserNotFoundException("User not found");
        }
    }


//    private final List<User> users = new ArrayList<>();
//    private long idUser = 0;

//    /**
//     * Создание пользователя
//     */
//    public UserDto addUser(User user) {
//        if (user != null) {
//            UserValidator.isValidCreateUser(user);
//            UserValidator.isDuplicateEmail(users, user);
//            user.setId(generatedId());
//            users.add(user);
//            log.info("Сохранили пользователя с id {}", user.getId());
//            return UserMapper.toUser(user);
//        } else {
//            log.warn("Данные пользователя не заполнены");
//            throw new NullPointerException("Данные пользователя не заполнены");
//        }
//    }

//    /**
//     * Получение списка пользователей
//     */
//    @Override
//    public List<User> findAll() {
//        List<UserDto> userDtoList = new ArrayList<>();
//        for (User user : users) {
//            userDtoList.add(UserMapper.toUser(user));
//        }
//        log.info("Вернули список пользователей {}", userDtoList.size());
//        return userDtoList;
//    }

//    /**
//     * Получение пользователя по id
//     */
//    @Override
//    public UserDto getUserDtoById(Long userId) {
//        UserValidator.isValidIdUsers(userId);
//        User user = null;
//        for (User userFromList : users) {
//            if (userFromList.getId() == userId)
//                user = userFromList;
//        }
//        if (user != null) {
//            log.info("Получили пользователя по id {}", userId);
//            return UserMapper.toUser(user);
//        } else {
//            log.warn("User not found");
//            throw new UserNotFoundException("User not found");
//        }
//    }

//    /**
//     * Удаление всех пользователей
//     */
//    @Override
//    public void deleteUsers() {
//        if (!users.isEmpty()) {
//            users.clear();
//        }
//        log.info("Удалили всех пользователей {}", users.size());
//    }

//    /**
//     * Удаление пользователя по id
//     */
//    @Override
//    public void deleteUserById(Long userId) {
//        UserValidator.isValidIdUsers(userId);
//        UserDto userDto = getUserDtoById(userId);
//        User user = UserMapper.toUser(userDto);
//        if (!users.contains(user)) {
//            log.warn("User not found in base");
//            throw new UserNotFoundException("User not found in base");
//        }
//        log.info("Удалили пользователя по id {}", userId);
//        users.remove(user);
//    }

//    private User findUserById(Long userId) {
//        UserValidator.isValidIdUsers(userId);
//        User user = null;
//        for (User userFromList : users) {
//            if (userFromList.getId() == userId) {
//                user = userFromList;
//            }
//        }
//        if (user != null) {
//            return user;
//        } else {
//            log.warn("Такого пользователя нет в базе");
//            throw new UserNotFoundException("Такого пользователя нет в базе");
//        }
//    }
//
//    private long generatedId() {
//        return ++idUser;
//    }
}