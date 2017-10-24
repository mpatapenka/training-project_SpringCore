package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;

@Repository
public class IMUserRepository extends IMGenericRepository<User> implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        return storage.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}