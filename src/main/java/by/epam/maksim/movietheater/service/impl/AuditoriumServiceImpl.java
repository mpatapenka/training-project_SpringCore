package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Auditorium;
import by.epam.maksim.movietheater.service.AuditoriumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

    private final Map<String, Auditorium> storage;

    @Override
    public Collection<Auditorium> getAll() {
        return storage.values();
    }

    @Override
    public Auditorium getByName(String name) {
        return storage.get(name);
    }

}