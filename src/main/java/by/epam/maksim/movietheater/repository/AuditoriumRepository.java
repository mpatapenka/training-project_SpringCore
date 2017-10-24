package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.entity.Auditorium;

public interface AuditoriumRepository extends GenericRepository<Auditorium> {
    Auditorium getByName(String name);
}