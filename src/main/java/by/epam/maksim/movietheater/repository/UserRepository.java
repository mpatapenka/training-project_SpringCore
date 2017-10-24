package by.epam.maksim.movietheater.repository;

import by.epam.maksim.movietheater.entity.User;

public interface UserRepository extends GenericRepository<User> {
    User getUserByEmail(String email);
}