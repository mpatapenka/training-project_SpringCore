package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.IdentifiedDomain;
import by.epam.maksim.movietheater.repository.GenericRepository;
import by.epam.maksim.movietheater.service.GenericService;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Collection;

@AllArgsConstructor
abstract class AbstractGenericService<T extends IdentifiedDomain, REPOSITORY extends GenericRepository<T>>
        implements GenericService<T> {

    REPOSITORY repository;

    @Override
    public T save(@Nonnull T object) {
        return repository.save(object);
    }

    @Override
    public void remove(@Nonnull T object) {
        repository.remove(object);
    }

    @Override
    public T getById(@Nonnull Long id) {
        return repository.getById(id);
    }

    @Nonnull
    @Override
    public Collection<T> getAll() {
        return repository.getAll();
    }

}