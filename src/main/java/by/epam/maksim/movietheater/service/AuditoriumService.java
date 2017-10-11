package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.Auditorium;

import java.util.Collection;

public interface AuditoriumService {
    Collection<Auditorium> getAll();
    Auditorium getByName(String name);
}