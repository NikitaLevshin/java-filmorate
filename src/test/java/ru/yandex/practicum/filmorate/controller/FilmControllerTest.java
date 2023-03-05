package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;
    Film film;
    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Фильм");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1990,02,15));
        film.setDuration(125);
    }

    @Test
    void getFilms() throws ValidationException {
        filmController.addFilm(film);
        assertTrue(filmController.getFilms().contains(film), "Некорректное получение фильмов");
    }

    @Test
    void addFilm() throws ValidationException {
        filmController.addFilm(film);
        assertEquals(film.getId(), 1, "Фильм не создан");
    }

    @Test
    void updateFilm() throws ValidationException {
        filmController.addFilm(film);
        film.setName("Обновленный");
        film.setDuration(75);
        film.setDescription("Обновленное");
        Film film1 = filmController.updateFilm(film);
        assertEquals(film.getName(), film1.getName(), "Не обновилось имя фильма");
        assertEquals(film.getDuration(), film1.getDuration(), "Не обновилась длительность фильма");
        assertEquals(film.getDescription(), film1.getDescription(), "Не обновилось описание фильма");
    }

    @Test
    void updateShouldThrowExceptionWhenFilmNotAdded() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    film.setDuration(10);
                    Film film1 = filmController.updateFilm(film);
                }
        );
        assertEquals("Фильм не найден", exception.getMessage(), "Не выбрасывается исключение, когда" +
                "фильм не добавлен");
    }
}