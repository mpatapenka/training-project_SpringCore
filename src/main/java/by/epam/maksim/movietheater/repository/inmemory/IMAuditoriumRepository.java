package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.repository.AuditoriumRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class IMAuditoriumRepository extends IMGenericRepository<Auditorium> implements AuditoriumRepository {

    @Value("${auditoriums.dir}")
    private String auditoriumsDirectory;

    @Override
    public Auditorium getByName(String name) {
        return storage.values().stream()
                .filter(auditorium -> name.equals(auditorium.getName()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}