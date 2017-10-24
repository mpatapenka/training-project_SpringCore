package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.config.DatabaseConfig;
import by.epam.maksim.movietheater.config.RepositoryConfig;
import by.epam.maksim.movietheater.entity.Counter;
import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.CounterRepository;
import by.epam.maksim.movietheater.service.CounterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class, DatabaseConfig.class})
public class DBCounterRepositoryTest {

    @Autowired
    private CounterRepository counterRepository;

    @Test
    @Transactional
    public void testsFullCounterRepositoryCycle() {
        Counter first = counterRepository.save(User.class, "John", CounterService.GET_NAME_DOMAIN);
        Counter second = counterRepository.save(Event.class, "Movie 43", CounterService.GET_BASE_PRICE_DOMAIN);
        Counter third = counterRepository.save(Event.class, "Titanic", CounterService.GET_BASE_PRICE_DOMAIN);
        assertNotNull(first);
        assertNotNull(second);
        assertNotNull(third);
        assertFalse(first.isNew());
        assertFalse(second.isNew());
        assertFalse(third.isNew());


        first = counterRepository.get(User.class, "John", CounterService.GET_NAME_DOMAIN);
        assertNotNull(first);
        assertFalse(first.isNew());


        long countOfFirst = first.getCount().incrementAndGet();
        first = counterRepository.save(first);
        assertEquals(countOfFirst, first.getCount().get());


        Collection<Counter> userTypeCounters = counterRepository.getByType(User.class);
        Collection<Counter> eventTypeCounters = counterRepository.getByType(Event.class);
        assertEquals(1, userTypeCounters.size());
        assertEquals(2, eventTypeCounters.size());


        Collection<Counter> byName = counterRepository.getByName("John");
        assertEquals(1, byName.size());
        assertEquals("John", byName.iterator().next().getName());


        Collection<Counter> byGetNameDomain = counterRepository.getByDomain(CounterService.GET_NAME_DOMAIN);
        Collection<Counter> byGetBasePriceDomain = counterRepository.getByDomain(CounterService.GET_BASE_PRICE_DOMAIN);
        assertEquals(1, byGetNameDomain.size());
        assertEquals(2, byGetBasePriceDomain.size());
    }

}