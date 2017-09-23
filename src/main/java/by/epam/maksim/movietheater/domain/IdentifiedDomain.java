package by.epam.maksim.movietheater.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class IdentifiedDomain implements Serializable {
    private Long id;
}