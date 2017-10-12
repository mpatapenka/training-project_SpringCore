package by.epam.maksim.movietheater.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Counter implements Serializable {
    private final Class<?> type;
    private final String name;
    private final String domain;
    private final AtomicLong count = new AtomicLong(0);
}