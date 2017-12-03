package by.epam.maksim.movietheater.entity.converter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Converter
public class LongSetToStringConverter implements AttributeConverter<Set<Long>, String> {

    private static final char SEPARATOR = ',';
    private static final Joiner JOINER = Joiner.on(SEPARATOR).skipNulls();
    private static final Splitter SPLITTER = Splitter.on(SEPARATOR).omitEmptyStrings().trimResults();

    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        return attribute == null ? null : JOINER.join(attribute);
    }

    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        return StringUtils.isBlank(dbData) ? Sets.newHashSet()
                : StreamSupport.stream(SPLITTER.split(dbData).spliterator(), false)
                        .map(Long::valueOf).collect(Collectors.toSet());
    }

}