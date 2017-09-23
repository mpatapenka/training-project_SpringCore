package by.epam.maksim.movietheater.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticket extends IdentifiedDomain implements Comparable<Ticket> {

    private final User user;
    private final Event event;
    private final LocalDateTime dateTime;
    private final long seat;

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }

        int result = dateTime.compareTo(other.dateTime);

        if (result == 0 && event.getName() != null) {
            result = event.getName().compareTo(other.event.getName());
        }

        if (result == 0) {
            result = Long.compare(seat, other.seat);
        }
        return result;
    }

}