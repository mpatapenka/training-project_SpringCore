package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.repository.AuditoriumRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBAuditoriumRepository extends DBGenericRepository<Auditorium> implements AuditoriumRepository {

    @Override
    @SuppressWarnings("unchecked")
    public Auditorium getByName(String name) {
        return (Auditorium) sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _name = '" + name + "'")
                .getResultStream().findFirst().orElse(null);
    }

    @Override
    Class<Auditorium> getEntityClass() {
        return Auditorium.class;
    }

}