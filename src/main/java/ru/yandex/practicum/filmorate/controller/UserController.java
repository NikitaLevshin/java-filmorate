package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserValidator userValidator = new UserValidator();
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<User> get() {
        log.debug("Получаем список всех пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Создаем пользователя {}", user.getLogin());
        userValidator.userValidation(user);
        user.setId(id);
        users.put(id, user);
        id++;
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("Обновляем пользователя {}", user.getLogin());
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не найден");
        }
        userValidator.userValidation(user);
        users.replace(user.getId(), user);
        return user;
    }
}
