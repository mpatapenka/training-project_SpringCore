package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.Counter;
import by.epam.maksim.movietheater.repository.CounterRepository;
import by.epam.maksim.movietheater.service.CounterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Override
    public Collection<Counter> getByType(Class<?> type) {
        return counterRepository.getByType(type);
    }

    @Override
    public Collection<Counter> getByName(String name) {
        return counterRepository.getByName(name);
    }

    @Override
    public Collection<Counter> getByDomain(String domain) {
        return counterRepository.getByDomain(domain);
    }

    @Override
    public Counter get(Class<?> type, String name, String domain) {
        return counterRepository.get(type, name, domain);
    }

    @Override
    public long increment(Class<?> type, String name, String domain) {
        Counter counter = counterRepository.get(type, name, domain);
        if (counter == null) {
            counter = counterRepository.save(type, name, domain);
        }
        return counter.getCount().incrementAndGet();
    }
}