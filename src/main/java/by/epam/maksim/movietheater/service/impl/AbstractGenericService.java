package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.entity.IdentifiedEntity;
import by.epam.maksim.movietheater.repository.GenericRepository;
import by.epam.maksim.movietheater.service.GenericService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Setter
abstract class AbstractGenericService<E extends IdentifiedEntity, REPOSITORY extends GenericRepository<E>>
        implements GenericService<E> {

    @Autowired
    REPOSITORY repository;

    @Override
    @Transactional
    public E save(E object) {
        return repository.save(object);
    }

    @Override
    @Transactional
    public void remove(E object) {
        repository.remove(object);
    }

    @Override
    @Transactional(readOnly = true)
    public E getById(Long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<E> getAll() {
        return repository.getAll();
    }

}