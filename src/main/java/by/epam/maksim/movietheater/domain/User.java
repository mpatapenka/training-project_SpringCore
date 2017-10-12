package by.epam.maksim.movietheater.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends IdentifiedEntity {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private Set<String> messages = new HashSet<>();
    private NavigableSet<Ticket> tickets = new TreeSet<>();
}