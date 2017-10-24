package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.entity.Counter;
import by.epam.maksim.movietheater.repository.CounterRepository;
import by.epam.maksim.movietheater.service.CounterService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Setter
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Counter> getByType(Class<?> type) {
        return counterRepository.getByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Counter> getByName(String name) {
        return counterRepository.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Counter> getByDomain(String domain) {
        return counterRepository.getByDomain(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public Counter get(Class<?> type, String name, String domain) {
        return counterRepository.get(type, name, domain);
    }

    @Override
    @Transactional
    public long increment(Class<?> type, String name, String domain) {
        Counter counter = counterRepository.get(type, name, domain);
        if (counter == null) {
            counter = counterRepository.save(type, name, domain);
        }
        return counter.getCount().incrementAndGet();
    }
}