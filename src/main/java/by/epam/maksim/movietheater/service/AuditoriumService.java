package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.entity.Auditorium;

import java.util.Collection;

public interface AuditoriumService extends GenericService<Auditorium> {
    Auditorium getByName(String name);
}