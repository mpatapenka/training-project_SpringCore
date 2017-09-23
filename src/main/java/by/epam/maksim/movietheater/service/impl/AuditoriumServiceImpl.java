package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Auditorium;
import by.epam.maksim.movietheater.service.AuditoriumService;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

    private final Map<String, Auditorium> storage;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(storage.values());
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return storage.get(name);
    }

}