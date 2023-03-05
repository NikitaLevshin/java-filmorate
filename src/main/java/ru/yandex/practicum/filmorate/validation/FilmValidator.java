package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private final static LocalDate MIN_DATE = LocalDate.of(1895, 12,28);
    public void filmValidation(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            throw new ValidationException("Дата выпуска фильма должна быть после 28-12-1895");
        }
    }
}
