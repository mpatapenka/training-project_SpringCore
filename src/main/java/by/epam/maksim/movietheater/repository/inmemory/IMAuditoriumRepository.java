package by.epam.maksim.movietheater.repository.inmemory;

import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.repository.AuditoriumRepository;
import by.epam.maksim.movietheater.util.ConfigUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;

@Repository
public class IMAuditoriumRepository extends IMGenericRepository<Auditorium> implements AuditoriumRepository {

    @Value("${auditoriums.dir}")
    private String auditoriumsDirectory;

    @PostConstruct
    public void loadAuditoriumsFromProperties() {
        Map<String, Auditorium> auditoriumMap =
                ConfigUtils.parseAuditoriums(ConfigUtils.readAllProperties(new File(auditoriumsDirectory)));

        auditoriumMap.values().forEach(auditorium -> {
            long currentId = idCounter.getAndIncrement();
            auditorium.setId(currentId);
            storage.put(currentId, auditorium);
        });
    }

    @Override
    public Auditorium getByName(String name) {
        return storage.values().stream()
                .filter(auditorium -> name.equals(auditorium.getName()))
                .map(SerializationUtils::clone)
                .findFirst().orElse(null);
    }

}