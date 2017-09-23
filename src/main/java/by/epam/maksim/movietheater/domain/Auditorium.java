package by.epam.maksim.movietheater.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Auditorium implements Serializable {

    private final String name;
    private final long numberOfSeats;
    private final Set<Long> vipSeats;

    public Auditorium(String name, long numberOfSeats, String vipSeatsAsString) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = Stream.of(vipSeatsAsString.split(",")).map(Long::valueOf).collect(Collectors.toSet());
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>.
     *
     * @param seats Seats to process.
     * @return number of vip seats in request.
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(vipSeats::contains).count();
    }

    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

}