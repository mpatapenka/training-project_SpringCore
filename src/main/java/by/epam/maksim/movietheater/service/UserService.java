package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserService extends GenericService<User> {
    /**
     * Finding user by email.
     *
     * @param email Email of the user.
     * @return found user or <code>null</code>.
     */
    @Nullable
    User getUserByEmail(@Nonnull String email);
}