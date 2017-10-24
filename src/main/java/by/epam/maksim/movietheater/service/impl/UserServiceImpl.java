package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.repository.UserRepository;
import by.epam.maksim.movietheater.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractGenericService<User, UserRepository> implements UserService {

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

}