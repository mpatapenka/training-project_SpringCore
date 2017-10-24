package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBUserRepository extends DBGenericRepository<User> implements UserRepository {

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByEmail(String email) {
        return (User) sessionFactory.getCurrentSession()
                .createQuery("from " + entityName + " where _email = '" + email + "'")
                .getResultStream().findFirst().orElse(null);
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }

}