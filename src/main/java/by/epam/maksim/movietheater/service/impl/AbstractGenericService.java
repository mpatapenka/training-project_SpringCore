package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.IdentifiedEntity;
import by.epam.maksim.movietheater.repository.GenericRepository;
import by.epam.maksim.movietheater.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@AllArgsConstructor
abstract class AbstractGenericService<E extends IdentifiedEntity, REPOSITORY extends GenericRepository<E>>
        implements GenericService<E> {

    @Autowired
    REPOSITORY repository;

    @Override
    public E save(E object) {
        return repository.save(object);
    }

    @Override
    public void remove(E object) {
        repository.remove(object);
    }

    @Override
    public E getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Collection<E> getAll() {
        return repository.getAll();
    }

}