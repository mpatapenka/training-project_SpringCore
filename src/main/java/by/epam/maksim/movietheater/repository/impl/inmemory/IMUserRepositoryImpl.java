package by.epam.maksim.movietheater.repository.impl.inmemory;

import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;

@Repository
public class IMUserRepositoryImpl extends IMAbstractGenericRepository<User> implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        return storage.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}