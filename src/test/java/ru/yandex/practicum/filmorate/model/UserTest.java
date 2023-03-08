package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private Validator validator;
    UserController userController;
    User user;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        user = new User();
        user.setEmail("testEmail@testEmail.com");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setBirthday(LocalDate.of(1990, 05,15));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void invalidEmailShouldFailValidation() {
        user.setEmail("email@.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void blankLoginShouldFail() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidBirthdayShouldFail() {
        user.setBirthday(LocalDate.of(2024, 05,16));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void loginWithSpaceShouldFail() {
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

    @Test
    public void blankUsernameShouldBeLogin() {
        user.setName("");
        userController.create(user);
        assertEquals(user.getName(), "testLogin", "Пустое имя не заменяется логином");
    }
}
