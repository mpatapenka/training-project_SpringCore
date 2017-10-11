package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.domain.IdentifiedEntity;

import java.util.Collection;

public interface GenericRepository<E extends IdentifiedEntity> {
    E save(E entity);
    void remove(E entity);
    E getById(Long id);
    Collection<E> getAll();
}