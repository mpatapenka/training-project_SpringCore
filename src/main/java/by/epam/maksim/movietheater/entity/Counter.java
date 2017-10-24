package by.epam.maksim.movietheater.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "_counter")
public class Counter extends IdentifiedEntity {

    @Column(name = "_type", nullable = false)
    private String type;

    @Column(name = "_name", length = 50, nullable = false)
    private String name;

    @Column(name = "_domain", length = 50, nullable = false)
    private String domain;

    @Column(name = "_count", nullable = false)
    private AtomicLong count;


    public static Counter build(String type, String name, String domain) {
        Counter counter = new Counter();
        counter.setType(type);
        counter.setName(name);
        counter.setDomain(domain);
        counter.setCount(new AtomicLong(0));
        return counter;
    }

}