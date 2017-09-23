package by.epam.maksim.movietheater.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.TreeSet;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends IdentifiedDomain {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private NavigableSet<Ticket> tickets = new TreeSet<>();
}