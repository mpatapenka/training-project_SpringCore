package by.epam.maksim.movietheater.service;


import by.epam.maksim.movietheater.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface AuditoriumService {
    /**
     * Getting all auditoriums from the system.
     *
     * @return set of all auditoriums.
     */
    @Nonnull
    Set<Auditorium> getAll();

    /**
     * Finding auditorium by name.
     *
     * @param name Name of the auditorium.
     * @return found auditorium or <code>null</code>.
     */
    @Nullable
    Auditorium getByName(@Nonnull String name);
}