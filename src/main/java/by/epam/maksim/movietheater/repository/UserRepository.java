package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.domain.User;

public interface UserRepository extends GenericRepository<User> {
    User getUserByEmail(String email);
}