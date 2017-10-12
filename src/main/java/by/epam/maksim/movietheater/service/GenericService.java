package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.IdentifiedEntity;

import java.util.Collection;

public interface GenericService<E extends IdentifiedEntity> {
    E save(E entity);
    void remove(E entity);
    E getById(Long id);
    Collection<E> getAll();
}