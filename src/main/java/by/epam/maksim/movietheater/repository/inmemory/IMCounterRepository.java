package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.Counter;
import by.epam.maksim.movietheater.repository.CounterRepository;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class IMCounterRepository implements CounterRepository {

    private static final Logger log = LoggerFactory.getLogger(IMCounterRepository.class);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Multimap<String, Counter> typeToCounter = HashMultimap.create();
    private final Multimap<String, Counter> nameToCounter = HashMultimap.create();
    private final Multimap<String, Counter> domainToCounter = HashMultimap.create();

    @Override
    public Collection<Counter> getByType(Class<?> type) {
        return typeToCounter.get(type.getName());
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
                log.error("Were stored more than one counter of type '{}', name '{}', entity '{}'.", type, name, domain);
                throw new IllegalArgumentException("Wrong number of counters with same type, name and entity.");
        }
    }

    @Override
    public Counter save(Class<?> type, String name, String domain) {
        Counter counter = get(type, name, domain);
        if (counter == null) {
            lock.writeLock().lock();

            counter = get(type, name, domain);
            if (counter == null) {
                counter = Counter.build(type.getName(), name, domain);
                typeToCounter.put(type.getName(), counter);
                nameToCounter.put(name, counter);
                domainToCounter.put(domain, counter);
            }

            lock.writeLock().unlock();
        }
        return counter;
    }

    @Override
    public Counter save(Counter entity) {
        throw new UnsupportedOperationException("Unsupported for inmemory implementation.");
    }

    @Override
    public void remove(Counter entity) {
        throw new UnsupportedOperationException("Unsupported for inmemory implementation.");
    }

    @Override
    public Counter getById(Long id) {
        throw new UnsupportedOperationException("Unsupported for inmemory implementation.");
    }

    @Override
    public Collection<Counter> getAll() {
        return Lists.newArrayList(typeToCounter.values());
    }

}