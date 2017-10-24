package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.IdentifiedEntity;
import by.epam.maksim.movietheater.repository.GenericRepository;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Setter
abstract class DBGenericRepository<E extends IdentifiedEntity> implements GenericRepository<E> {

    String entityName = getEntityClass().getSimpleName();

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public E save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
        return getById(entity.getId());
    }

    @Override
    public void remove(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Override
    public E getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<E> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from " + entityName).getResultList();
    }

    abstract Class<E> getEntityClass();

}