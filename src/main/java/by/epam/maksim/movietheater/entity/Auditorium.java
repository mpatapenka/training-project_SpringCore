package by.epam.maksim.movietheater.entity;

import by.epam.maksim.movietheater.entity.converter.LongSetToStringConverter;
import com.google.common.base.Splitter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "_auditorium")
public class Auditorium extends IdentifiedEntity {

    @Column(name = "_name", length = 50, nullable = false)
    private String name;

    @Column(name = "_number_of_seats", nullable = false)
    private long numberOfSeats;

    @Column(name = "_vip_seats")
    @Convert(converter = LongSetToStringConverter.class)
    private Set<Long> vipSeats;

    private static final transient Splitter commaSplitter = Splitter.on(',').omitEmptyStrings().trimResults();


    public static Auditorium build(String name, long numberOfSeats, String vipSeatsAsString) {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(name);
        auditorium.setNumberOfSeats(numberOfSeats);
        auditorium.setVipSeats(StreamSupport.stream(commaSplitter.split(vipSeatsAsString).spliterator(), false)
                .map(Long::valueOf).collect(Collectors.toSet()));
        return auditorium;
    }

    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(vipSeats::contains).count();
    }

    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

}