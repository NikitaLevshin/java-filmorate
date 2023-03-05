package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    FilmValidator filmValidator = new FilmValidator();
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<Film> getFilms() {
        log.debug("Получаем список всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.debug("Добавляем фильм {}", film.getName());
        filmValidator.filmValidation(film);
        film.setId(id);
        films.put(id, film);
        id++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.debug("Обноваляем фильм {}", film.getName());
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм не найден");
        }
        filmValidator.filmValidation(film);
        films.replace(film.getId(), film);
        return film;
    }

}
