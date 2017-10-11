package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import by.epam.maksim.movietheater.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

}