package by.epam.maksim.movietheater.repository.database;

import by.epam.maksim.movietheater.config.DatabaseConfig;
import by.epam.maksim.movietheater.config.RepositoryConfig;
import by.epam.maksim.movietheater.entity.Auditorium;
import by.epam.maksim.movietheater.repository.AuditoriumRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class, DatabaseConfig.class})
public class DBAuditoriumRepositoryTest {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Test
    @Transactional
    public void testsFullAuditoriumRepositoryCycle() {
        Auditorium first = auditoriumRepository.save(Auditorium.build("first", 5, "1 , 2,3"));
        Auditorium second = auditoriumRepository.save(Auditorium.build("second", 10, "1, 2, 3, 4,5"));
        assertNotNull(first);
        assertNotNull(second);
        assertFalse(first.isNew());
        assertFalse(second.isNew());


        final String newFirstAuditoriumName = "updated first";

        first.setName(newFirstAuditoriumName);
        first = auditoriumRepository.save(first);
        assertEquals(newFirstAuditoriumName, first.getName());


        Collection<Auditorium> auditoriums = auditoriumRepository.getAll();
        assertEquals(2, auditoriums.size());


        first = auditoriumRepository.getByName(newFirstAuditoriumName);
        assertNotNull(first);
        assertEquals(first.getName(), newFirstAuditoriumName);


        auditoriumRepository.remove(first);
        auditoriums = auditoriumRepository.getAll();
        assertEquals(1, auditoriums.size());
        assertNotEquals(newFirstAuditoriumName, auditoriums.iterator().next().getName());
    }

}