package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import by.epam.maksim.movietheater.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserServiceImpl extends AbstractGenericService<User, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return repository.getUserByEmail(email);
    }

}