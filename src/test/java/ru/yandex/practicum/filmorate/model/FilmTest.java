package ru.yandex.practicum.filmorate.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;


public class FilmTest {

    private Validator validator;
    FilmController filmController;
    Film film;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1990,02,15));
        film.setDuration(125);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void FilmDateValidationTest() {
        film.setReleaseDate(LocalDate.of(1750, 01,05));
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Дата выпуска фильма должна быть после 28-12-1895", exception.getMessage(),
                "Не выбрасывается исключение, когда фильм выпущен раньше 28-12-1895");
    }

    @Test
    public void invalidDescriptionShouldFailValidation() {
        film.setDescription("TooLongDescriptionTooLongDescriptionTooLongDescriptionTooLongDescription" +
                "TooLongDescriptionTooLongDescriptionTooLongDescriptionTooLongDescriptionTooLongDescription" +
                "TooLongDescriptionTooLongDescriptionTooLongDescriptionTooLongDescriptionTooLongDescription");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void BlankNameShouldFailValidation() {
        film.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void NegativeDurationShouldFailValidation() {
        film.setDuration(-156);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
