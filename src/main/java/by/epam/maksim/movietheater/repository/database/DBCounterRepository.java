package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.Counter;
import by.epam.maksim.movietheater.repository.CounterRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class DBCounterRepository extends DBGenericRepository<Counter> implements CounterRepository {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Counter> getByType(Class<?> type) {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _type = '" + type.getName() + "'").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Counter> getByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _name = '" + name + "'").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Counter> getByDomain(String domain) {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _domain = '" + domain + "'").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Counter get(Class<?> type, String name, String domain) {
        return (Counter) sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _type = '" + type.getName() + "' and _name = '" + name
                        + "' and _domain = '" + domain + "'").getResultStream().findFirst().orElse(null);
    }

    @Override
    public Counter save(Class<?> type, String name, String domain) {
        Counter counter = Counter.build(type.getName(), name, domain);
        return save(counter);
    }

    @Override
    Class<Counter> getEntityClass() {
        return Counter.class;
    }

}