package by.epam.maksim.movietheater.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticket extends IdentifiedEntity implements Comparable<Ticket> {

    private final User user;
    private final Event event;
    private final LocalDateTime airDateTime;
    private final long seat;
    private BigDecimal sellingPrice;

    public Ticket(User user, Event event, LocalDateTime airDateTime, long seat) {
        this.user = user;
        this.event = event;
        this.airDateTime = airDateTime;
        this.seat = seat;
        this.sellingPrice = BigDecimal.ZERO;
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }

        int result = 0;

        if (airDateTime != null) {
            result = airDateTime.compareTo(other.airDateTime);
        }

        if (result == 0 && event != null && event.getName() != null && other.event != null) {
            result = event.getName().compareTo(other.event.getName());
        }

        if (result == 0) {
            result = Long.compare(seat, other.seat);
        }

        return result;
    }

}