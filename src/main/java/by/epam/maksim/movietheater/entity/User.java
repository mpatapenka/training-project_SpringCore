package by.epam.maksim.movietheater.entity;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"messages", "tickets"})
@ToString(callSuper = true)
@Entity
@Table(name = "_user")
public class User extends IdentifiedEntity {

    @Column(name = "_first_name", length = 50)
    private String firstName;

    @Column(name = "_last_name", length = 50)
    private String lastName;

    @Column(name = "_email", length = 50, nullable = false)
    private String email;

    @Column(name = "_birthday")
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    private Set<UserMessage> messages = Sets.newHashSet();

    @OneToMany(mappedBy = "user")
    private Set<Ticket> tickets = Sets.newHashSet();

}