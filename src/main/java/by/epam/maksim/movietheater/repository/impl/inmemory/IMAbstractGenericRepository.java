package by.epam.maksim.movietheater.repository.impl.inmemory;

import by.epam.maksim.movietheater.domain.IdentifiedDomain;
import by.epam.maksim.movietheater.repository.GenericRepository;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

abstract class IMAbstractGenericRepository<T extends IdentifiedDomain> implements GenericRepository<T> {

    private final AtomicLong idCounter = new AtomicLong(1);
    final Map<Long, T> storage = new ConcurrentHashMap<>();

    @Override
    public T save(@Nonnull T object) {
        if (object.getId() == null) {
            object.setId(idCounter.getAndIncrement());
        }
        storage.put(object.getId(), SerializationUtils.clone(object));
        return object;
    }

    @Override
    public void remove(@Nonnull T object) {
        if (object.getId() == null) {
            throw new IllegalArgumentException("Object can't be deleted, id is 'null'.");
        }
        storage.remove(object.getId());
    }

    @Override
    public T getById(@Nonnull Long id) {
        return SerializationUtils.clone(storage.get(id));
    }

    @Nonnull
    @Override
    public Collection<T> getAll() {
        return storage.values().stream().map(SerializationUtils::clone).collect(Collectors.toList());
    }

}