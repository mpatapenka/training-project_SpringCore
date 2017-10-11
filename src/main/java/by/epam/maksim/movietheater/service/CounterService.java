package by.epam.maksim.movietheater.service;

import by.epam.maksim.movietheater.domain.Counter;

import java.util.Collection;

public interface CounterService {
    String GET_NAME_DOMAIN = "getName()";
    String GET_BASE_PRICE_DOMAIN = "getBasePrice()";
    String BOOK_TICKET_FOR_EVENT_DOMAIN = "bookTicketForEvent";
    String DISCOUNT_PERCENTAGE_DOMAIN = "discountPercentage:";

    Collection<Counter> getByType(Class<?> type);
    Collection<Counter> getByName(String name);
    Collection<Counter> getByDomain(String domain);
    Counter get(Class<?> type, String name, String domain);
    long increment(Class<?> type, String name, String domain);
}