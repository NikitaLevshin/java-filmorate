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
    void getFilms() {
        filmController.add(film);
        assertTrue(filmController.get().contains(film), "Некорректное получение фильмов");
    }

    @Test
    void addFilm() {
        filmController.add(film);
        assertEquals(film.getId(), 1, "Фильм не создан");
    }

    @Test
    void updateFilm() {
        filmController.add(film);
        film.setName("Обновленный");
        film.setDuration(75);
        film.setDescription("Обновленное");
        Film film1 = filmController.update(film);
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
                    Film film1 = filmController.update(film);
                }
        );
        assertEquals("Фильм не найден", exception.getMessage(), "Не выбрасывается исключение, когда" +
                "фильм не добавлен");
    }
}