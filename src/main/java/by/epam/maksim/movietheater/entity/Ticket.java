package by.epam.maksim.movietheater.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "_ticket")
public class Ticket extends IdentifiedEntity {

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "_user_id", nullable = false)
    private User user;

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "_event_id", nullable = false)
    private Event event;

    @Column(name = "_seance_datetime", nullable = false)
    private LocalDateTime seanceDateTime;

    @Column(name = "_seat", nullable = false)
    private long seat;

    @Column(name = "_selling_price", nullable = false)
    private BigDecimal sellingPrice;

    public static Ticket build(User user, Event event, LocalDateTime seanceDateTime, long seat) {
        return Ticket.build(user, event, seanceDateTime, seat, null);
    }

    public static Ticket build(User user, Event event, LocalDateTime seanceDateTime, long seat, BigDecimal sellingPrice) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setSeanceDateTime(seanceDateTime);
        ticket.setSeat(seat);
        ticket.setSellingPrice(sellingPrice);
        return ticket;
    }

}