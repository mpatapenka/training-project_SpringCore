package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.User;

public interface UserService extends GenericService<User> {
    User getUserByEmail(String email);
}