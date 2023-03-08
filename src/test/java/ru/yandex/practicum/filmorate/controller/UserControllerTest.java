package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;
    User user;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        user = new User();
        user.setEmail("testEmail@testEmail.com");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setBirthday(LocalDate.of(1990, 05,15));
    }
    @Test
    void getUsers() {
        userController.create(user);
        assertTrue(userController.get().contains(user), "Пользователи запрашиваются некорректно");
    }

    @Test
    void createUser() {
        userController.create(user);
        assertEquals(user.getId(), 1);
    }

    @Test
    void updateUser() {
        userController.create(user);
        user.setLogin("UpdateLogin");
        user.setName("UpdateName");
        user.setEmail("UpdatedEmail@email.com");
        user.setBirthday(LocalDate.of(1991,12,22));
        User user1 = userController.update(user);
        assertEquals(user.getName(), user1.getName(), "Имя пользователя не обновляется");
        assertEquals(user.getLogin(), user1.getLogin(), "Логин пользователя не обновляется");
        assertEquals(user.getEmail(), user1.getEmail(), "Email пользователя не обновляется");
        assertEquals(user.getBirthday(), user1.getBirthday(), "Дата рождения пользователя не обновляется");
    }

    @Test
    void updateShouldThrowExceptionWhenUserNotAdded() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    user.setLogin("updateLogin");
                    User user1 = userController.update(user);
                }
        );
        assertEquals("Пользователь не найден", exception.getMessage(), "Не выбрасывается исключение, когда" +
                "фильм не добавлен");
    }

    @Test
    void UserLoginValidationTest() {
        user.setLogin("test user 123");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.create(user);
                }
        );
        assertEquals("Логин не может содержать пробелы", exception.getMessage(),
                "Не выбрасывается исключение, когда логин содержит пробелы");
    }
}