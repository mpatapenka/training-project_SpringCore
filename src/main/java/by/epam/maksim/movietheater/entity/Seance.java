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
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "_seance")
public class Seance extends IdentifiedEntity {

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "_event_id", nullable = false)
    private Event event;

    @ManyToOne(targetEntity = Auditorium.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "_auditorium_id", nullable = false)
    private Auditorium auditorium;

    @Column(name = "_air_datetime", nullable = false)
    private LocalDateTime airDateTime;

}