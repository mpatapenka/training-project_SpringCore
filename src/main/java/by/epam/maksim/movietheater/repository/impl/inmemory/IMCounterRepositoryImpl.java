package by.epam.maksim.movietheater.repository.impl.inmemory;

import by.epam.maksim.movietheater.domain.Counter;
import by.epam.maksim.movietheater.repository.CounterRepository;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class IMCounterRepositoryImpl implements CounterRepository {

    private static final Logger log = LoggerFactory.getLogger(IMCounterRepositoryImpl.class);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Multimap<Class<?>, Counter> typeToCounter = HashMultimap.create();
    private final Multimap<String, Counter> nameToCounter = HashMultimap.create();
    private final Multimap<String, Counter> domainToCounter = HashMultimap.create();

    @Override
    public Collection<Counter> getByType(Class<?> type) {
        return typeToCounter.get(type);
    }

    @Override
    public Collection<Counter> getByName(String name) {
        return nameToCounter.get(name);
    }

    @Override
    public Collection<Counter> getByDomain(String domain) {
        return domainToCounter.get(domain);
    }

    @Override
    public Counter get(Class<?> type, String name, String domain) {
        lock.readLock().lock();

        Collection<Counter> byType = getByType(type);
        Collection<Counter> byName = getByName(name);
        Collection<Counter> byDomain = getByDomain(domain);

        lock.readLock().unlock();

        Set<Counter> requiredCounters = Sets.newHashSet(byType);
        requiredCounters.retainAll(byName);
        requiredCounters.retainAll(byDomain);

        switch (requiredCounters.size()) {
            case 0: return null;
            case 1: return requiredCounters.iterator().next();
            default:
                log.error("Were stored more than one counter of type '{}', name '{}', domain '{}'.", type, name, domain);
                throw new IllegalArgumentException("Wrong number of counters with same type, name and domain.");
        }
    }

    @Override
    public Counter save(Class<?> type, String name, String domain) {
        Counter counter = get(type, name, domain);
        if (counter == null) {
            lock.writeLock().lock();

            counter = get(type, name, domain);
            if (counter == null) {
                counter = new Counter(type, name, domain);
                typeToCounter.put(type, counter);
                nameToCounter.put(name, counter);
                domainToCounter.put(domain, counter);
            }

            lock.writeLock().unlock();
        }
        return counter;
    }

}