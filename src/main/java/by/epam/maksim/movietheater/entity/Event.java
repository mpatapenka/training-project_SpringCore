package by.epam.maksim.movietheater.entity;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"seances"})
@Entity
@Table(name = "_event")
public class Event extends IdentifiedEntity {

    @Column(name = "_name", length = 50, nullable = false)
    private String name;

    @Column(name = "_base_price", nullable = false)
    private BigDecimal basePrice;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_rating", nullable = false)
    private EventRating rating;

    @OneToMany(mappedBy = "event")
    private Set<Seance> seances = Sets.newHashSet();

    public NavigableSet<LocalDateTime> getAirDates() {
        return seances.stream()
                .map(Seance::getAirDateTime)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Auditorium getAuditorium(LocalDateTime airDateTime) {
        return seances.stream()
                .filter(seance -> airDateTime.isEqual(seance.getAirDateTime()))
                .map(Seance::getAuditorium)
                .findFirst().orElse(null);
    }

}