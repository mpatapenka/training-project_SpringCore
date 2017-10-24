package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.repository.AuditoriumRepository;
import by.epam.maksim.movietheater.service.AuditoriumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditoriumServiceImpl extends AbstractGenericService<Auditorium, AuditoriumRepository> implements AuditoriumService {

    @Override
    @Transactional(readOnly = true)
    public Auditorium getByName(String name) {
        return repository.getByName(name);
    }

}